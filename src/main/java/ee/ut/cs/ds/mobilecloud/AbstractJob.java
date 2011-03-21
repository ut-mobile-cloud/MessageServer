/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

import com.google.gson.annotations.Expose;
import java.io.File;
import java.util.List;
import org.codehaus.jackson.map.type.SimpleType;


/**
 *
 * @author madis
 */
public abstract class AbstractJob implements Runnable {
	List<File> data;
	
	@Expose private List<String> parameters;
	@Expose private String ownerDevice;
	@Expose private String deviceID;
	@Expose private String taskID;
	@Expose private String description;
	private String results;
	
	
	@Override
	public void run() {
		performTask();
		notifyOwner();
	}
	
	abstract public void performTask();
	SimpleType x;
	public void  notifyOwner() {
		NotificationCentre.getProviderForDevice(getOwnerDevice())
				.withDeviceData(getDeviceID())
				.withMessage(getResults())
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
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

}
