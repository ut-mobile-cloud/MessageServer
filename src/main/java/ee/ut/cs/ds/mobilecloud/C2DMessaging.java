/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

/**
 *
 * @author Carlos
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.URL;
import java.net.URLEncoder;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

public class C2DMessaging {
    private static final String UPDATE_CLIENT_AUTH = "Update-Client-Auth";



    public static final String PARAM_REGISTRATION_ID = "registration_id";

    public static final String PARAM_DELAY_WHILE_IDLE = "delay_while_idle";

    public static final String PARAM_COLLAPSE_KEY = "collapse_key";

    private static final String UTF8 = "UTF-8";

    public static final int DATAMESSAGING_MAX_JITTER_MSEC = 3000;

    static C2DMessaging singleton;

    C2DMConfigLoader serverConfig;
    
    protected C2DMessaging() {
        serverConfig = null;
        serverConfig = new C2DMConfigLoader();
    }

    private C2DMessaging(C2DMConfigLoader serverConfig) {
        this.serverConfig = serverConfig;
    }

    public synchronized static C2DMessaging get(ServletContext servletContext) {
        if (singleton == null) {
            C2DMConfigLoader serverConfig = new C2DMConfigLoader();
            singleton = new C2DMessaging(serverConfig);
        }
        return singleton;
    }

    C2DMConfigLoader getServerConfig() {
        return serverConfig;
    }

    private static class NullHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public String sendNoRetry(String registrationId,
            String collapse,
            Map<String, String[]> params,
            boolean delayWhileIdle)
        throws IOException {

        // Send a sync message to this Android device.
        StringBuilder postDataBuilder = new StringBuilder();
        postDataBuilder.append(PARAM_REGISTRATION_ID).
            append("=").append(registrationId);

        if (delayWhileIdle) {
            postDataBuilder.append("&")
                .append(PARAM_DELAY_WHILE_IDLE).append("=1");
        }
        postDataBuilder.append("&").append(PARAM_COLLAPSE_KEY).append("=").
            append(collapse);

        for (Object keyObj: params.keySet()) {
            String key = (String) keyObj;
            if (key.startsWith("data.")) {
                String[] values = params.get(key);
                postDataBuilder.append("&").append(key).append("=").
                    append(URLEncoder.encode(values[0], UTF8));
            }
        }

        byte[] postData = postDataBuilder.toString().getBytes(UTF8);

        // Hit the dm URL.
        URL url = new URL(serverConfig.getC2DMUrl());

        //HttpURLConnection con1 = (HttpURLConnection) url.openConnection();
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setHostnameVerifier(new NullHostnameVerifier());
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
        String authToken = serverConfig.getToken();
        conn.setRequestProperty("Authorization", "GoogleLogin auth=" + authToken);

        OutputStream out = conn.getOutputStream();
        out.write(postData);
        out.close();

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpServletResponse.SC_UNAUTHORIZED ||
                responseCode == HttpServletResponse.SC_FORBIDDEN) {
            // The token is too old - return false to retry later, will fetch the token
            // from DB. This happens if the password is changed or token expires. Either admin
            // is updating the token, or Update-Client-Auth was received by another server,
            // and next retry will get the good one from database.
//            log.warning("Unauthorized - need token");
            serverConfig.invalidateCachedToken();
            //return false;
            return "Error: UNAUTHORIZED OR FORBIDDEN";
        }

        // Check for updated token header
        String updatedAuthToken = conn.getHeaderField(UPDATE_CLIENT_AUTH);
        if (updatedAuthToken != null && !authToken.equals(updatedAuthToken)) {
            /*log.info("Got updated auth token from datamessaging servers: " +
                    updatedAuthToken);
            serverConfig.updateToken(updatedAuthToken);*/
        }

        String responseLine = new BufferedReader(new InputStreamReader(conn.getInputStream()))
            .readLine();

        // NOTE: You *MUST* use exponential backoff if you receive a 503 response code.
        // Since App Engine's task queue mechanism automatically does this for tasks that
        // return non-success error codes, this is not explicitly implemented here.
        // If we weren't using App Engine, we'd need to manually implement this.
        if (responseLine == null || responseLine.equals("")) {
     //       log.info("Got " + responseCode +
       //             " response from Google AC2DM endpoint.");
            throw new IOException("Got empty response from Google AC2DM endpoint.");
        }

        String[] responseParts = responseLine.split("=", 2);
        if (responseParts.length != 2) {
//            log.warning("Invalid message from google: " +
   //                 responseCode + " " + responseLine);
            throw new IOException("Invalid response from Google " +
                    responseCode + " " + responseLine);
        }

        if (responseParts[0].equals("id")) {
            //log.info("Successfully sent data message to device: " + responseLine);
            //return true;
        	return "Message Sent Successfully";
        }

        if (responseParts[0].equals("Error")) {
            String err = responseParts[1];
//            log.warning("Got error response from Google datamessaging endpoint: " + err);
            // No retry.
            // TODO(costin): show a nicer error to the user.
            throw new IOException(err);
        } else {
            // 500 or unparseable response - server error, needs to retry
            //log.warning("Invalid response from google " + responseLine + " " +
              //      responseCode);
            return "500 or unparseable response - server error"+responseLine+" "+responseCode;
        }
    }

    public String sendNoRetry(String token, String collapseKey,
            String name1, String value1, String name2, String value2,
            String name3, String value3)
                throws IOException {

        Map<String, String[]> params = new HashMap<String, String[]>();
        if (value1 != null) params.put("data." + name1, new String[] {value1});
        if (value2 != null) params.put("data." + name2, new String[] {value2});
        if (value3 != null) params.put("data." + name3, new String[] {value3});

        try {
            return sendNoRetry(token, collapseKey, params, true);
        } catch (IOException ex) {
            //return false;
        	return ex.toString();
        }
    }

    public String sendNoRetry(String token, String collapseKey,
            String... nameValues)
                throws IOException {

        Map<String, String[]> params = new HashMap<String, String[]>();
        int len = nameValues.length;
        if (len % 2 == 1) {
            len--; // ignore last
        }
        for (int i = 0; i < len; i+=2) {
            String name = nameValues[i];
            String value = nameValues[i + 1];
            if (name != null && value != null) {
                params.put("data." + name, new String[] {value});
            }
        }

        try {
            return sendNoRetry(token, collapseKey, params, true);
        } catch (IOException ex) {
            return ex.toString();
        }
    }

}
