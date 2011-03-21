/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author madis
 */
public class UploadPictureJob extends AbstractJob {
	
	static final String DefaultS3BucketName = "ut-ee-cs-ds-mobilecloud-testbucket";
	private AmazonS3 s3;
	
	public UploadPictureJob() {
		try {
			s3 = new AmazonS3Client(new PropertiesCredentials(
					this.getClass().getClassLoader().getResourceAsStream("AwsCredentials.properties")));
		} catch (IOException ex) {
			Logger.getLogger(UploadPictureJob.class.getName()).log(Level.SEVERE, null, ex);
		}
		for (Bucket bucket : s3.listBuckets()) {
			System.out.println(" - " + bucket.getName());
		}
	}

	@Override
	public void performTask() {
		s3.createBucket(DefaultS3BucketName);
		for (File file : data) {
			String fileName = getTaskID() + data.get(0).getName();
			PutObjectResult putObjectResult = s3.putObject(DefaultS3BucketName, fileName, file);
		}
	}

}
