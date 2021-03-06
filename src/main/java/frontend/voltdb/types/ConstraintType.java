/* This file is part of VoltDB.
 * Copyright (C) 2008-2010 VoltDB L.L.C.
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

package frontend.voltdb.types;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public enum ConstraintType {
    FOREIGN_KEY (0),
    MAIN           (1),
    UNIQUE       (2),
    CHECK         (3),
    PRIMARY_KEY (4),
    NOT_NULL(5);

    ConstraintType(int val) {
        assert (this.ordinal() == val) :
            "Enum element " + this.name() +
            " in position " + this.ordinal() +
            " instead of position " + val;
    }

    public int getValue() {
        return this.ordinal();
    }

    private static final Map<Integer, ConstraintType> idx_lookup = new HashMap<Integer, ConstraintType>();
    private static final Map<String, ConstraintType> name_lookup = new HashMap<String, ConstraintType>();
    static {
        for (ConstraintType vt : EnumSet.allOf(ConstraintType.class)) {
            ConstraintType.idx_lookup.put(vt.ordinal(), vt);
            ConstraintType.name_lookup.put(vt.name().toLowerCase(), vt);
        }
    }

    public static Map<Integer, ConstraintType> getIndexMap() {
        return idx_lookup;
    }

    public static Map<String, ConstraintType> getNameMap() {
        return name_lookup;
    }

    public static ConstraintType get(Integer idx) {
        assert(idx >= 0);
        ConstraintType ret = ConstraintType.idx_lookup.get(idx);
        //
        // TODO: We probably should have an INVALID type like with the others...
        //
        return (ret);
        //return (ret == null ? ConstraintType.INVALID : ret);
    }

    public static ConstraintType get(String name) {
        ConstraintType ret = ConstraintType.name_lookup.get(name.toLowerCase());
        return (ret);
        //return (ret == null ? ConstraintType.INVALID : ret);
    }
}
