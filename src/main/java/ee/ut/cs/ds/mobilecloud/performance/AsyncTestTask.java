/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud.performance;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectResult;
import ee.ut.cs.ds.mobilecloud.NotificationCentre;
import java.io.File;
import java.io.IOException;
import java.util.List;
import ee.ut.cs.ds.mobilecloud.NotificationCentre;
import ee.ut.cs.ds.mobilecloud.IPhoneNotificationProvider;
import ee.ut.cs.ds.mobilecloud.NotificationProvider;
/**
 *
 * @author madis
 */
public class AsyncTestTask implements Runnable{
	
	private List<File> files;
	String taskID;
	
	public AsyncTestTask(String taskID) {
		this.taskID = taskID;
	}
	private double timeNow() {
        return ((double)System.currentTimeMillis())/1000;
    }
	
	@Override
	public void run() {
		AsyncTestTimes times = (AsyncTestTimes)TestTimesManager.sharedManager().getTimesForTestID(taskID);
		times.setServerRequestToCloud(timeNow());
		String DefaultS3BucketName = "ut-ee-cs-ds-mobilecloud-testbucket";
        AmazonS3 s3 = null;
        try {
			s3 = new AmazonS3Client(new PropertiesCredentials(
					this.getClass().getClassLoader().getResourceAsStream("AwsCredentials.properties")));
		} catch (IOException ex) {
			//Logger.getLogger(UploadPictureTask.class.getName()).log(Level.SEVERE, null, ex);
		}
        s3.createBucket(DefaultS3BucketName);
		for (File file : files) {
			String fileName = "" + timeNow() + files.get(0).getName();
			PutObjectResult putObjectResult = s3.putObject(DefaultS3BucketName, fileName, file);
			System.out.println("File name : " + fileName);
		}
		times.setServerResponseFromCloud(timeNow());
        
		String MobileCloudIPhoneDeviceToken = "5324075b4c07afa9e2dbe15b74dbda2227a74d5537f0d4af24cc0aecda697f1c";
		times.setServerSendPushNotification(timeNow());
		NotificationCentre.getProviderForDevice("iphone")
				.withDeviceData(MobileCloudIPhoneDeviceToken)
				.withMessage("Async test complete")
				.withKeyValuePairs("testID", times.getTestID(), "resultType", "AsyncTestResult")
				.send();
	}

	/**
	 * @return the files
	 */
	public List<File> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(List<File> files) {
		this.files = files;
	}
	
}
