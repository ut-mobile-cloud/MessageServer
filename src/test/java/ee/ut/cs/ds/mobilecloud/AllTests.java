/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author madis
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ee.ut.cs.ds.mobilecloud.TestTaskTimes.class,ee.ut.cs.ds.mobilecloud.TaskTimesDataSourceImplTest.class,ee.ut.cs.ds.mobilecloud.TaskTimesManagerTest.class})
public class AllTests {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

}