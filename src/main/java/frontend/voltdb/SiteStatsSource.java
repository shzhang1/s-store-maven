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
package frontend.voltdb;

import java.util.ArrayList;
import frontend.voltdb.VoltTable.ColumnInfo;

/**
 * Super class of sources of statistical information that are tied to an ExecutionSite.
 */
public abstract class SiteStatsSource extends StatsSource {

    /**
     * CatalogId of the site this source is associated with
     */
    private final int m_siteId;

    public SiteStatsSource(String name, int siteId, boolean isEE) {
        super(name, isEE);
        this.m_siteId = siteId;
    }

    @Override
    protected void populateColumnSchema(ArrayList<ColumnInfo> columns) {
        super.populateColumnSchema(columns);
        columns.add(new ColumnInfo(VoltSystemProcedure.CNAME_SITE_ID, VoltSystemProcedure.CTYPE_ID));
    }

    @Override
    protected void updateStatsRow(Object rowKey, Object rowValues[]) {
        rowValues[columnNameToIndex.get(VoltSystemProcedure.CNAME_SITE_ID)] = new Integer(m_siteId);
        super.updateStatsRow(rowKey, rowValues);
    }
}
