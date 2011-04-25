/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author madis
 */
public class ResultsManager {
	private static ResultsManager instance = new ResultsManager();
	private Map <String, String> results;
	
	private ResultsManager() {
		results = new HashMap<String, String>();
	}
	
	public void addResults(String taskID, String result) {
		
		results.put(taskID, result);
	}
	
	public String getResultForTaskID(String taskID) {
		return results.containsKey(taskID) ? results.get(taskID) : "Result not found";
	}
	
	public static ResultsManager getInstance() {
		if (instance == null) {
			instance = new ResultsManager();
		}
		return instance;
	}

}
