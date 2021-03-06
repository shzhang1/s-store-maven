package frontend.edu.brown.hstore.dispatchers;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

import frontend.edu.brown.hstore.HStoreCoordinator;
import frontend.edu.brown.hstore.HStoreSite;
import protorpc.edu.brown.hstore.Hstoreservice.TransactionFinishRequest;
import protorpc.edu.brown.hstore.Hstoreservice.TransactionFinishResponse;

public class TransactionFinishDispatcher extends AbstractDispatcher<Object[]> {
    
    public TransactionFinishDispatcher(HStoreSite hstore_site, HStoreCoordinator hstore_coordinator) {
        super(hstore_site, hstore_coordinator);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void runImpl(Object o[]) {
        RpcController controller = (RpcController)o[0];
        TransactionFinishRequest request = (TransactionFinishRequest)o[1];
        RpcCallback<TransactionFinishResponse> callback = (RpcCallback<TransactionFinishResponse>)o[2];
        hstore_coordinator.getTransactionFinishHandler().remoteHandler(controller, request, callback);
    }

}
