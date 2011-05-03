/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud.performance;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author madis
 */
class SyncTestTimesManager {
    private static SyncTestTimesManager instance = null;
    private Map<String, SyncTestTimes> timesMap;
    
    private SyncTestTimesManager() {
        timesMap = new ConcurrentHashMap<String, SyncTestTimes>();
    }
    public static SyncTestTimesManager sharedManager() {
        if (instance == null) {
            instance = new SyncTestTimesManager();
        }
        return instance;
    }
    
    public SyncTestTimes getTimesForTestID(String testID) {
        SyncTestTimes times = timesMap.get(testID);
        
        if (times == null) {
            times = new SyncTestTimes(testID);
            timesMap.put(testID, times);
        }
        
        return times;
    }
    
    public SyncTestTimes addTimes(SyncTestTimes times) {
        SyncTestTimes presentTimes = timesMap.get(times.getTestID());
        if (presentTimes == null) {
            timesMap.put(times.getTestID(), times);
        } else {
            presentTimes.updateWith(times);
        }
        return timesMap.get(times.getTestID());
    }

    Iterable<SyncTestTimes> getAllTimes() {
        return timesMap.values();
    }
}
