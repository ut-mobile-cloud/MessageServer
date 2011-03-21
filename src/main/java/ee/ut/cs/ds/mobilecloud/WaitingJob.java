/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author madis
 */
public class WaitingJob extends AbstractJob {

	
	private List<Integer>parameters;
	
	@Override
	public void performTask() {
		try {
			// TODO: take the time to sleep from parameter
			int timeToSleep = 0;
			if(getParameters() == null || getParameters().isEmpty()) {
				// Defaults to 5 sec sleeping time
				timeToSleep = 5000;
			} else {
				timeToSleep = parameters.get(0) * 1000;
			}
			Thread.sleep(timeToSleep);
		} catch (InterruptedException ex) {
			Logger.getLogger(WaitingJob.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
