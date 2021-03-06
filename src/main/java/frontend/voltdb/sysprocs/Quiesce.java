package frontend.voltdb.sysprocs;

import java.util.List;
import java.util.Map;

import frontend.voltdb.exceptions.ServerFaultException;
import frontend.voltdb.types.TimestampType;
import frontend.voltdb.utils.VoltTableUtil;
import frontend.voltdb.exceptions.ServerFaultException;
import frontend.voltdb.types.TimestampType;
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
import frontend.voltdb.types.TimestampType;
import frontend.voltdb.utils.VoltTableUtil;

import protorpc.edu.brown.hstore.Hstoreservice.Status;
import frontend.edu.brown.hstore.PartitionExecutor;

/** 
 * Flush out and reject all of the txns queued up at each PartitionExecutor
 */
@ProcInfo(singlePartition = false)
public class Quiesce extends VoltSystemProcedure {
    private static final Logger LOG = Logger.getLogger(Quiesce.class);

    private static final ColumnInfo ResultsColumns[] = {
        new ColumnInfo(VoltSystemProcedure.CNAME_HOST_ID, VoltSystemProcedure.CTYPE_ID),
        new ColumnInfo("HOSTNAME", VoltType.STRING),
        new ColumnInfo("PARTITION", VoltType.INTEGER),
        new ColumnInfo("STATUS", VoltType.STRING),
        new ColumnInfo("CREATED", VoltType.TIMESTAMP),
    };
    
    @Override
    public void initImpl() {
        executor.registerPlanFragment(SysProcFragmentId.PF_quiesceDistribute, this);
        executor.registerPlanFragment(SysProcFragmentId.PF_quiesceAggregate, this);
    }

    @Override
    public DependencySet executePlanFragment(Long txn_id,
                                             Map<Integer, List<VoltTable>> dependencies,
                                             int fragmentId,
                                             ParameterSet params,
                                             PartitionExecutor.SystemProcedureExecutionContext context) {
        DependencySet result = null;
        switch (fragmentId) {
            case SysProcFragmentId.PF_quiesceDistribute: {
                LOG.debug("Clearing out work queue at partition " + executor.getPartitionId());
                executor.haltProcessing();
                
                // Clear out the QueueManager too if this is the first partition
                // at this site
                hstore_site.getTransactionQueueManager().clearQueues(executor.getPartitionId());
                
                VoltTable vt = new VoltTable(ResultsColumns);
                Object row[] = {
                    this.hstore_site.getSiteId(),
                    this.hstore_site.getSiteName(),
                    this.executor.getPartitionId(),
                    Status.OK.name(),
                    new TimestampType(),
                };
                vt.addRow(row);
                result = new DependencySet(SysProcFragmentId.PF_quiesceDistribute, vt);
                break;
            }
            // Aggregate Results
            case SysProcFragmentId.PF_quiesceAggregate:
                List<VoltTable> siteResults = dependencies.get(SysProcFragmentId.PF_quiesceDistribute);
                if (siteResults == null || siteResults.isEmpty()) {
                    String msg = "Missing site results";
                    throw new ServerFaultException(msg, txn_id);
                }
                VoltTable vt = VoltTableUtil.union(siteResults);
                result = new DependencySet(SysProcFragmentId.PF_quiesceAggregate, vt);
                break;
            default:
                String msg = "Unexpected sysproc fragmentId '" + fragmentId + "'";
                throw new ServerFaultException(msg, txn_id);
        } // SWITCH
        // Invalid!
        return (result);
    }
    
    public VoltTable[] run() {
        return this.executeOncePerPartition(SysProcFragmentId.PF_quiesceDistribute,
                                            SysProcFragmentId.PF_quiesceAggregate,
                                            new ParameterSet());
    }
}
