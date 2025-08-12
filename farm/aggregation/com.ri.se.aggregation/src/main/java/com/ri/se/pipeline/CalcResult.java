package com.ri.se.pipeline;

import com.ri.se.aggregation.acctmanegement.AccountHandling;
import com.ri.se.aggregation.dymmydata.Results;

public class CalcResult {

	public static void main(String[] args) throws Exception {
		//

		AccountHandling.createAccount();

		for (int i = 1; i <= 20; i++) {
			String animalID = Results.execRecordInsertion(i);
			CompleteCycel.execute(animalID);
		}

	}

}
