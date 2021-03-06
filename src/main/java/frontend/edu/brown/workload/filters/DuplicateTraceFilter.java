package frontend.edu.brown.workload.filters;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import frontend.voltdb.catalog.CatalogType;

import frontend.edu.brown.workload.AbstractTraceElement;
import frontend.edu.brown.workload.TransactionTrace;

/**
 * @author pavlo
 */
public class DuplicateTraceFilter extends Filter {
    private static final Logger LOG = Logger.getLogger(DuplicateTraceFilter.class);
    
    private final Set<Long> txn_ids = new HashSet<Long>();
    private long skip_ctr = 0;
    
    @Override
    public String debugImpl() {
        return (this.getClass().getSimpleName() + "[num_ids=" + this.txn_ids.size() + ", skip_ctr=" + this.skip_ctr + "]");
    }
    
    @Override
    protected FilterResult filter(AbstractTraceElement<? extends CatalogType> element) {
        if (element instanceof TransactionTrace) {
            long txn_id = ((TransactionTrace)element).getTransactionId();
            if (this.txn_ids.contains(txn_id)) {
                this.skip_ctr++;
                if (LOG.isTraceEnabled() && this.skip_ctr % 100 == 0) LOG.trace(this.debugImpl());
                if (LOG.isTraceEnabled()) LOG.trace("SKIP: " + element);
                return (FilterResult.SKIP);
            }
            this.txn_ids.add(txn_id);
        }
        return (FilterResult.ALLOW);
    }
    
    @Override
    protected void resetImpl() {
        this.txn_ids.clear();
    }
}
