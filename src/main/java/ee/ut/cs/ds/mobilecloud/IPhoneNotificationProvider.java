/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author madis
 */
class IPhoneNotificationProvider implements NotificationProvider {

    String token;
    String message;
    Map<String, String> customFields;
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
    public NotificationProvider withKeyValuePairs(String... keyValuePairs) {
        int pairs = keyValuePairs.length % 2 == 0 ? keyValuePairs.length : keyValuePairs.length - 1;
        if (customFields == null) {
            customFields = new HashMap<String, String>();
        }
        for (int i = 0; i < pairs; i+=2) {
            customFields.put(keyValuePairs[i], keyValuePairs[i+1]);
        }
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

            String payload = APNS.newPayload()
                    .alertBody(message)
                    .badge(1)
                    .customFields(customFields)
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
