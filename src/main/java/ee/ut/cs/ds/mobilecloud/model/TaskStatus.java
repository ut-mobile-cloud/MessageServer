/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author madis
 */
public class TaskStatus {
    public enum Status {
        NEW,
        WAITING,
        RUNNING,
        COMPLETED,
        ERROR
    }
    String taskID;
    String description;
    Status status;
    
    public TaskStatus(String taskID, String description, Status status) {
        this.taskID = taskID;
        this.description = description;
        this.status = status;
    }
    
    String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }
}
