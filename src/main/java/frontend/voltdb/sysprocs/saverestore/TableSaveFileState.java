/* This file is part of VoltDB.
 * Copyright (C) 2008-2010 VoltDB Inc.
 *
 * VoltDB is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VoltDB is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VoltDB.  If not, see <http://www.gnu.org/licenses/>.
 */

/***************************************************************************
 *  Copyright (C) 2017 by S-Store Project                                  *
 *  Brown University                                                       *
 *  Massachusetts Institute of Technology                                  *
 *  Portland State University                                              *
 *                                                                         *
 *  Author:  The S-Store Team (sstore.cs.brown.edu)                        *
 *                                                                         *
 *                                                                         *
 *  Permission is hereby granted, free of charge, to any person obtaining  *
 *  a copy of this software and associated documentation files (the        *
 *  "Software"), to deal in the Software without restriction, including    *
 *  without limitation the rights to use, copy, modify, merge, publish,    *
 *  distribute, sublicense, and/or sell copies of the Software, and to     *
 *  permit persons to whom the Software is furnished to do so, subject to  *
 *  the following conditions:                                              *
 *                                                                         *
 *  The above copyright notice and this permission notice shall be         *
 *  included in all copies or substantial portions of the Software.        *
 *                                                                         *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,        *
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF     *
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. *
 *  IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR      *
 *  OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,  *
 *  ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR  *
 *  OTHER DEALINGS IN THE SOFTWARE.                                        *
 ***************************************************************************/

package frontend.voltdb.sysprocs.saverestore;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import frontend.voltdb.VoltTableRow;
import frontend.voltdb.VoltSystemProcedure.SynthesizedPlanFragment;
import frontend.voltdb.catalog.Host;
import frontend.voltdb.catalog.Site;
import frontend.voltdb.catalog.Table;

import frontend.voltdb.catalog.Host;
import frontend.voltdb.catalog.Site;
import frontend.voltdb.catalog.Table;

import frontend.edu.brown.hstore.PartitionExecutor.SystemProcedureExecutionContext;
import frontend.voltdb.catalog.Table;
import frontend.voltdb.catalog.Table;


public abstract class TableSaveFileState
{
    // XXX This look a lot like similar stuff hiding in PlanAssembler.  I bet
    // there's no easy way to consolidate it, though.
    private static int NEXT_DEPENDENCY_ID = 1;

    public synchronized static int getNextDependencyId()
    {
        return NEXT_DEPENDENCY_ID++;
    }

    TableSaveFileState(String tableName, int allowExport)
    {
        m_tableName = tableName;
        m_planDependencyIds = new HashSet<Integer>();
        m_allowExport = allowExport;
    }

    abstract public SynthesizedPlanFragment[]
    generateRestorePlan(Table catalogTable);

    String getTableName()
    {
        return m_tableName;
    }

    abstract void addHostData(VoltTableRow row) throws IOException;

    public abstract boolean isConsistent();

    void addPlanDependencyId(int dependencyId)
    {
        m_planDependencyIds.add(dependencyId);
    }

    int[] getPlanDependencyIds()
    {
        int[] unboxed_ids = new int[m_planDependencyIds.size()];
        int id_index = 0;
        for (int id : m_planDependencyIds)
        {
            unboxed_ids[id_index] = id;
            id_index++;
        }
        return unboxed_ids;
    }

    void setRootDependencyId(int dependencyId)
    {
        m_rootDependencyId = dependencyId;
    }

    public int getRootDependencyId()
    {
        return m_rootDependencyId;
    }

    public void setSystemProcedureExecutionContext(SystemProcedureExecutionContext context){
        m_context = context;
    }
    
    public SystemProcedureExecutionContext getSystemProcedureExecutionContext(){
        return m_context;
    }
    
    private SystemProcedureExecutionContext m_context;
    private final String m_tableName;
    private final Set<Integer> m_planDependencyIds;
    final int m_allowExport;
    int m_rootDependencyId;
}
