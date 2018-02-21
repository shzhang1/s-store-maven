/***************************************************************************
 *   Copyright (C) 2012 by H-Store Project                                 *
 *   Brown University                                                      *
 *   Massachusetts Institute of Technology                                 *
 *   Yale University                                                       *
 *                                                                         *
 *   Permission is hereby granted, free of charge, to any person obtaining *
 *   a copy of this software and associated documentation files (the       *
 *   "Software"), to deal in the Software without restriction, including   *
 *   without limitation the rights to use, copy, modify, merge, publish,   *
 *   distribute, sublicense, and/or sell copies of the Software, and to    *
 *   permit persons to whom the Software is furnished to do so, subject to *
 *   the following conditions:                                             *
 *                                                                         *
 *   The above copyright notice and this permission notice shall be        *
 *   included in all copies or substantial portions of the Software.       *
 *                                                                         *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,       *
 *   EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF    *
 *   MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.*
 *   IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR     *
 *   OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, *
 *   ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR *
 *   OTHER DEALINGS IN THE SOFTWARE.                                       *
 ***************************************************************************/
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

package frontend.edu.brown.hstore.txns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import frontend.voltdb.ClientResponseImpl;
import frontend.voltdb.ParameterSet;
import frontend.voltdb.SQLStmt;
import frontend.voltdb.VoltTable;
import frontend.voltdb.catalog.Procedure;
import frontend.voltdb.catalog.Statement;
import frontend.voltdb.catalog.Table;
import frontend.voltdb.exceptions.SerializableException;
import frontend.voltdb.types.SpeculationType;
import frontend.voltdb.utils.EstTime;

import com.google.protobuf.RpcCallback;

import frontend.edu.brown.hstore.HStoreSite;
import protorpc.edu.brown.hstore.Hstoreservice.WorkFragment;
import frontend.edu.brown.hstore.callbacks.LocalFinishCallback;
import frontend.edu.brown.hstore.callbacks.LocalInitQueueCallback;
import frontend.edu.brown.hstore.callbacks.LocalPrepareCallback;
import frontend.edu.brown.hstore.callbacks.PartitionCountingCallback;
import frontend.edu.brown.hstore.internal.StartTxnMessage;
import frontend.edu.brown.logging.LoggerUtil;
import frontend.edu.brown.logging.LoggerUtil.LoggerBoolean;
import frontend.edu.brown.profilers.TransactionProfiler;
import frontend.edu.brown.protorpc.ProtoRpcController;
import frontend.edu.brown.statistics.FastIntHistogram;
import frontend.edu.brown.statistics.Histogram;
import frontend.edu.brown.statistics.ObjectHistogram;
import frontend.edu.brown.utils.ClassUtil;
import frontend.edu.brown.utils.PartitionSet;
import frontend.edu.brown.utils.StringUtil;

/**
 * 
 * @author pavlo
 */
public class DumbTransaction extends AbstractTransaction {
    private static final Logger LOG = Logger.getLogger(DumbTransaction.class);
    private static final LoggerBoolean debug = new LoggerBoolean();
    private static final LoggerBoolean trace = new LoggerBoolean();
    static {
        LoggerUtil.attachObserver(LOG, debug, trace);
    }

 
    public DumbTransaction(HStoreSite hstore_site) {
        super(hstore_site);

    }


    @Override
    public <T extends PartitionCountingCallback<? extends AbstractTransaction>> T getInitCallback() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public <T extends PartitionCountingCallback<? extends AbstractTransaction>> T getPrepareCallback() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public <T extends PartitionCountingCallback<? extends AbstractTransaction>> T getFinishCallback() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String toStringImpl() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String debug() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
