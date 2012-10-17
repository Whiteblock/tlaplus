// Copyright (c) 2012 Microsoft Corporation.  All rights reserved.
package tlc2.tool.fp;

import java.rmi.RemoteException;

public class MSBDiskFPSetTest2 extends AbstractHeapBasedDiskFPSetTest {
	
	/* (non-Javadoc)
	 * @see tlc2.tool.fp.HeapBasedDiskFPSetTest#getDiskFPSet(tlc2.tool.fp.FPSetConfiguration)
	 */
	protected DiskFPSet getDiskFPSet(final FPSetConfiguration fpSetConfig) throws RemoteException {
		return new MSBDiskFPSet(fpSetConfig);
	}

	/* (non-Javadoc)
	 * @see tlc2.tool.fp.HeapBasedDiskFPSetTest#getLowerLimit()
	 */
	protected long getLowerLimit() {
		return 1L << 8;
	}
}