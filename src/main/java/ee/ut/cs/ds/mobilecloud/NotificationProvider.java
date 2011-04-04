/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

/**
 *
 * @author madis
 */
public interface NotificationProvider {
	NotificationProvider withDeviceData(String deviceID, String... args);
	NotificationProvider withMessage(String message);
        NotificationProvider withKeyValuePairs(String... keyValuePairs);
	void send();
}
