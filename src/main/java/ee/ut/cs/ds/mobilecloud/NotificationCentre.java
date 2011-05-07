/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

/**
 *
 * @author madis
 */
public class NotificationCentre {

	public static NotificationProvider getProviderForDevice(String device) {
		if (device.equals("iphone")) {
			return new IPhoneNotificationProvider();
		} else if (device.equalsIgnoreCase("android")) {
			return new AndroidNotificationProvider();
		}
		return null;
	}

}
