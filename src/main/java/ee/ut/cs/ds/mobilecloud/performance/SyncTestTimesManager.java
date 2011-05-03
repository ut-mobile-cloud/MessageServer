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
    
    private Map<String, SyncTestTimes> timesMap;
    private SyncTestTimesManager() {
        timesMap = new ConcurrentHashMap<String, SyncTestTimes>();
    }
    public static SyncTestTimesManager sharedManager() {
        SyncTestTimesManager instance = null;
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
}
