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

package frontend.voltdb.jni;

import java.io.File;

import frontend.voltdb.DependencySet;
import frontend.voltdb.ParameterSet;
import frontend.voltdb.SysProcSelector;
import frontend.voltdb.TableStreamType;
import frontend.voltdb.VoltTable;
import frontend.voltdb.VoltTable.ColumnInfo;
import frontend.voltdb.VoltType;
import frontend.voltdb.catalog.Table;
import frontend.voltdb.exceptions.EEException;
import frontend.voltdb.export.ExportProtoMessage;
import frontend.voltdb.utils.NotImplementedException;
import frontend.voltdb.utils.DBBPool.BBContainer;
import frontend.voltdb.catalog.Table;
import frontend.voltdb.export.ExportProtoMessage;
import frontend.voltdb.utils.DBBPool;
import frontend.voltdb.catalog.Table;
import frontend.voltdb.export.ExportProtoMessage;
import frontend.voltdb.utils.DBBPool;

public class MockExecutionEngine extends ExecutionEngine {

    public MockExecutionEngine() {
        super(null);
    }

    @Override
    public DependencySet executePlanFragment(final long planFragmentId, int outputDepId,
            int inputDepIdfinal, ParameterSet parameterSet, final long txnId,
            final long lastCommittedTxnId, final long undoToken) throws EEException
    {
        // Create a mocked up dependency pair. Required by some tests
        VoltTable vt;
        vt = new VoltTable(new ColumnInfo[] {
                           new ColumnInfo("foo", VoltType.INTEGER)});
        vt.addRow(Integer.valueOf(1));
        return new DependencySet(outputDepId, vt);
    }

    @Override
    public DependencySet executeQueryPlanFragmentsAndGetDependencySet(
            long[] planFragmentIds,
            int numFragmentIds,
            int[] input_depIds,
            int[] output_depIds,
            ParameterSet[] parameterSets,
            int numParameterSets,
            long txnId, long lastCommittedTxnId, long undoToken) throws EEException {
        
        // TODO
        return (null);
    }

    @Override
    public VoltTable executeCustomPlanFragment(final String plan, int outputDepId,
            int inputDepId, final long txnId, final long lastCommittedTxnId, final long undoQuantumToken)
            throws EEException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VoltTable[] executeQueryPlanFragmentsAndGetResults(final long[] planFragmentIds, final int numFragmentIds,
            final int[] input_depIds,
            final int[] output_depIds,
            final ParameterSet[] parameterSets,
            final int numParameterSets, final long txnId, final long lastCommittedTxnId, final long undoToken) throws EEException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VoltTable[] getStats(final SysProcSelector selector, final int[] locators, boolean interval, Long now) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void loadCatalog(final String serializedCatalog) throws EEException {
        // TODO Auto-generated method stub
    }

    @Override
    public void updateCatalog(final String catalogDiffs, int catalogVersion) throws EEException {
        // TODO Auto-generated method stub
    }

    @Override
    public void loadTable(final int tableId, final VoltTable table, final long txnId,
        final long lastCommittedTxnId, final long undoToken, final boolean allowExport)
    throws EEException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void release() throws EEException {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean releaseUndoToken(final long undoToken) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public VoltTable serializeTable(final int tableId) throws EEException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void tick(final long time, final long lastCommittedTxnId) {
        // TODO Auto-generated method stub
    }

    @Override
    public int toggleProfiler(final int toggle) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean undoUndoToken(final long undoToken) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setLogLevels(final long logLevels) throws EEException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void quiesce(long lastCommittedTxnId) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean activateTableStream(int tableId, TableStreamType type) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int tableStreamSerializeMore(DBBPool.BBContainer c, int tableId, TableStreamType type) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ExportProtoMessage exportAction(boolean ackAction, boolean pollAction,
										   boolean resetAction, boolean syncAction,
										   long ackOffset, long seqNo, int partitionId, long mTableId) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void processRecoveryMessage( java.nio.ByteBuffer buffer, long pointer) {
        // TODO Auto-generated method stub

    }

    @Override
    public long tableHashCode( int tableId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashinate(Object value, int partitionCount) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void trackingEnable(Long txnId) throws EEException {
        // TODO Auto-generated method stub
    }
    @Override
    public void trackingFinish(Long txnId) throws EEException {
        // TODO Auto-generated method stub
    }
    @Override
    public VoltTable trackingReadSet(Long txnId) throws EEException {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public VoltTable trackingWriteSet(Long txnId) throws EEException {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void antiCacheInitialize(File dbFilePath, long blockSize) throws EEException {
        // TODO Auto-generated method stub
    }
    @Override
    public void antiCacheReadBlocks(Table catalog_tbl, short[] block_ids, int[] tuple_offsets) {
        // TODO Auto-generated method stub
    }
    @Override
    public void antiCacheMergeBlocks(Table catalog_tbl) {
        // TODO Auto-generated method stub
    }

    @Override
    public VoltTable antiCacheEvictBlock(Table catalog_tbl, long block_size, int num_blocks) {
        // TODO Auto-generated method stub
        return (null);
    }
    
    @Override
    public void MMAPInitialize(File dbDir, long mapSize, long syncFrequency) throws EEException {
     // TODO Auto-generated method stub        
    }
    
    @Override
    public void ARIESInitialize(File dbDir, File logFile) throws EEException {
     // TODO Auto-generated method stub        
    }

    @Override
    public long getArieslogBufferLength() { 
    // XXX: do nothing, we only implement this for JNI now.
        return 0;
    }

    @Override
    public void getArieslogData(int bufferLength, byte[] arieslogDataArray) { 
    // XXX: do nothing, we only implement this for JNI now.
    }

    @Override
    public void doAriesRecoveryPhase(long replayPointer, long replayLogSize, long replayTxnId) { 
    // TODO Auto-generated method stub
    }

    @Override
    public void freePointerToReplayLog(long ariesReplayPointer) {
        // TODO Auto-generated method stub
    }

    @Override
    public long readAriesLogForReplay(long[] size) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long extractTable(Table extractTable, String destinationShim, String destinationFile, boolean caching) {
        // TODO Auto-generated method stub
        return 0L;
    }
    
    @Override
    public long loadTableFromFile(Table loadTable, String destinationShim, String destinationFile) {
        // TODO Auto-generated method stub
        return 0L;
    }
    
}
