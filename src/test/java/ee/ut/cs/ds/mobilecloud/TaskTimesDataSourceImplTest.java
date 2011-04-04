/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

import java.util.List;
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
public class TaskTimesDataSourceImplTest {
	static String SampleTaskID1 = "XXd909c290d0fb1ca068ffaddf22cbd0";
	static String SampleTaskID2 = "YYd909c290d0fb1ca068ffaddf22cbd0";
	static String SampleTaskID3 = "ZZd909c290d0fb1ca068ffaddf22cbd0";
	
	TaskTimesDataSource dataSource;
	
    public TaskTimesDataSourceImplTest() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

    @Before
    public void setUp() {
		dataSource = TaskTimesDataSourceImpl.getInstance().clearRecords();
    }

    @After
    public void tearDown() {
    }
	
	
	@Test
	public void testAddNewTimes() {
		TaskTimes t1 = new TaskTimes(SampleTaskID1);
		t1.setInitiationTime(new Float(1.1));
		dataSource.updateTimesForTaskID(SampleTaskID1, t1);
		assertThat(dataSource.getTimesForTaskID(SampleTaskID1), is(equalTo(t1)));
	}
	
	@Test
	public void testUpdateTimeForExistingTask() {
		TaskTimes t1 = new TaskTimes(SampleTaskID1);
		t1.setInitiationTime(new Float(1.1));
		dataSource.updateTimesForTaskID(SampleTaskID1, t1);
		t1.setResults(new Float(3.3));
		dataSource.updateTimesForTaskID(SampleTaskID1, t1);
		assertThat(dataSource.getTimesForTaskID(SampleTaskID1), is(equalTo(t1)));
	}
	
	@Test
	public void testGetAllTimes() {
		TaskTimes t1 = new TaskTimes(SampleTaskID1);
		dataSource.updateTimesForTaskID(SampleTaskID1, t1);
		TaskTimes t2 = new TaskTimes(SampleTaskID2);
		dataSource.updateTimesForTaskID(SampleTaskID2, t2);
		TaskTimes t3 = new TaskTimes(SampleTaskID3);
		dataSource.updateTimesForTaskID(SampleTaskID3, t3);
		
		List<TaskTimes> allTimes = dataSource.getAllTimes();
		
		assertThat(allTimes.size(), is(equalTo(3)));
		assertThat(allTimes, hasItem(t1));
		assertThat(allTimes, hasItem(t2));
		assertThat(allTimes, hasItem(t3));
	}

}