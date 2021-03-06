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

package org.hsqldb;

import org.hsqldb.HsqlNameManager.HsqlName;
import org.hsqldb.lib.HashMap;
import org.hsqldb.lib.HashMappedList;
import org.hsqldb.lib.HsqlArrayList;
import org.hsqldb.lib.Iterator;
import org.hsqldb.lib.OrderedHashSet;
import org.hsqldb.store.ValuePool;

import java.lang.Error;

/**
 * Collection of SQL schema objects of a specific type in a schema
 *
 * @author Fred Toussi (fredt@users dot sourceforge.net)
 * @version 1.9.0
 * @since 1.9.0
 */
public class SchemaObjectSet {

    HashMap       map;
    int           type;
    SchemaManager manager;

    SchemaObjectSet(int type) {

        this.type = type;

        switch (type) {

            case SchemaObject.VIEW :
            case SchemaObject.TABLE :
            case SchemaObject.STREAM :
            case SchemaObject.SEQUENCE :
            case SchemaObject.CHARSET :
            case SchemaObject.DOMAIN :
            case SchemaObject.TYPE :
            case SchemaObject.COLLATION :
            case SchemaObject.PROCEDURE :
            case SchemaObject.FUNCTION :
            case SchemaObject.ASSERTION :
            case SchemaObject.TRIGGER :
                map = new HashMappedList();
                break;

            case SchemaObject.CONSTRAINT :
            case SchemaObject.INDEX :
                map = new HashMap();
                break;
        }
    }

    HsqlName getName(String name) {

        switch (type) {

            case SchemaObject.VIEW :
            case SchemaObject.TABLE :
            case SchemaObject.STREAM :
            case SchemaObject.SEQUENCE :
            case SchemaObject.CHARSET :
            case SchemaObject.DOMAIN :
            case SchemaObject.TYPE :
            case SchemaObject.COLLATION :
            case SchemaObject.PROCEDURE :
            case SchemaObject.FUNCTION :
            case SchemaObject.ASSERTION :
            case SchemaObject.TRIGGER :
                SchemaObject object = ((SchemaObject) map.get(name));

                return object == null ? null
                                      : object.getName();

            case SchemaObject.CONSTRAINT :
            case SchemaObject.INDEX : {
                return (HsqlName) map.get(name);
            }
            default :
                return (HsqlName) map.get(name);
        }
    }

    SchemaObject getObject(String name) {

        switch (type) {

            case SchemaObject.VIEW :
            case SchemaObject.TABLE :
            case SchemaObject.STREAM :
            case SchemaObject.SEQUENCE :
            case SchemaObject.CHARSET :
            case SchemaObject.DOMAIN :
            case SchemaObject.TYPE :
            case SchemaObject.COLLATION :
            case SchemaObject.PROCEDURE :
            case SchemaObject.FUNCTION :
            case SchemaObject.ASSERTION :
            case SchemaObject.TRIGGER :
                return (SchemaObject) map.get(name);

            default :
                throw  dbError.error(ErrorCode.U_S0500, "SchemaObjectSet");
        }
    }

    boolean contains(String name) {
        return map.containsKey(name);
    }

    void checkAdd(HsqlName name) {

        if (map.containsKey(name.name)) {
            int code = getAddErrorCode(name.type);

            throw dbError.error (code, name.name);
        }
    }

    void checkExists(String name) {

        if (!map.containsKey(name)) {
            int code = getGetErrorCode(type);

            throw dbError.error (code, name);
        }
    }

    void add(SchemaObject object) {

        HsqlName name = object.getName();

        if (map.containsKey(name.name)) {
            int code = getAddErrorCode(name.type);

            throw dbError.error (code, name.name);
        }

        Object value = object;

        switch (name.type) {

            case SchemaObject.CONSTRAINT :
            case SchemaObject.INDEX :
                value = name;
        }

        map.put(name.name, value);
    }

    void remove(String name) {
        map.remove(name);
    }

    void removeParent(HsqlName parent) {

        Iterator it = map.values().iterator();

        while (it.hasNext()) {
            if (type == SchemaObject.TRIGGER) {
                SchemaObject trigger = (SchemaObject) it.next();

                if (trigger.getName().parent == parent) {
                    it.remove();
                }
            } else {
                HsqlName name = (HsqlName) it.next();

                if (name.parent == parent) {
                    it.remove();
                }
            }
        }
    }

    void rename(HsqlName name, HsqlName newName) {

        if (map.containsKey(newName.name)) {
            int code = getAddErrorCode(name.type);

            throw dbError.error (code, newName.name);
        }

        switch (newName.type) {

            case SchemaObject.VIEW :
            case SchemaObject.TABLE :
            case SchemaObject.STREAM :
            case SchemaObject.SEQUENCE :
            case SchemaObject.CHARSET :
            case SchemaObject.COLLATION :
            case SchemaObject.PROCEDURE :
            case SchemaObject.FUNCTION :
            case SchemaObject.DOMAIN :
            case SchemaObject.TYPE :
            case SchemaObject.ASSERTION :
            case SchemaObject.TRIGGER : {
                int i = ((HashMappedList) map).getIndex(name.name);

                if (i == -1) {
                    int code = getGetErrorCode(name.type);

                    throw dbError.error (code, name.name);
                }

                SchemaObject object =
                    (SchemaObject) ((HashMappedList) map).get(i);

                object.getName().rename(newName);
                ((HashMappedList) map).setKey(i, name.name);

                break;
            }
            case SchemaObject.CONSTRAINT :
            case SchemaObject.INDEX : {
                map.remove(name.name);
                name.rename(newName);
                map.put(name.name, name);

                break;
            }
        }
    }

    static int getAddErrorCode(int type) {

        int code;

        switch (type) {

            case SchemaObject.VIEW :
            case SchemaObject.TABLE :
            case SchemaObject.STREAM :
            case SchemaObject.SEQUENCE :
            case SchemaObject.CHARSET :
            case SchemaObject.DOMAIN :
            case SchemaObject.TYPE :
            case SchemaObject.COLLATION :
            case SchemaObject.PROCEDURE :
            case SchemaObject.FUNCTION :
            case SchemaObject.CONSTRAINT :
            case SchemaObject.ASSERTION :
            case SchemaObject.INDEX :
            case SchemaObject.TRIGGER :
                code = ErrorCode.X_42504;
                break;

            default :
                throw  dbError.error(ErrorCode.U_S0500, "SchemaObjectSet");
        }

        return code;
    }

    static int getGetErrorCode(int type) {

        int code;

        switch (type) {

            case SchemaObject.VIEW :
            case SchemaObject.TABLE :
            case SchemaObject.STREAM :
            case SchemaObject.SEQUENCE :
            case SchemaObject.CHARSET :
            case SchemaObject.DOMAIN :
            case SchemaObject.TYPE :
            case SchemaObject.CONSTRAINT :
            case SchemaObject.COLLATION :
            case SchemaObject.PROCEDURE :
            case SchemaObject.FUNCTION :
            case SchemaObject.ASSERTION :
            case SchemaObject.INDEX :
            case SchemaObject.TRIGGER :
                code = ErrorCode.X_42501;
                break;

            default :
                throw  dbError.error(ErrorCode.U_S0500, "SchemaObjectSet");
        }

        return code;
    }

    public static String getName(int type) {

        switch (type) {

            case SchemaObject.VIEW :
                return Tokens.T_VIEW;

            case SchemaObject.TABLE :
                return Tokens.T_TABLE;
                
            case SchemaObject.STREAM :
            	return Tokens.T_STREAM;

            case SchemaObject.SEQUENCE :
                return Tokens.T_SEQUENCE;

            case SchemaObject.CHARSET :
                return Tokens.T_CHARACTER + ' ' + Tokens.T_SET;

            case SchemaObject.DOMAIN :
                return Tokens.T_DOMAIN;

            case SchemaObject.TYPE :
                return Tokens.T_TYPE;

            case SchemaObject.CONSTRAINT :
                return Tokens.T_CONSTRAINT;

            case SchemaObject.COLLATION :
                return Tokens.T_COLLATION;

            case SchemaObject.PROCEDURE :
                return Tokens.T_PROCEDURE;

            case SchemaObject.FUNCTION :
                return Tokens.T_FUNCTION;

            case SchemaObject.ASSERTION :
                return Tokens.T_ASSERTION;

            case SchemaObject.INDEX :
                return Tokens.T_INDEX;

            case SchemaObject.TRIGGER :
                return Tokens.T_TRIGGER;

            default :
                throw dbError.runtimeError(ErrorCode.U_S0500, "SchemaObjectSet");
        }
    }

    String[] getSQL(OrderedHashSet resolved, OrderedHashSet unresolved) {

        HsqlArrayList list = new HsqlArrayList();

        if (!(map instanceof HashMappedList)) {
            return null;
        }

        if (map.isEmpty()) {
            return ValuePool.emptyStringArray;
        }

        Iterator it = map.values().iterator();

        if (type == SchemaObject.FUNCTION || type == SchemaObject.PROCEDURE) {
            OrderedHashSet set = new OrderedHashSet();

            while (it.hasNext()) {
                RoutineSchema routine = (RoutineSchema) it.next();

                for (int i = 0; i < routine.routines.length; i++) {
                    set.add(routine.routines[i]);
                }
            }

            it = set.iterator();
        }

        while (it.hasNext()) {
            SchemaObject   object     = (SchemaObject) it.next();
            OrderedHashSet references = object.getReferences();

            if (references != null) {
                boolean isResolved = true;

                for (int j = 0; j < references.size(); j++) {
                    HsqlName name = (HsqlName) references.get(j);

                    if (SqlInvariants.isSchemaNameSystem(name)) {
                        continue;
                    }

                    if (name.type == SchemaObject.COLUMN) {
                        name = name.parent;
                    }

                    if (name.type == SchemaObject.CHARSET) {

                        // some built-in character sets have no schema
                        if (name.schema == null) {
                            continue;
                        }
                    }

                    if (!resolved.contains(name)) {
                        isResolved = false;

                        break;
                    }
                }

                if (!isResolved) {
                    unresolved.add(object);

                    continue;
                }
            }

            resolved.add(object.getName());

            if (object.getType() == SchemaObject.TABLE || object.getType() == SchemaObject.STREAM) {
                list.addAll(((Table) object).getSQL(resolved, unresolved));
            } else {
                list.add(object.getSQL());
            }
        }

        String[] array = new String[list.size()];

        list.toArray(array);

        return array;
    }
}