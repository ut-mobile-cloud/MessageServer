/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

import org.junit.Ignore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 *
 * @author madis
 */
public class TaskTimesManagerTest {

	static String SAMPLE_MD5_TASK_ID = "d41d8cd98f00b204e9800998ecf8427e";
    public TaskTimesManagerTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
	@Ignore
    @Test
    public void testUpdateTimesForTask() {
		MockHttpServletRequest request = new MockHttpServletRequest();
//		request.setMethod("POST");
//		request.addParameter("action", "UpdateTimes");
//		request.addParameter("taskID", SAMPLE_MD5_TASK_ID);
//		String taskTimesJson = "{" +
//				"\"taskID\" : \"d41d8cd98f00b204e9800998ecf8427e\"," +
//				"\"runningTime\" : 0.4," +
//				"\"initiationTime\" : 1.1," +
//				"\"notificationDelivery\" : 2.2," +
//				"\"results\" : 3.3 " +
//				"}";
//		request.addParameter("taskTimes", taskTimesJson);
//		Gson gson = new GsonBuilder().create();
//		TaskTimes newTimes = gson.fromJson(taskTimesJson, TaskTimes.class);
//		TaskTimes initialTimes = new TaskTimes(SAMPLE_MD5_TASK_ID);
//		TaskTimesDataSource dataSourceMock = mock(TaskTimesDataSource.class);
//		when(dataSourceMock.getTimesForTaskID(SAMPLE_MD5_TASK_ID))
//				.thenReturn(initialTimes);
//		dataSourceMock.updateTimesForTaskID(SAMPLE_MD5_TASK_ID, initialTimes);
		
		
	}

}