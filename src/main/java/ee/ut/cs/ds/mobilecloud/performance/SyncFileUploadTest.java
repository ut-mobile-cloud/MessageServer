/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud.performance;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectResult;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author madis
 */
public class SyncFileUploadTest implements Runnable {

    private List<File> files;
    private String testID;
    public SyncFileUploadTest(String testID) {
        this.testID = testID;
    }
    
    
    @Override
    public void run() {
        String DefaultS3BucketName = "ut-ee-cs-ds-mobilecloud-testbucket";
        AmazonS3 s3 = null;
        try {
		s3 = new AmazonS3Client(new PropertiesCredentials(
					this.getClass().getClassLoader().getResourceAsStream("AwsCredentials.properties")));
		} catch (IOException ex) {
			Logger.getLogger("Things went bad SyncFileUploadTest").log(Level.SEVERE, null, ex);
		}
		for (Bucket bucket : s3.listBuckets()) {
			System.out.println(" - " + bucket.getName());
                }
        s3.createBucket(DefaultS3BucketName);
		for (File file : files) {
			String fileName = testID + files.get(0).getName();
			PutObjectResult putObjectResult = s3.putObject(DefaultS3BucketName, fileName, file);
			System.out.println("File name : " + fileName);
			String resultString = "Successfully uploaded a photo with file name " + fileName;
		}
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
