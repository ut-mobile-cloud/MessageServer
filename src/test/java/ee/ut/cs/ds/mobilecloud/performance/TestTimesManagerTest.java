/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud.performance;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestTimesManagerTest {
	
	TestTimesManager manager;
	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		manager = TestTimesManager.sharedManager();
	}
	
	@After
	public void tearDown() {
		manager.clearAllData();
	}

	@Test
	public void testSharedManager() {
		TestTimesManager expResult = manager;
		TestTimesManager result = TestTimesManager.sharedManager();
		assertEquals(expResult, result);
	}
	
	@Test
	public void testAddSyncTimes() {
		String testID = "1";
		SyncTestTimes times = new SyncTestTimes(testID);
		manager.addTimes(times);
		SyncTestTimes timesFromManager = (SyncTestTimes)manager.getTimesForTestID(testID);
		assertThat(times, is(equalTo(timesFromManager)));
		
		
	}
	
	private void addPredeterminedAsyncTimes() {
		AsyncTestTimes timesA = new AsyncTestTimes("A1");
		AsyncTestTimes timesB = new AsyncTestTimes("A2");
		manager.addTimes(timesA);
		manager.addTimes(timesB);
	}
	private void addPredeterminedSyncTimes() {
		SyncTestTimes timesA = new SyncTestTimes("S1");
		SyncTestTimes timesB = new SyncTestTimes("S2");
		manager.addTimes(timesA);
		manager.addTimes(timesB);
	}
	/**
	 * Test of getTimesForTestID method, of class TestTimesManager.
	 */
	@Test
	public void testGetTimesForTestID() {
		addPredeterminedAsyncTimes();
		AsyncTestTimes timesA = (AsyncTestTimes)manager.getTimesForTestID("A1");
		assertThat(timesA.getTestID(), is(equalTo("A1")));
		assertThat(timesA, is(instanceOf(AsyncTestTimes.class)));
	}

}
