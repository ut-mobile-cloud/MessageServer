/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

/**
 *
 * @author madis
 */
class AndroidNotificationProvider implements NotificationProvider {

	public AndroidNotificationProvider() {
	}

	@Override
	public NotificationProvider withDeviceData(String deviceID, String... args) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public NotificationProvider withMessage(String message) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void send() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
