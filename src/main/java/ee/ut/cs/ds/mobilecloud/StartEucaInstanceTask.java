/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ut.cs.ds.mobilecloud;

import com.xerox.amazonws.ec2.InstanceType;
import com.xerox.amazonws.ec2.Jec2;
import com.xerox.amazonws.ec2.LaunchConfiguration;
import com.xerox.amazonws.ec2.ReservationDescription;
import com.xerox.amazonws.ec2.ReservationDescription.Instance;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class StartEucaInstanceTask extends AbstractTask {

    private Jec2 cloud = null;
    private String idKey;
    private String secretKey;
    private String imageId;
    private Instance runInstance(String imageId) throws Exception {
        LaunchConfiguration launchConfig = new LaunchConfiguration(
                imageId.trim());
        launchConfig.setKeyName("huber");
        launchConfig.setMinCount(1);
        launchConfig.setMaxCount(1);
        launchConfig.setInstanceType(InstanceType.MEDIUM_HCPU);

        ReservationDescription reservationDescription = cloud.runInstances(launchConfig);
        Instance instance = reservationDescription.getInstances().get(0);
        String[] instances = new String[]{instance.getInstanceId()};

        // wait for the instance to start running
        do {
            instance = cloud.describeInstances(instances).get(0).getInstances().get(0);

            System.out.println("Run: Instance ID = " + instance.getInstanceId()
                    + ", State = " + instance.getState());

            Thread.sleep(5000);
        } while (!instance.isRunning());

        System.out.println("Run: Instance ID = " + instance.getInstanceId()
                + ", Public DNS Name = " + instance.getDnsName()
                + ", Private DNS Name = " + instance.getPrivateDnsName());

        // takes some time for SSH to start on the VMs so sleep for a bit or we
        // get a connection refused
        System.out.println("SSH: waiting for SSH on VM to start");
        Thread.sleep(30000);

        return instance;
    }

    public void createInstance() {
		String key = getParameters().get(0);
		String secretKey = getParameters().get(1);
//        cloud = new Jec2(getIdKey().trim(), getSecretKey().trim(), false,
		cloud = new Jec2(key, secretKey, false,
                "172.17.60.242", 8773);
        // cloud = new Jec2("qWmzuvv8MvADE2TgduZ9RGXsnUaJ1EOBtbhiew",
        // "zUBBWleQPSSPDO8XpfmKjT9fJvEoouSLuFIJ8g",false, "172.17.60.242",
        // 8773);
        cloud.setResourcePrefix("/services/Eucalyptus");
        cloud.setSignatureVersion(1);
    }

    public void setIdKey(String ikey) {
        this.idKey = ikey;
    }

    public String getIdKey() {
        return this.idKey;
    }

    public void setSecretKey(String skey) {
        this.secretKey = skey;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setImageId(String id) {
        this.imageId = id;
    }

    public String getImageId() {
        return getParameters().get(2);
    }

    @Override
    public void performTask() {
        try {
            createInstance();
            runInstance(getImageId());
            setResults("Success - Instance Started ");
        } catch (Exception ex) {
            Logger.getLogger(StartEucaInstanceTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
