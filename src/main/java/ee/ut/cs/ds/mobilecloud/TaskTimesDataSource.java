/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

import java.util.List;

/**
 *
 * @author madis
 */
interface TaskTimesDataSource {
	TaskTimes getTimesForTaskID(String taskID);
	void updateTimesForTaskID(String taskID, TaskTimes newTimes);
	List<TaskTimes> getAllTimes();
	TaskTimesDataSource clearRecords();
	
}
