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
    
    public SyncTestTimes(String testID) {
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
