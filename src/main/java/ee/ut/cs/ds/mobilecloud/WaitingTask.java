/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

import com.google.gson.annotations.Expose;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author madis
 */
public class WaitingTask extends AbstractTask {

	
	@Expose private List<Integer>parameters;
	
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
			setResults("I was able to complete my job of waiting");
		} catch (InterruptedException ex) {
			Logger.getLogger(WaitingTask.class.getName()).log(Level.SEVERE, null, ex);
			setResults("Someone interrupted my waiting");
		}
	}

}
