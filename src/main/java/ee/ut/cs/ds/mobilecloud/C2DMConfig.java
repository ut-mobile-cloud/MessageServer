/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

/**
 *
 * @author Carlos
 */

public final class C2DMConfig {

	private String authToken;

	public static final String DATAMESSAGING_SEND_ENDPOINT = "https://android.clients.google.com/c2dm/send";

	private String c2dmUrl;

	public String getAuthToken() {
		return (authToken == null) ? "" : authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public void setC2DMUrl(String url) {
		this.c2dmUrl = url;
	}

	public String getC2DMUrl() {
		if (c2dmUrl == null) {
			return DATAMESSAGING_SEND_ENDPOINT;
		} else {
			return c2dmUrl;
		}
	}
}
