/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.ut.cs.ds.mobilecloud;

import com.google.gson.annotations.Expose;
import java.util.List;


/**
 *
 * @author madis
 */
public class CalculateSumTask extends AbstractTask {
	
	@Expose List <Integer>parameters;
	
	@Override
	public void performTask() {
		
		Integer sum = 0;
		System.out.println("Calculating sum");
		for (Integer augend : parameters) {
			System.out.println("sum : " + sum + " augend : " + augend);
			sum += augend;
		}
		setResults(sum.toString());
	}

}
