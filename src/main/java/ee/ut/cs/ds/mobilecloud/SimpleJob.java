/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author madis
 */
public class SimpleJob implements Runnable {

	String results;

	@Override
	public void run() {
		doTheTask();
		notifyAuthor();
	}
	
	

	void doTheTask() {
		try {
			Thread.sleep(5000);
			results = "All done in just 5 seconds";
		} catch (InterruptedException ex) {
			Logger.getLogger(SimpleJob.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	void notifyAuthor() {
		ApnsService service = null;
		
		try {
			InputStream certStream = this.getClass().getClassLoader().getResourceAsStream("Certificates.p12");
			service = APNS.newService()
					.withCert(certStream, "iphone4")
					.withSandboxDestination()
					.build();
			service.start();
			
			// TODO: The message sent to author should contain just a note about job being done.
			// Author must then request results from server if needed
			String payload = APNS.newPayload()
					.alertBody(this.getClass()
					.getName() + ":" + results)
					.badge(1).sound("default")
					.build();
			String token = "5324075b4c07afa9e2dbe15b74dbda2227a74d5537f0d4af24cc0aecda697f1c";
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
