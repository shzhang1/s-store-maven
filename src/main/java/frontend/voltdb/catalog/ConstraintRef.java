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

/* WARNING: THIS FILE IS AUTO-GENERATED
            DO NOT MODIFY THIS SOURCE
            ALL CHANGES MUST BE MADE IN THE CATALOG GENERATOR */

package frontend.voltdb.catalog;

/**
 * A reference to a table constraint
 */
public class ConstraintRef extends CatalogType {


    void setBaseValues(Catalog catalog, CatalogType parent, String path, String name) {
        super.setBaseValues(catalog, parent, path, name);
        m_fields.put("constraint", null);
    }

    public void update() {
    }

    /** GETTER: The constraint that is referenced */
    public Constraint getConstraint() {
        Object o = getField("constraint");
        if (o instanceof UnresolvedInfo) {
            UnresolvedInfo ui = (UnresolvedInfo) o;
            Constraint retval = (Constraint) m_catalog.getItemForRef(ui.path);
            assert(retval != null);
            m_fields.put("constraint", retval);
            return retval;
        }
        return (Constraint) o;
    }

    /** SETTER: The constraint that is referenced */
    public void setConstraint(Constraint value) {
        m_fields.put("constraint", value);
    }

}
