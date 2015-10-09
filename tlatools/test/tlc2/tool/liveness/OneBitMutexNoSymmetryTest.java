/*******************************************************************************
 * Copyright (c) 2015 Microsoft Research. All rights reserved. 
 *
 * The MIT License (MIT)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software. 
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Contributors:
 *   Markus Alexander Kuppe - initial API and implementation
 ******************************************************************************/

package tlc2.tool.liveness;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tlc2.output.EC;

public class OneBitMutexNoSymmetryTest extends ModelCheckerTestCase {

	public OneBitMutexNoSymmetryTest() {
		super("OneBitMutexNoSymmetryMC", "symmetry" + File.separator + "OneBitMutex");
	}
	
	public void testSpec() {
		assertTrue(recorder.recorded(EC.TLC_FINISHED));
		assertTrue(recorder.recordedWithStringValues(EC.TLC_STATS, "244", "127", "0"));
		assertFalse(recorder.recorded(EC.GENERAL));

		// Assert it has found the temporal violation and also a counter example
		assertTrue(recorder.recorded(EC.TLC_TEMPORAL_PROPERTY_VIOLATED));
		assertTrue(recorder.recorded(EC.TLC_COUNTER_EXAMPLE));

		// Assert the error trace
		assertTrue(recorder.recorded(EC.TLC_STATE_PRINT2));
		final List<String> expectedTrace = new ArrayList<String>(17);
		//1
		expectedTrace.add("/\\ unchecked = (A :> {} @@ B :> {})\n" 
						+ "/\\ other = (A :> A @@ B :> A)\n"
						+ "/\\ x = (A :> FALSE @@ B :> FALSE)\n" 
						+ "/\\ pc = (A :> \"ncs\" @@ B :> \"ncs\")");
		//2
		expectedTrace.add("/\\ unchecked = (A :> {} @@ B :> {})\n"
						+ "/\\ other = (A :> A @@ B :> A)\n"
						+ "/\\ x = (A :> FALSE @@ B :> FALSE)\n"
						+ "/\\ pc = (A :> \"e1\" @@ B :> \"ncs\")");
		//3
		expectedTrace.add("/\\ unchecked = (A :> {B} @@ B :> {})\n"
						+ "/\\ other = (A :> A @@ B :> A)\n"
						+ "/\\ x = (A :> TRUE @@ B :> FALSE)\n"
						+ "/\\ pc = (A :> \"e2\" @@ B :> \"ncs\")");
		//4
		expectedTrace.add("/\\ unchecked = (A :> {} @@ B :> {})\n"
						+ "/\\ other = (A :> B @@ B :> A)\n"
						+ "/\\ x = (A :> TRUE @@ B :> FALSE)\n" 
						+ "/\\ pc = (A :> \"e3\" @@ B :> \"ncs\")");
		//5
		expectedTrace.add("/\\ unchecked = (A :> {} @@ B :> {})\n" 
						+ "/\\ other = (A :> B @@ B :> A)\n"
						+ "/\\ x = (A :> TRUE @@ B :> FALSE)\n" 
						+ "/\\ pc = (A :> \"e3\" @@ B :> \"e1\")");
		//6
		expectedTrace.add("/\\ unchecked = (A :> {} @@ B :> {A})\n" 
						+ "/\\ other = (A :> B @@ B :> A)\n"
						+ "/\\ x = (A :> TRUE @@ B :> TRUE)\n" 
						+ "/\\ pc = (A :> \"e3\" @@ B :> \"e2\")");
		//7
		expectedTrace.add("/\\ unchecked = (A :> {} @@ B :> {})\n" 
						+ "/\\ other = (A :> B @@ B :> A)\n"
						+ "/\\ x = (A :> TRUE @@ B :> TRUE)\n" 
						+ "/\\ pc = (A :> \"e3\" @@ B :> \"e3\")");
		//8 (Loops back to)
		expectedTrace.add("/\\ unchecked = (A :> {} @@ B :> {})\n" 
						+ "/\\ other = (A :> B @@ B :> A)\n"
						+ "/\\ x = (A :> TRUE @@ B :> TRUE)\n" 
						+ "/\\ pc = (A :> \"e3\" @@ B :> \"e4\")");
		//9
		expectedTrace.add("/\\ unchecked = (A :> {} @@ B :> {})\n" 
						+ "/\\ other = (A :> B @@ B :> A)\n"
						+ "/\\ x = (A :> TRUE @@ B :> TRUE)\n" 
						+ "/\\ pc = (A :> \"e4\" @@ B :> \"e4\")");
		//10
		expectedTrace.add("/\\ unchecked = (A :> {} @@ B :> {})\n" 
						+ "/\\ other = (A :> B @@ B :> A)\n"
						+ "/\\ x = (A :> TRUE @@ B :> FALSE)\n" 
						+ "/\\ pc = (A :> \"e4\" @@ B :> \"e5\")");
		//11
		expectedTrace.add("/\\ unchecked = (A :> {} @@ B :> {})\n" 
						+ "/\\ other = (A :> B @@ B :> A)\n"
						+ "/\\ x = (A :> FALSE @@ B :> FALSE)\n" 
						+ "/\\ pc = (A :> \"e5\" @@ B :> \"e5\")");
		//12
		expectedTrace.add("/\\ unchecked = (A :> {} @@ B :> {})\n" 
						+ "/\\ other = (A :> B @@ B :> A)\n"
						+ "/\\ x = (A :> FALSE @@ B :> FALSE)\n" 
						+ "/\\ pc = (A :> \"e1\" @@ B :> \"e5\")");
		//13
		expectedTrace.add("/\\ unchecked = (A :> {} @@ B :> {})\n" 
						+ "/\\ other = (A :> B @@ B :> A)\n"
						+ "/\\ x = (A :> FALSE @@ B :> FALSE)\n" 
						+ "/\\ pc = (A :> \"e1\" @@ B :> \"e1\")");
		//14
		expectedTrace.add("/\\ unchecked = (A :> {B} @@ B :> {})\n" 
						+ "/\\ other = (A :> B @@ B :> A)\n"
						+ "/\\ x = (A :> TRUE @@ B :> FALSE)\n" 
						+ "/\\ pc = (A :> \"e2\" @@ B :> \"e1\")");
		//15
		expectedTrace.add("/\\ unchecked = (A :> {} @@ B :> {})\n" 
						+ "/\\ other = (A :> B @@ B :> A)\n"
						+ "/\\ x = (A :> TRUE @@ B :> FALSE)\n" 
						+ "/\\ pc = (A :> \"e3\" @@ B :> \"e1\")");
		//16
		expectedTrace.add("/\\ unchecked = (A :> {} @@ B :> {A})\n" 
						+ "/\\ other = (A :> B @@ B :> A)\n"
						+ "/\\ x = (A :> TRUE @@ B :> TRUE)\n" 
						+ "/\\ pc = (A :> \"e3\" @@ B :> \"e2\")");
		//17
		expectedTrace.add("/\\ unchecked = (A :> {} @@ B :> {})\n" 
						+ "/\\ other = (A :> B @@ B :> A)\n"
						+ "/\\ x = (A :> TRUE @@ B :> TRUE)\n" 
						+ "/\\ pc = (A :> \"e3\" @@ B :> \"e3\")");
		assertTraceWith(recorder.getRecords(EC.TLC_STATE_PRINT2), expectedTrace);

		assertBackToState(8, "<Action line 76, col 13 to line 82, col 50 of module OneBitMutex>");
	}
}