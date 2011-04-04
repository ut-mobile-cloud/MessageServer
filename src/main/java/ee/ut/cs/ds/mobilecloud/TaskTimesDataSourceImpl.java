/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author madis
 */
public class TaskTimesDataSourceImpl implements TaskTimesDataSource {

	private Map<String, TaskTimes> taskTimesData;
	private static TaskTimesDataSourceImpl instance;

	private TaskTimesDataSourceImpl() {
		taskTimesData = new ConcurrentHashMap<String, TaskTimes>();
	}
	
	@Override
	public TaskTimesDataSourceImpl clearRecords() {
		taskTimesData.clear();
		return this;
	}
	public static TaskTimesDataSource getInstance() {
		if (instance == null) {
			instance = new TaskTimesDataSourceImpl();
		}
		return instance;
	}

	@Override
	public TaskTimes getTimesForTaskID(String taskID) {
		TaskTimes taskTimes = taskTimesData.get(taskID);
		return taskTimes;
	}

	@Override
	public void updateTimesForTaskID(String taskID, TaskTimes newTimes) {
		TaskTimes times = getTimesForTaskID(taskID);
		if (times != null) {
			times.updateFrom(newTimes);
		} else {
			taskTimesData.put(taskID, newTimes);
		}
	}

	@Override
	public List<TaskTimes> getAllTimes() {
		List<TaskTimes> allTimes = new ArrayList<TaskTimes>();
		
		for (TaskTimes times : taskTimesData.values()) {
			allTimes.add(times);
		}
		return allTimes;
	}
}
