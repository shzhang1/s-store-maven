/* Copyright (c) 2001-2009, The HSQL Development Group
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of the HSQL Development Group nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL HSQL DEVELOPMENT GROUP, HSQLDB.ORG,
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package org.hsqldb;

import org.hsqldb.HSQLInterface.HSQLParseException;
import org.hsqldb.HsqlNameManager.HsqlName;
import org.hsqldb.index.Index;
import org.hsqldb.lib.OrderedHashSet;

// fredt@users 20020420 - patch523880 by leptipre@users - VIEW support - modified
// fredt@users 20031227 - remimplementated as compiled query

/**
 * Represents an SQL VIEW based on a query expression
 *
 * @author leptipre@users
 * @author Fred Toussi (fredt@users dot sourceforge.net)
 * @version 1.9.0
 * @since 1.7.0
 */
public class View extends TableDerived {

    SubQuery viewSubQuery;
    String   statement;

    //
    HsqlName[] columnNames;

    /** schema at the time of compilation */
    HsqlName compileTimeSchema;

    /**
     * List of subqueries in this view in order of materialization. Last
     * element is the view itself.
     */
    SubQuery[] viewSubqueries;

    /**
     * Names of SCHEMA objects referenced in VIEW
     */
    OrderedHashSet schemaObjectNames;

    /**
     * check option
     */
    int check;

    //
    private Table baseTable;

    //
    Expression checkExpression;

    View(Session session, Database db, HsqlName name, HsqlName[] columnNames,
            String definition, int check) {

        super(db, name, VIEW_TABLE);

        this.columnNames  = columnNames;
        this.statement    = definition;
        this.check        = check;
        compileTimeSchema = session.getSchemaHsqlName(null);
    }

    public int getType() {
        return VIEW;
    }

    public OrderedHashSet getReferences() {
        return schemaObjectNames;
    }

    public OrderedHashSet getComponents() {
        return null;
    }

    /**
     * Compiles the query expression and sets up the columns.
     */
    public void compile(Session session) {

        if (!database.schemaManager.schemaExists(compileTimeSchema.name)) {
            compileTimeSchema = session.getSchemaHsqlName(null);
        }

        session.setSchema(compileTimeSchema.name);

        ParserDQL p = new ParserDQL(session, new Scanner(statement));

        p.read();

        viewSubQuery    = p.XreadViewSubquery(this);
        queryExpression = viewSubQuery.queryExpression;

        if (getColumnCount() == 0) {
            if (columnNames == null) {
                columnNames =
                    viewSubQuery.queryExpression.getResultColumnNames();
            }

            if (columnNames.length
                    != viewSubQuery.queryExpression.getColumnCount()) {
                throw dbError.error(ErrorCode.X_42593, tableName.statementName);
            }

            TableUtil.setColumnsInSchemaTable(
                this, columnNames, queryExpression.getColumnTypes());
        }

        //
        viewSubqueries = p.compileContext.getSubqueries();

        for (int i = 0; i < viewSubqueries.length; i++) {
            if (viewSubqueries[i].parentView == null) {
                viewSubqueries[i].parentView = this;
            }
        }

        //
        viewSubQuery.getTable().view       = this;
        viewSubQuery.getTable().columnList = columnList;
        schemaObjectNames = p.compileContext.getSchemaObjectNames();
        baseTable                          = queryExpression.getBaseTable();

        if (baseTable == null) {
            return;
        }

        switch (check) {

            case SchemaObject.ViewCheckModes.CHECK_NONE :
                break;

            case SchemaObject.ViewCheckModes.CHECK_LOCAL :
                checkExpression = queryExpression.getCheckCondition();
                break;

            case SchemaObject.ViewCheckModes.CHECK_CASCADE :
                break;

            default :
                throw dbError.runtimeError(ErrorCode.U_S0500, "View");
        }
    }

    public String getSQL() {

        StringBuffer sb = new StringBuffer(128);

        sb.append(Tokens.T_CREATE).append(' ').append(Tokens.T_VIEW);
        sb.append(' ');
        sb.append(getName().getSchemaQualifiedStatementName()).append(' ');
        sb.append('(');

        int count = getColumnCount();

        for (int j = 0; j < count; j++) {
            sb.append(getColumn(j).getName().statementName);

            if (j < count - 1) {
                sb.append(',');
            }
        }

        sb.append(')').append(' ').append(Tokens.T_AS).append(' ');
        sb.append(getStatement());

        return sb.toString();
    }

    public int[] getUpdatableColumns() {
        return queryExpression.getBaseTableColumnMap();
    }

    public int getCheckOption() {
        return check;
    }

    /**
     * Returns the query expression for the view.
     */
    public String getStatement() {
        return statement;
    }

    /**
     * Overridden to disable SET TABLE READONLY DDL for View objects.
     */
    public void setDataReadOnly(boolean value) {
        throw dbError.error(ErrorCode.X_28000);
    }

    public void collectAllFunctionExpressions(OrderedHashSet collector) {

        // filter schemaObjectNames
    }

    /*************** VOLTDB *********************/

    /**
     * VoltDB added method to get a non-catalog-dependent
     * representation of this HSQLDB object.
     * @param session The current Session object may be needed to resolve
     * some names.
     * @param indent A string of whitespace to be prepended to every line
     * in the resulting XML.
     * @return XML, correctly indented, representing this object.
     * @throws HSQLParseException
     */
    String voltGetXML(Session session, String indent) throws HSQLParseException
    {
        StringBuilder sb = new StringBuilder();

        // append open table tag
        sb.append(indent).append("<table");
        // add table metadata
        sb.append(" name='").append(getName().name).append("'");
        sb.append(" query='").append(statement).append("'");
        sb.append(">\n");

        // read all the columns
        sb.append(indent + "  ").append("<columns>\n");
        int[] columnIndices = getColumnMap();
        for (int i : columnIndices) {
            ColumnSchema column = getColumn(i);
            sb.append(column.voltGetXML(session, indent + "    "));
        }
        sb.append(indent + "  ").append("</columns>\n");

        // read all the indexes
        sb.append(indent + "  ").append("<indexes>\n");
        for (Index index : getIndexes()) {
            sb.append(index.voltGetXML(session, indent + "    "));
        }
        sb.append(indent + "  ").append("</indexes>\n");

        // read all the constraints
        sb.append(indent + "  ").append("<constraints>\n");
        for (Constraint constraint : getConstraints()) {
            // giant hack to ignore "CHECK" constraint
            if (constraint.getConstraintType() != Constraint.CHECK)
                sb.append(constraint.voltGetXML(session, indent + "    "));
        }
        sb.append(indent + "  ").append("</constraints>\n");

        // close table tag
        sb.append(indent).append("</table>\n");

        return sb.toString();
    }
}
