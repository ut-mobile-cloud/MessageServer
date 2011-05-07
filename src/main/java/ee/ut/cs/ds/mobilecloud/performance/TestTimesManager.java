/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud.performance;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author madis
 */
public class TestTimesManager {
    private static TestTimesManager instance = null;
    private Map<String, SyncTestTimes> syncTestTimesMap;
    private Map<String, AsyncTestTimes> asyncTestTimesMap;
    
    private TestTimesManager() {
        syncTestTimesMap = new ConcurrentHashMap<String, SyncTestTimes>();
        asyncTestTimesMap = new ConcurrentHashMap<String, AsyncTestTimes>();
    }
    public static TestTimesManager sharedManager() {
        if (instance == null) {
            instance = new TestTimesManager();
        }
        return instance;
    }
    
    public Object getTimesForTestID(String testID) {
		if (syncTestTimesMap.containsKey(testID)) {
			return syncTestTimesMap.get(testID);
		} else if (asyncTestTimesMap.containsKey(testID)) {
			return asyncTestTimesMap.get(testID);
		}
		return null;
    }

	void clearAllData() {
		syncTestTimesMap.clear();
		asyncTestTimesMap.clear();
	}

	SyncTestTimes addTimes(SyncTestTimes times) {
		SyncTestTimes presentTimes = syncTestTimesMap.get(times.getTestID());
		if (presentTimes == null) {
			syncTestTimesMap.put(times.getTestID(), times);
		} else {
			presentTimes.updateWith(times);
		}
		return syncTestTimesMap.get(times.getTestID());
	}

	AsyncTestTimes addTimes(AsyncTestTimes times) {
		AsyncTestTimes presentTimes = asyncTestTimesMap.get(times.getTestID());
		if (presentTimes == null) {
			asyncTestTimesMap.put(times.getTestID(), times);
		} else {
			presentTimes.updateWith(times);
		}
		return asyncTestTimesMap.get(times.getTestID());
	}
    
	public Collection<Object> getAllTimes() {
		Collection <Object> allTimes = new HashSet<Object>();
		allTimes.add(syncTestTimesMap.values());
		allTimes.add(asyncTestTimesMap.values());
		return allTimes;
	}
    
}
