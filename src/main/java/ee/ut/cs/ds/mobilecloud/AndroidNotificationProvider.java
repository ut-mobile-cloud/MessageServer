/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author madis
 */
class AndroidNotificationProvider implements NotificationProvider {

    String token;
    String message;
    Map<String, String[]> customFields;
    final String collapseKey = "C2DMNotification";

    public AndroidNotificationProvider() {
    }

    @Override
    public NotificationProvider withDeviceData(String deviceID, String... args) {
        this.token = deviceID;
        return this;
    }

    @Override
    public NotificationProvider withMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public void send() {
        try {
            C2DMessaging messageController = new C2DMessaging();
            messageController.sendNoRetry(this.message, collapseKey, this.customFields, true);
        } catch (IOException ex) {
            Logger.getLogger(AndroidNotificationProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public NotificationProvider withKeyValuePairs(String... keyValuePairs) {
        Map<String, String[]> params = new HashMap<String, String[]>();
        int len = keyValuePairs.length;
        if (len % 2 == 1) {
            len--;
        }
        for (int i = 0; i < len; i += 2) {
            String name = keyValuePairs[i];
            String value = keyValuePairs[i + 1];
            if (name != null && value != null) {
                params.put("data." + name, new String[]{value});
            }
        }
        return this;
    }
}
