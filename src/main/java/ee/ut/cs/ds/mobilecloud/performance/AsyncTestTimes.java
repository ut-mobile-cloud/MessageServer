/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud.performance;

/**
 *
 * Setters are provided only for values that can be measured in server.
 * @author madis
 */
public class AsyncTestTimes {
    private String testID; // Test identificator
                       // Timestamps : 
    private double clientInitialRequest; // client makes initial request to server
    private double serverReceiveInitialRequest; // server receives initial request
    private double serverSendImmediateResponse; // server starts sending back response for initial request. (Does it immediately. Async you know)
    private double clientReceiveImmediateResponse; // client receives server's immediate response
    private double serverRequestToCloud; // server makes request to cloud
    private double serverResponseFromCloud; // server gets response from cloud (job should be done)
    private double serverSendPushNotification; // server sends out push notification
    private double clientReceivePushNotification; // client receives push notification
    
    public AsyncTestTimes(String testID) {
        this.testID = testID;
		initialize();
    }
	
	AsyncTestTimes() {
		initialize();
	}

    private void initialize() {
		clientInitialRequest = -1;
		serverReceiveInitialRequest = -1;
		serverSendImmediateResponse = -1;
		clientReceiveImmediateResponse = -1;
		serverRequestToCloud = -1;
		serverResponseFromCloud = -1;
		serverSendPushNotification = -1;
		clientReceivePushNotification = -1;
	}
	public void updateWith(AsyncTestTimes times) {
        if (times.getClientInitialRequest() >= 0) {
			clientInitialRequest = times.clientInitialRequest;
		}
		if (times.clientReceiveImmediateResponse >= 0) {
			clientReceiveImmediateResponse = times.clientReceiveImmediateResponse;
		}
		if (times.clientReceivePushNotification >= 0) {
			clientReceivePushNotification = times.clientReceivePushNotification;
		}
    }
    /**
     * @return the testID
     */
    public String getTestID() {
        return testID;
    }

    /**
     * @return the clientInitialRequest
     */
    public double getClientInitialRequest() {
        return clientInitialRequest;
    }

    /**
     * @return the serverReceiveInitialRequest
     */
    public double getServerReceiveInitialRequest() {
        return serverReceiveInitialRequest;
    }

    /**
     * @param serverReceiveInitialRequest the serverReceiveInitialRequest to set
     */
    public void setServerReceiveInitialRequest(double serverReceiveInitialRequest) {
        this.serverReceiveInitialRequest = serverReceiveInitialRequest;
    }

    /**
     * @return the serverSendImmediateResponse
     */
    public double getServerSendImmediateResponse() {
        return serverSendImmediateResponse;
    }

    /**
     * @param serverSendImmediateResponse the serverSendImmediateResponse to set
     */
    public void setServerSendImmediateResponse(double serverSendImmediateResponse) {
        this.serverSendImmediateResponse = serverSendImmediateResponse;
    }

    /**
     * @return the clientReceiveImmediateResponse
     */
    public double getClientReceiveImmediateResponse() {
        return clientReceiveImmediateResponse;
    }

    /**
     * @param clientReceiveImmediateResponse the clientReceiveImmediateResponse to set
     */
    public void setClientReceiveImmediateResponse(double clientReceiveImmediateResponse) {
        this.clientReceiveImmediateResponse = clientReceiveImmediateResponse;
    }

    /**
     * @return the serverRequestToCloud
     */
    public double getServerRequestToCloud() {
        return serverRequestToCloud;
    }

    /**
     * @param serverRequestToCloud the serverRequestToCloud to set
     */
    public void setServerRequestToCloud(double serverRequestToCloud) {
        this.serverRequestToCloud = serverRequestToCloud;
    }

    /**
     * @return the serverResponseFromCloud
     */
    public double getServerResponseFromCloud() {
        return serverResponseFromCloud;
    }

    /**
     * @param serverResponseFromCloud the serverResponseFromCloud to set
     */
    public void setServerResponseFromCloud(double serverResponseFromCloud) {
        this.serverResponseFromCloud = serverResponseFromCloud;
    }

    /**
     * @return the serverSendPushNotification
     */
    public double getServerSendPushNotification() {
        return serverSendPushNotification;
    }

    /**
     * @param serverSendPushNotification the serverSendPushNotification to set
     */
    public void setServerSendPushNotification(double serverSendPushNotification) {
        this.serverSendPushNotification = serverSendPushNotification;
    }

    /**
     * @return the clientReceivePushNotification
     */
    public double getClientReceivePushNotification() {
        return clientReceivePushNotification;
    }

	/**
	 * @param testID the testID to set
	 */
	public void setTestID(String testID) {
		this.testID = testID;
	}
    
    
}
