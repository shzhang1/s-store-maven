package frontend.edu.brown.markov.containers;

import java.util.Collection;

import frontend.voltdb.catalog.Procedure;

import frontend.edu.brown.markov.MarkovGraph;
import frontend.edu.brown.markov.MarkovUtil;

public class GlobalMarkovGraphsContainer extends MarkovGraphsContainer {

    public GlobalMarkovGraphsContainer(Collection<Procedure> procedures) {
        super(procedures);
    }
    
    @Override
    public MarkovGraph getFromParams(Long txn_id, int base_partition, Object[] params, Procedure catalog_proc) {
        return (this.getOrCreate(MarkovUtil.GLOBAL_MARKOV_CONTAINER_ID, catalog_proc, true));
    }
    
    public boolean isGlobal() {
        return (true);
    }
}
