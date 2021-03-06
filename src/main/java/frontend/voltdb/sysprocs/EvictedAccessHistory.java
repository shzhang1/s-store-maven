package frontend.voltdb.sysprocs;

import java.util.List;
import java.util.Map;

import frontend.voltdb.exceptions.ServerFaultException;
import frontend.voltdb.types.SortDirectionType;
import frontend.voltdb.types.TimestampType;
import frontend.voltdb.utils.Pair;
import frontend.voltdb.utils.VoltTableUtil;
import frontend.voltdb.exceptions.ServerFaultException;
import frontend.voltdb.types.SortDirectionType;
import frontend.voltdb.types.TimestampType;
import frontend.voltdb.utils.Pair;
import frontend.voltdb.utils.VoltTableUtil;
import org.apache.log4j.Logger;
import frontend.voltdb.DependencySet;
import frontend.voltdb.ParameterSet;
import frontend.voltdb.ProcInfo;
import frontend.voltdb.VoltSystemProcedure;
import frontend.voltdb.VoltTable;
import frontend.voltdb.VoltTable.ColumnInfo;
import frontend.voltdb.VoltType;
import frontend.voltdb.exceptions.ServerFaultException;
import frontend.voltdb.types.SortDirectionType;
import frontend.voltdb.types.TimestampType;
import frontend.voltdb.utils.Pair;
import frontend.voltdb.utils.VoltTableUtil;

import frontend.edu.brown.hstore.PartitionExecutor;
import frontend.edu.brown.logging.LoggerUtil.LoggerBoolean;
import frontend.edu.brown.profilers.AntiCacheManagerProfiler;
import frontend.edu.brown.profilers.AntiCacheManagerProfiler.AccessHistory;

/** 
 * Gather the eviction history from each partition
 */
@ProcInfo(singlePartition = false)
public class EvictedAccessHistory extends VoltSystemProcedure {
    private static final Logger LOG = Logger.getLogger(EvictedAccessHistory.class);
    private static final LoggerBoolean debug = new LoggerBoolean();

    private static final ColumnInfo ACCESS_HISTORY[] = {
        new ColumnInfo("TIMESTAMP", VoltType.TIMESTAMP),
        new ColumnInfo(VoltSystemProcedure.CNAME_HOST_ID, VoltSystemProcedure.CTYPE_ID),
        new ColumnInfo("HOSTNAME", VoltType.STRING),
        new ColumnInfo("PARTITION", VoltType.INTEGER),
        new ColumnInfo("TXN_ID", VoltType.BIGINT),
        new ColumnInfo("START", VoltType.BIGINT), // TIMESTAMP (MS)
        new ColumnInfo("PROCEDURE", VoltType.STRING),
        new ColumnInfo("NUM_BLOCKS", VoltType.INTEGER),
        new ColumnInfo("NUM_TABLES", VoltType.INTEGER),
        new ColumnInfo("NUM_TUPLES", VoltType.INTEGER),
    };
    
    private static final int DISTRIBUTE_ID = SysProcFragmentId.PF_anitCacheAccessDistribute;
    private static final int AGGREGATE_ID = SysProcFragmentId.PF_anitCacheAccessAggregate;

    @Override
    public void initImpl() {
        executor.registerPlanFragment(AGGREGATE_ID, this);
        executor.registerPlanFragment(DISTRIBUTE_ID, this);
    }

    @Override
    public DependencySet executePlanFragment(Long txn_id,
                                             Map<Integer, List<VoltTable>> dependencies,
                                             int fragmentId,
                                             ParameterSet params,
                                             PartitionExecutor.SystemProcedureExecutionContext context) {
        DependencySet result = null;
        switch (fragmentId) {
            // ----------------------------------------------------------------------------
            // COLLECT DATA
            // ----------------------------------------------------------------------------
            case DISTRIBUTE_ID: {
                VoltTable vt = new VoltTable(ACCESS_HISTORY);
                AntiCacheManagerProfiler profiler = hstore_site.getAntiCacheManager().getDebugContext().getProfiler(this.partitionId);
                assert(profiler != null);
                for (AccessHistory eah : profiler.evictedaccess_history) {
                    Object row[] = {
                            new TimestampType(),
                            this.hstore_site.getSiteId(),
                            this.hstore_site.getSiteName(),
                            this.partitionId,
                            eah.txnId,
                            eah.startTimestamp,
                            eah.numBlocks,
                            eah.numTables,
                            eah.numTuples,
                        };
                        vt.addRow(row);
                } // FOR
                result = new DependencySet(DISTRIBUTE_ID, vt);
                if (debug.val)
                    LOG.info(String.format("%s - Sending back result for partition %d",
                             hstore_site.getTransaction(txn_id), this.executor.getPartitionId()));
                break;
            }
            
            // ----------------------------------------------------------------------------
            // AGGREGATE RESULTS
            // ----------------------------------------------------------------------------
            case AGGREGATE_ID: {
                List<VoltTable> siteResults = dependencies.get(DISTRIBUTE_ID);
                if (siteResults == null || siteResults.isEmpty()) {
                    String msg = "Missing site results";
                    throw new ServerFaultException(msg, txn_id);
                }
                
                Pair<Integer, SortDirectionType> sortCol = Pair.of(3, SortDirectionType.ASC);
                @SuppressWarnings("unchecked")
                VoltTable vt = VoltTableUtil.sort(VoltTableUtil.union(siteResults), sortCol);
                result = new DependencySet(AGGREGATE_ID, vt);
                break;
            }
            default:
                String msg = "Unexpected sysproc fragmentId '" + fragmentId + "'";
                throw new ServerFaultException(msg, txn_id);
        } // SWITCH
        return (result);
    }
    
    public VoltTable[] run() {
        if (hstore_conf.site.anticache_enable == false) {
            String msg = "Unable to collect evicted access history because 'site.anticache_enable' is disabled";
            throw new VoltAbortException(msg);
        }
        else if (hstore_conf.site.anticache_profiling == false) {
            String msg = "Unable to collect evicted access history because 'site.anticache_profiling' is disabled";
            throw new VoltAbortException(msg);
        }
        
        ParameterSet params = new ParameterSet();
        return this.executeOncePerPartition(DISTRIBUTE_ID, AGGREGATE_ID, params);
    }
}
