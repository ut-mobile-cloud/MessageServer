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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

class C2DMConfigLoader {

	String currentToken;
	String c2dmUrl = "https://android.apis.google.com/c2dm/send";
	String emailAddress = "davidpaniagualiv@gmail.com";

	public void invalidateCachedToken() {
		currentToken = null;
	}

	/**
	 * Return the auth token from the database. Should be called only if the old
	 * token expired.
	 *
	 * @return
	 */
	public String getToken() {
		if (currentToken == null) {
			//HttpManager httpManager = new HttpManager();
			/*
			 * currentToken = httpManager.postGetString(
			 * httpManager.URL_AUTHENTICATE,
			 * httpManager.generateGoogleParameters());
			 */
			Data data = new Data();
			try {
				currentToken = data.getToken(emailAddress);
			} catch (SQLException e) { // ]
			}

		}
		return currentToken;
	}

	public String getC2DMUrl() {
		if (c2dmUrl == null) {
			c2dmUrl = getDataMessagingConfig().getC2DMUrl();
		}
		return c2dmUrl;
	}

	private C2DMConfig getDataMessagingConfig() {

		C2DMConfig dmConfig = null;
		dmConfig = new C2DMConfig();
		// dmConfig.setKey(key);
		// Must be in classpath, before sending. Do not checkin !
		try {
			InputStream is = this.getClass().getClassLoader()
					.getResourceAsStream("/dataMessagingToken.txt");
			if (is != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String token = reader.readLine();
				dmConfig.setAuthToken(token);
			}
		} catch (Throwable t) {
		}
		return dmConfig;
	}
}
