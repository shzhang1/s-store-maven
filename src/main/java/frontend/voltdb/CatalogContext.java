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

package frontend.voltdb;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import frontend.voltdb.catalog.Catalog;
import frontend.voltdb.catalog.CatalogMap;
import frontend.voltdb.catalog.Cluster;
import frontend.voltdb.catalog.Database;
import frontend.voltdb.catalog.Host;
import frontend.voltdb.catalog.Partition;
import frontend.voltdb.catalog.PlanFragment;
import frontend.voltdb.catalog.Procedure;
import frontend.voltdb.catalog.Site;
import frontend.voltdb.catalog.Statement;
import frontend.voltdb.catalog.Table;
import frontend.voltdb.utils.JarClassLoader;

import frontend.edu.brown.catalog.CatalogUtil;
import frontend.edu.brown.mappings.ParameterMappingsSet;
import frontend.edu.brown.mappings.ParametersUtil;
import frontend.edu.brown.utils.PartitionSet;
import frontend.voltdb.catalog.*;
import frontend.voltdb.utils.JarClassLoader;
import frontend.voltdb.catalog.*;
import frontend.voltdb.utils.JarClassLoader;

public class CatalogContext {

    // THE CATALOG!
    public final Catalog catalog;

    // PUBLIC IMMUTABLE CACHED INFORMATION
    public final Cluster cluster;
    public final Database database;
    public final CatalogMap<Host> hosts;
    public final CatalogMap<Site> sites;
    
    // ParameterMappingsSet
    public final ParameterMappingsSet paramMappings;
    
    /** The total number of unique Hosts in the cluster */
    public final int numberOfHosts;
    /** The total number of unique Sites in the cluster */
    public final int numberOfSites;
    /** The total number of unique Partitions in the cluster */
    public final int numberOfPartitions;
    /** The total number of unique tables in the database */
    public final int numberOfTables;
    
    // PRIVATE
    public final File jarPath;
    private final JarClassLoader catalogClassLoader;

    // ------------------------------------------------------------
    // PARTITIONS
    // ------------------------------------------------------------
    
    private final Partition partitions[];
    private final PartitionSet partitionIdCollection = new PartitionSet();
    private final Integer partitionIdArray[];
    
    /** PartitionId -> Singleton set of that PartitionId */
    private final PartitionSet partitionSingletons[];
    
    /** PartitionId -> SiteId */
    private final int partitionSiteXref[];
    
    // ------------------------------------------------------------
    // PROCEDURES
    // ------------------------------------------------------------
    
    public final CatalogMap<Procedure> procedures;
    
    private final Collection<Procedure> regularProcedures = new ArrayList<Procedure>();
    private final Collection<Procedure> sysProcedures = new ArrayList<Procedure>();
    private final Collection<Procedure> mrProcedures = new ArrayList<Procedure>();
    private final Procedure proceduresArray[];
    
    // ------------------------------------------------------------
    // STATEMENTS
    // ------------------------------------------------------------
    
    private final Map<Integer, Statement> stmtIdXref = new HashMap<Integer, Statement>();
    
    // ------------------------------------------------------------
    // TABLES
    // ------------------------------------------------------------
    
    private final Collection<Table> sysTables = new ArrayList<Table>();
    private final Collection<Table> dataTables = new ArrayList<Table>();
    private final Collection<Table> viewTables = new ArrayList<Table>();
    private final Collection<Table> mapReduceTables = new ArrayList<Table>();
    private final Collection<Table> replicatedTables = new ArrayList<Table>();
    private final Collection<Table> evictableTables = new ArrayList<Table>();
    private final Collection<Table> streamTables = new ArrayList<Table>(); // added by hawk, 2014/5/27
    private final Collection<Table> windowTables = new ArrayList<Table>(); // added by hawk, 2014/5/27
    
    // ------------------------------------------------------------
    // PLANFRAGMENTS
    // ------------------------------------------------------------
    
    /**
     * PlanFragmentId -> TableIds Read/Written
     */
    private final Map<Long, int[]> fragmentReadTables = new HashMap<Long, int[]>(); 
    private final Map<Long, int[]> fragmentWriteTables = new HashMap<Long, int[]>();
    
    public CatalogContext(Catalog catalog) {
        this(catalog, (File)null);
    }

    public CatalogContext(Catalog catalog, String pathToCatalogJar) {
        this(catalog, new File(pathToCatalogJar));
    }
    
    public CatalogContext(Catalog catalog, File pathToCatalogJar) {
        // check the heck out of the given params in this immutable class
        assert(catalog != null);
        if (catalog == null) {
            throw new RuntimeException("Can't create CatalogContext with null catalog.");
        }

        this.jarPath = pathToCatalogJar;
        this.catalog = catalog;
        this.cluster = CatalogUtil.getCluster(this.catalog);
        this.database = CatalogUtil.getDatabase(this.catalog);
        this.hosts = this.cluster.getHosts();
        this.sites = this.cluster.getSites();
        
        if (this.jarPath != null) {
            this.catalogClassLoader = new JarClassLoader(this.jarPath.getAbsolutePath());
            this.paramMappings = ParametersUtil.getParameterMappingsSetFromJar(this.database, this.jarPath);
        } else {
            this.catalogClassLoader = null;
            this.paramMappings = null;
        }
        
        // ------------------------------------------------------------
        // PROCEDURES
        // ------------------------------------------------------------
        this.procedures = database.getProcedures();
        this.proceduresArray = new Procedure[this.procedures.size()+1];
        for (Procedure proc : this.procedures) {
            this.proceduresArray[proc.getId()] = proc;
            if (proc.getSystemproc()) {
                this.sysProcedures.add(proc);
            }
            else if (proc.getMapreduce()) {
                this.mrProcedures.add(proc);
            }
            else {
                this.regularProcedures.add(proc);
            }
        } // FOR
        
        // count nodes
        this.numberOfHosts = cluster.getHosts().size();

        // count exec sites
        this.numberOfSites = cluster.getSites().size();
        
        // ------------------------------------------------------------
        // PARTITIONS
        // ------------------------------------------------------------
        this.numberOfPartitions = cluster.getNum_partitions();
        this.partitions = new Partition[this.numberOfPartitions];
        this.partitionIdArray = new Integer[this.numberOfPartitions];
        this.partitionSingletons = new PartitionSet[this.numberOfPartitions];
        this.partitionSiteXref = new int[this.numberOfPartitions];
        for (Partition part : CatalogUtil.getAllPartitions(catalog)) {
            int p = part.getId();
            this.partitions[p] = part;
            this.partitionIdArray[p] = Integer.valueOf(p);
            this.partitionSingletons[p] = new PartitionSet(p);
            this.partitionIdCollection.add(this.partitionIdArray[p]);
            this.partitionSiteXref[part.getId()] = ((Site)part.getParent()).getId();
        } // FOR
        
        // ------------------------------------------------------------
        // TABLES
        // ------------------------------------------------------------
        for (Table tbl : this.database.getTables()) {
            // SYSTEM TABLE
            if (tbl.getSystable()) {
                this.sysTables.add(tbl);
            }
            // MAPREDUCE TABLE
            else if (tbl.getMapreduce()) {
                this.mapReduceTables.add(tbl);
            }
            // MATERIALIZED VIEW TABLE
            else if (tbl.getMaterializer() != null) {
                this.viewTables.add(tbl);
            }
            // REGULAR DATA TABLE
            else {
                this.dataTables.add(tbl);
                if (tbl.getIsreplicated()) {
                    this.replicatedTables.add(tbl);
                }
                if (tbl.getEvictable()) {
                    this.evictableTables.add(tbl);
                }
                // added by hawk, 2014/5/27
                if (tbl.getIsstream()) {
                    this.streamTables.add(tbl);
                }
                if (tbl.getIswindow()) {
                    this.windowTables.add(tbl);
                }
                // ended by hawk
            }
        } // FOR
        this.numberOfTables = database.getTables().size();
        
        // PLANFRAGMENTS
        this.initPlanFragments();
    }
    
    private void initPlanFragments() {
        Set<PlanFragment> allFrags = new HashSet<PlanFragment>();
        for (Procedure proc : database.getProcedures()) {
            for (Statement stmt : proc.getStatements()) {
                allFrags.clear();
                allFrags.addAll(stmt.getFragments());
                allFrags.addAll(stmt.getMs_fragments());
                for (PlanFragment frag : allFrags) {
                    Collection<Table> tables = CatalogUtil.getReferencedTables(frag);
                    int tableIds[] = new int[tables.size()];
                    int i = 0;
                    for (Table tbl : tables) {
                        tableIds[i++] = tbl.getRelativeIndex();
                    } // FOR
                    if (frag.getReadonly()) {
                        this.fragmentReadTables.put(Long.valueOf(frag.getId()), tableIds);
                    } else {
                        this.fragmentWriteTables.put(Long.valueOf(frag.getId()), tableIds);
                    }
                } // FOR (frag)
            } // FOR (stmt)
        } // FOR (proc)
    }
    

    public CatalogContext deepCopy() {
        return new CatalogContext(catalog.deepCopy(), jarPath);
    }

    public CatalogContext update(String pathToNewJar, String diffCommands) {
        Catalog newCatalog = catalog.deepCopy();
        newCatalog.execute(diffCommands);
        CatalogContext retval = new CatalogContext(newCatalog, pathToNewJar);
        return retval;
    }

    /**
     * Given a class name in the catalog jar, loads it from the jar, even if the
     * jar is served from a url and isn't in the classpath.
     *
     * @param procedureClassName The name of the class to load.
     * @return A java Class variable assocated with the class.
     * @throws ClassNotFoundException if the class is not in the jar file.
     */
    public Class<?> classForProcedure(String procedureClassName) throws ClassNotFoundException {
        //System.out.println("Loading class " + procedureClassName);

        // this is a safety mechanism to prevent catalog classes overriding voltdb stuff
        if (procedureClassName.startsWith("org.voltdb."))
            return Class.forName(procedureClassName);

        // look in the catalog for the file
        return catalogClassLoader.loadClass(procedureClassName);
    }
    
    
    // ------------------------------------------------------------
    // SITES
    // ------------------------------------------------------------
    
    /**
     * Return the unique Site catalog object for the given id
     * @param catalog_item
     * @return
     */
    public Site getSiteById(int site_id) {
        assert(site_id >= 0);
        return (this.sites.get("id", site_id));
    }
    
    /**
     * Return the site id for the given partition
     * @param partition_id
     * @return
     */
    public int getSiteIdForPartitionId(int partition_id) {
        return (this.partitionSiteXref[partition_id]);
    }
    
    /**
     * Return the site for the given partition
     * @param partition
     * @return
     */
    public Site getSiteForPartition(int partition) {
        int siteId = this.partitionSiteXref[partition]; 
        return (this.getSiteById(siteId));
    }

    // ------------------------------------------------------------
    // PARTITIONS
    // ------------------------------------------------------------
    
    /**
     * Return an array containing all the Partition handles in the cluster
     */
    public Partition[] getAllPartitions() {
        return (this.partitions);
    }
    
    /**
     * Return the Partition catalog object for the given PartitionId
     * @param id
     * @return
     */
    public Partition getPartitionById(int id) {
        return (this.partitions[id]);
    }
    
    /**
     * Return all the partition ids in this H-Store database cluster
     */
    public PartitionSet getAllPartitionIds() {
        return (this.partitionIdCollection);
    }
    
    /**
     * Return all the partition ids in this H-Store database cluster
     */
    public Integer[] getAllPartitionIdArray() {
        return (this.partitionIdArray);
    }
    
    /**
     * Return a PartitionSet that only contains the given partition id
     * @param partition
     */
    public PartitionSet getPartitionSetSingleton(int partition) {
        return (this.partitionSingletons[partition]);
    }
    
    // ------------------------------------------------------------
    // TABLES + COLUMNS
    // ------------------------------------------------------------

    /**
     * Return the Table for the given name
     * @param tableName
     */
    public Table getTableByName(String tableName) {
        return (this.database.getTables().getIgnoreCase(tableName));
    }
    
    /**
     * Return the Table for the given id
     * @param tableId
     */
    public Table getTableById(int tableId) {
        // HACK HACK HACK
        assert(tableId-1 >= 0);
        Table tables[] = this.database.getTables().values();
        if (tableId-1 >= tables.length) {
            String msg = "Invalid tableId '" + tableId + "'";
            throw new IllegalArgumentException(msg);
        }
        return (tables[tableId-1]);
    }
    
    /**
     * Return all of the internal system tables for the database
     */
    public Collection<Table> getSysTables() {
        return (sysTables);
    }

    /**
     * Return all of the user-defined data tables for the database
     */
    public Collection<Table> getDataTables() {
        return (dataTables);
    }
    
    /**
     * Return all of the materialized view tables for the database
     */
    public Collection<Table> getViewTables() {
        return (viewTables);
    }

    /**
     * Return all of the MapReduce input data tables for the database
     */
    public Collection<Table> getMapReduceTables() {
        return (mapReduceTables);
    }
    
    /**
     * Return all of the replicated tables for the database
     */
    public Collection<Table> getReplicatedTables() {
        return (replicatedTables);
    }
    
    /**
     * Return all of the evictable tables for the database
     */
    public Collection<Table> getEvictableTables() {
        return (evictableTables);
    }

    /**
     * Return all of the stream tables for the database, added by hawk, 2014/5/27
     */
    public Collection<Table> getStreamTables() {
        return (streamTables);
    }

    public Collection<Table> getWindowTables() {
        return (windowTables);
    }
    
    //ended by hawk

    // ------------------------------------------------------------
    // PROCEDURES
    // ------------------------------------------------------------
    
    public Procedure getProcedureById(int procId) {
        if (procId >= 0 && procId < this.proceduresArray.length) {
            return (this.proceduresArray[procId]);
        }
        return (null);
    }
    
    /**
     * Return all of the regular transactional Procedures in the catalog
     */
    public Collection<Procedure> getRegularProcedures() {
        return (this.regularProcedures);
    }
    
    /**
     * Return all of the internal system Procedures in the catalog
     */
    public Collection<Procedure> getSysProcedures() {
        return (this.sysProcedures);
    }
    
    /**
     * Return all of the MapReduce Procedures in the catalog
     */
    public Collection<Procedure> getMapReduceProcedures() {
        return (this.mrProcedures);
    }
    
    // ------------------------------------------------------------
    // STATEMENTS
    // ------------------------------------------------------------
    
    public Statement getStatementById(int stmtId) {
        // HACK: The first call will actually build the cache
        if (this.stmtIdXref.isEmpty()) {
            synchronized (this.stmtIdXref) {
                if (this.stmtIdXref.isEmpty()) {
                    for (Procedure catalog_proc : this.procedures.values()) {
                        for (Statement catalog_stmt : catalog_proc.getStatements().values()) {
                            this.stmtIdXref.put(catalog_stmt.getId(), catalog_stmt);
                        } // FOR (stmt)
                    } // FOR (proc)
                }
            } // SYNCH
        }
        return (this.stmtIdXref.get(stmtId));
    }
    
    // ------------------------------------------------------------
    // PLANFRAGMENTS
    // ------------------------------------------------------------
    
    /**
     * Return the tableIds that are read by this PlanFragment
     * @param planFragmentId
     * @return
     */
    public int[] getReadTableIds(Long planFragmentId) {
        return (this.fragmentReadTables.get(planFragmentId));
    }

    /**
     * Return the tableIds that are written by this PlanFragment
     * @param planFragmentId
     * @return
     */
    public int[] getWriteTableIds(Long planFragmentId) {
        return (this.fragmentWriteTables.get(planFragmentId));
    }
}
