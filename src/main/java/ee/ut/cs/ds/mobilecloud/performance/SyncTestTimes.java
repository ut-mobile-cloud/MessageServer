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
public class SyncTestTimes {
    private String testID;
                       // Timestamps
    private double clientInitialRequest; // client makes initial requet
    private double serverReceiveInitialRequest; // server receives request
    private double serverRequestToCloud; // server makes request to cloud
    private double serverResponseFromCloud; // server gets response
    private double serverSendResponse; // Timestamp when server sends response to client
    private double clientReceiveResponse; // Timestamp when client receives the response
    
    private void initialize() {
        clientInitialRequest = -1;
        serverReceiveInitialRequest = -1;
        serverRequestToCloud = -1;
        serverResponseFromCloud = -1;
        serverSendResponse = -1;
        clientReceiveResponse = -1;
    }
    public SyncTestTimes(String testID) {
        initialize();
        this.testID = testID;
    }
    
    public SyncTestTimes() {
        initialize();
    }
    
    /**
     * Will update it's fields if
     *  1) fields are initialized (in beginning set to -1 so will be checked against being greater or equal to 0)
     *  2) given parameter times will be assumed to always contain newer times. Will not be checked if actually true.
     * @param times 
     */
    public void updateWith(SyncTestTimes times) {
        if (times.getClientInitialRequest() >= 0)  {
            clientInitialRequest = times.getClientInitialRequest();
        }
        if (times.getServerReceiveInitialRequest() >= 0) {
            serverReceiveInitialRequest = times.getServerReceiveInitialRequest();
        }
        if (times.getServerRequestToCloud() >= 0) {
            serverRequestToCloud = times.getServerRequestToCloud();
        }
        if (times.getServerResponseFromCloud() >= 0) {
            serverResponseFromCloud = times.getServerResponseFromCloud();
        }
        if (times.getServerSendResponse() >= 0) {
            serverSendResponse = times.getServerSendResponse();
        }
        if (times.getClientReceiveResponse() >= 0) {
            clientReceiveResponse = times.getClientReceiveResponse();
        }
        
    }

    public void setTestID(String testID) {
        this.testID = testID;
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
     * @return the serverSendResponse
     */
    public double getServerSendResponse() {
        return serverSendResponse;
    }

    /**
     * @param serverSendResponse the serverSendResponse to set
     */
    public void setServerSendResponse(double serverSendResponse) {
        this.serverSendResponse = serverSendResponse;
    }

    /**
     * @return the clientReceiveResponse
     */
    public double getClientReceiveResponse() {
        return clientReceiveResponse;
    }
    
    
}
