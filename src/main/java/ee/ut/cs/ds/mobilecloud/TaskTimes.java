/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

/**
 *
 * @author madis
 */
public class TaskTimes {
	private String taskID;
	private String description;
	private Float initiationTime; // The time it took for client device to request a job to be started and get response
	private Float runningTime; // The time it took to run the job
	private Float notificationDelivery; // Time between the start of sending push notification and the time that device receives it. Could be hard to measure
	private Float results; // Time between client's request for results and the time he receives them.

	public TaskTimes(String taskID) {
		this.taskID = taskID;
		setTimeValuesToIllegal();
	}
	public TaskTimes() {
		setTimeValuesToIllegal();
	}
	
	private void setTimeValuesToIllegal() {
		initiationTime = new Float(-1);
		runningTime = new Float(-1);
		notificationDelivery = new Float(-1);
		results = new Float(-1);
	}
	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public Float getInitiationTime() {
		return initiationTime;
	}

	public void setInitiationTime(Float initiationTime) {
		this.initiationTime = initiationTime;
	}

	public Float getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(Float runningTime) {
		this.runningTime = runningTime;
	}

	public Float getNotificationDelivery() {
		return notificationDelivery;
	}

	public void setNotificationDelivery(Float notificationDelivery) {
		this.notificationDelivery = notificationDelivery;
	}

	public Float getResults() {
		return results;
	}

	public void setResults(Float results) {
		this.results = results;
	}

	void updateFrom(TaskTimes newTimes) {
		/**
		 * Times should be updated only if
		 *	1) particular field has not already been set (ie. it's value is -1)
		 *	2) the field that it is updated from has been set (it's value is >= 0)
		 */
		if(getInitiationTime() < 0 && newTimes.getInitiationTime() >= 0) {
			setInitiationTime(newTimes.getInitiationTime());
		}
		if(getRunningTime() < 0 && newTimes.getRunningTime() >= 0) {
			setRunningTime(newTimes.getRunningTime());
		}
		if(getNotificationDelivery() < 0 && newTimes.getNotificationDelivery() >= 0) {
			setNotificationDelivery(newTimes.getNotificationDelivery());
		}
		if(getResults() < 0 && newTimes.getResults() >= 0) {
			setResults(newTimes.getResults());
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof TaskTimes) {
			TaskTimes that = (TaskTimes)other;
			if (this.getTaskID().equals(that.getTaskID()) &&
					this.getInitiationTime().equals(that.getInitiationTime()) &&
					this.getRunningTime().equals(that.getRunningTime()) &&
					this.getResults().equals(that.getResults()) &&
					this.getNotificationDelivery().equals(that.getNotificationDelivery())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 53 * hash + (this.taskID != null ? this.taskID.hashCode() : 0);
		hash = 53 * hash + (this.initiationTime != null ? this.initiationTime.hashCode() : 0);
		hash = 53 * hash + (this.runningTime != null ? this.runningTime.hashCode() : 0);
		hash = 53 * hash + (this.notificationDelivery != null ? this.notificationDelivery.hashCode() : 0);
		hash = 53 * hash + (this.results != null ? this.results.hashCode() : 0);
		return hash;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
