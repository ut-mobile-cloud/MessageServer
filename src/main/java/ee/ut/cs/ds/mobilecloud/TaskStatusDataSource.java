/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud;

import ee.ut.cs.ds.mobilecloud.model.TaskStatus;
import java.util.List;

/**
 *
 * @author madis
 */
public interface TaskStatusDataSource {
	TaskStatus getStatus(String taskID);
	List<TaskStatus> getAllStatuses();
}
