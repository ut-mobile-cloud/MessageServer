/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import java.io.InputStream;

/**
 *
 * @author madis
 */
class IPhoneNotificationProvider implements NotificationProvider {

	String token;
	String message;

	public IPhoneNotificationProvider() {
	}

	@Override
	public NotificationProvider withDeviceData(String deviceID, String... args) {
		token = deviceID;
		return this;
	}

	@Override
	public NotificationProvider withMessage(String message) {
		this.message = message;
		return this;
	}

	@Override
	public void send() {
		ApnsService service = null;

		try {
			InputStream certStream = this.getClass().getClassLoader().getResourceAsStream("Certificates.p12");
			APNS.newPayload();
			service = APNS.newService()
					.withCert(certStream, "iphone4")
					.withSandboxDestination()
					.build();
			
			service.start();

			// TODO: The message sent to author should contain just a note about job being done.
			// Author must then request results from server if needed
			
			String payload = APNS.newPayload()
					.alertBody("Vastus : " + message)
					.badge(1).sound("default")
					.build();
			service.push(token, payload);
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		} finally {
			if (service != null) {
				service.stop();
			}
		}
	}
}
