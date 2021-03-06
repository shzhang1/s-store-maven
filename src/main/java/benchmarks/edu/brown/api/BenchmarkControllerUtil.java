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

package benchmarks.edu.brown.api;

import frontend.voltdb.SysProcSelector;

import frontend.edu.brown.statistics.Histogram;
import frontend.edu.brown.statistics.HistogramUtil;

/**
 * Utility methods for the BenchmarkController
 * @author pavlo
 */
public abstract class BenchmarkControllerUtil {

    public static class ProfilingOutput {
        public final SysProcSelector key;
        public final String clientParam;
        public final String siteParam;
        private ProfilingOutput(SysProcSelector key, String clientParam, String siteParam) {
            this.key = key;
            this.clientParam = clientParam;
            this.siteParam = siteParam;
        }
        @Override
        public String toString() {
            return String.format("%s/%s/%s", this.key, this.clientParam, this.siteParam);
        }
    }
    
    /**
     * For each client output option, we'll enable the corresponding
     * site config parameter so that we can collect the proper data
     */
    public static final ProfilingOutput[] PROFILING_OUTPUTS = {
        new ProfilingOutput(SysProcSelector.TABLE, "client.output_table_stats", null),
        new ProfilingOutput(SysProcSelector.PROCEDURE, "client.output_procedure_stats", null), // added by hawk, 2013/11/25
        //new ProfilingOutput(SysProcSelector.TRIGGER, "client.output_trigger_stats", null), // added by hawk, 2013/12/6
        //new ProfilingOutput(SysProcSelector.STREAM, "client.output_stream_stats", null), // added by hawk, 2013/12/13
        new ProfilingOutput(SysProcSelector.EXECPROFILER, "client.output_exec_profiling", "site.exec_profiling"),
        new ProfilingOutput(SysProcSelector.QUEUEPROFILER, "client.output_queue_profiling", "site.queue_profiling"),
        new ProfilingOutput(SysProcSelector.TXNPROFILER, "client.output_txn_profiling", "site.txn_profiling"),
        new ProfilingOutput(SysProcSelector.SITEPROFILER, "client.output_site_profiling", "site.profiling"),
        new ProfilingOutput(SysProcSelector.SPECEXECPROFILER, "client.output_specexec_profiling", "site.specexec_profiling"),
        new ProfilingOutput(SysProcSelector.MARKOVPROFILER, "client.output_markov_profiling", "site.markov_profiling"),
        new ProfilingOutput(SysProcSelector.PLANNERPROFILER, "client.output_planner_profiling", "site.planner_profiling"),
        new ProfilingOutput(SysProcSelector.TXNCOUNTER, "client.output_txn_counters", "site.txn_counters"),
        new ProfilingOutput(SysProcSelector.ANTICACHE, "client.output_anticache_profiling", "site.anticache_profiling"),
        new ProfilingOutput(SysProcSelector.ANTICACHEEVICTIONS, "client.output_anticache_evictions", "site.anticache_profiling"),
        new ProfilingOutput(SysProcSelector.ANTICACHEACCESS, "client.output_anticache_access", "site.anticache_profiling"),
    };
    
    
    public static String getClientName(String host, int id) {
        return String.format("%s-%03d", host, id);
    }
    
    /**
     * Return an array with stats about latencies recorded in a Histogram:
     * <ol>
     *  <li> Min Latency
     *  <li> Max Latency
     *  <li> Average Latency
     *  <li> Stdev Latency
     * </ol>
     * @param latencies
     * @return
     */
    public static double[] computeLatencies(Histogram<Integer> latencies) {
        double minLatency = -1;
        double avgLatency = -1;
        double maxLatency = -1;
        double stdDevLatency = -1;
        
        if (latencies.getSampleCount() > 0) {
            Integer val = latencies.getMinValue();
            if (val != null) minLatency = val.doubleValue();
            
            val = latencies.getMaxValue();
            if (val != null) maxLatency = val.doubleValue();
            
            avgLatency = HistogramUtil.sum(latencies) / (double)latencies.getSampleCount();
            stdDevLatency = HistogramUtil.stdev(latencies);
        }
        
        return new double[]{ minLatency, maxLatency, avgLatency, stdDevLatency };
    }
    
}
