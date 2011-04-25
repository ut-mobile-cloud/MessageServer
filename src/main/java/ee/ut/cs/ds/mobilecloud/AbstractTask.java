/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud;

import com.google.gson.annotations.Expose;
import ee.ut.cs.ds.mobilecloud.model.TaskStatus.Status;
import java.io.File;
import java.util.List;
import org.codehaus.jackson.map.type.SimpleType;

/**
 *
 * @author madis
 */
public abstract class AbstractTask implements Runnable {

    List<File> data;
    @Expose
    private List<String> parameters;
    @Expose
    private String ownerDevice;
    @Expose
    private String deviceID;
    @Expose
    private String taskID;
    @Expose
    private String description;
    private String results;

    @Override
    public void run() {
		TaskStatusDataSourceImpl.getInstance()
				.getStatus(taskID)
				.setStatus(Status.RUNNING);
        long startTime = System.currentTimeMillis();
		performTask();
		float duration = ((float)(System.currentTimeMillis() - startTime)) / 1000;
		updateTaskRunningTime(duration);
		TaskStatusDataSourceImpl.getInstance().getStatus(taskID).setStatus(Status.COMPLETED);
		storeResult();
        notifyOwner();
    }

    abstract public void performTask();
	
	void updateTaskRunningTime(float duration) {
		TaskTimes times = new TaskTimes(taskID);
		times.setDescription(getDescription());
		times.setRunningTime(new Float(duration));
		TaskTimesDataSourceImpl.getInstance().updateTimesForTaskID(taskID, times);
	}
	
	public void storeResult() {
		ResultsManager.getInstance().addResults(taskID, results);
	}
	
    public void notifyOwner() {
	NotificationCentre.getProviderForDevice(getOwnerDevice())
			.withDeviceData(getDeviceID())
			.withMessage(getMessage())
			.withKeyValuePairs("taskID", taskID)
			.send();
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public String getOwnerDevice() {
        return ownerDevice;
    }

    public void setOwnerDevice(String ownerDevice) {
        this.ownerDevice = ownerDevice;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResults() {
        return results == null ? "" : results;
    }

    public void setResults(String results) {
        this.results = results;
    }

	private String getMessage() {
		return "MCM task complete";
	}
}
