/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud;

import ee.ut.cs.ds.mobilecloud.model.TaskStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author madis
 */
public class TaskStatusDataSourceImpl implements TaskStatusDataSource {
	private static TaskStatusDataSource instance;
	private Map<String, TaskStatus> statusData;
	
	private TaskStatusDataSourceImpl() {
		statusData = new HashMap<String, TaskStatus>();
	}
	public static TaskStatusDataSource getInstance() {
		if (instance == null) {
			instance = new TaskStatusDataSourceImpl();
		}
		return instance;
	}
	
	public TaskStatus getStatus(String taskID) {
		TaskStatus requestedStatus = statusData.get(taskID);
		if (requestedStatus == null) {
			requestedStatus = new TaskStatus(taskID);
			statusData.put(taskID, requestedStatus);
		}
		return requestedStatus;
	}
	
	@Override
	public List<TaskStatus> getAllStatuses() {
		List<TaskStatus> statusList = new ArrayList<TaskStatus>(statusData.values());
		return statusList;
	}
	
}
