/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
/**
 *
 * @author madis
 */
public class TestTaskTimes {

    public TestTaskTimes() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

	@Test
    public void testEqual() {
		TaskTimes timesA = new TaskTimes("XXd909c290d0fb1ca068ffaddf22cbd0");
		timesA.setInitiationTime(new Float(0.1));
		timesA.setRunningTime(new Float(0.2));
		timesA.setNotificationDelivery(new Float(0.3));
		timesA.setResults(new Float(0.4));
		
		TaskTimes timesB = new TaskTimes("XXd909c290d0fb1ca068ffaddf22cbd0");
		timesB.setInitiationTime(new Float(0.1));
		timesB.setRunningTime(new Float(0.2));
		timesB.setNotificationDelivery(new Float(0.3));
		timesB.setResults(new Float(0.4));
		assertThat(timesA, is(equalTo(timesB)));
				
	}
	
	@Test
	public void testNotEqual() {
		TaskTimes timesA = new TaskTimes("XXd909c290d0fb1ca068ffaddf22cbd0");
		timesA.setInitiationTime(new Float(0.1));
		timesA.setRunningTime(new Float(0.2));
		timesA.setNotificationDelivery(new Float(0.3));
		timesA.setResults(new Float(0.4));
		TaskTimes timesB = new TaskTimes("XXd909c290d0fb1ca068ffaddf22cbd0");
		timesB.setInitiationTime(new Float(0.3)); // Different value here
		timesB.setRunningTime(new Float(0.2));
		timesB.setNotificationDelivery(new Float(0.3));
		timesB.setResults(new Float(0.4));
		
		assertFalse(timesA.equals(timesB));
	}

}