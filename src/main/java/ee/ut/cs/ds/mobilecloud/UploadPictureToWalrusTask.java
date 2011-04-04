/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;

/**
 *
 * @author root
 */
public class UploadPictureToWalrusTask extends AbstractTask {

    String fileName;
    S3Service s3Service;
    String idKey;
    String secretKey;

    public int connectWalrus(String parIdKey, String parSecretKey) {
        int success = -1;
        AWSCredentials awsCredentials = new AWSCredentials(parIdKey,
                parSecretKey);
        try {
            s3Service = new RestS3Service(awsCredentials);
            success = 0;
        } catch (S3ServiceException e) {
            e.printStackTrace();
        }
        return success;
    }

    public String uploadImageFile(String bucketName, File fileData) {
        try {
            S3Object fileObject = new S3Object(fileData);
            fileObject.setContentType("i");
            fileObject = s3Service.putObject(bucketName, fileObject);
            return "";
        } catch (S3ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    public void performTask() {
        connectWalrus(idKey, secretKey);
        if (s3Service == null) {
            for (File file : data) {
                uploadImageFile("testBucket2", file);
                setResults("Successfully uploaded a photo with file name " + fileName);
            }
        }
    }
}
