package analytics.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WeightPrediction {

	// FCR-E = Feed Consumption Rate - Expected : Calculate Total weight gained - expected. This is used to find
	// when the animal is ready for slaughterhouse
	public double calcualteFCR_E(double weightToSlaughterhouse, double currentWeight, double fcr, Date dateToSlaughterhouse) {
		long dtme = claculateDtME(dateToSlaughterhouse) ;
		double tefc = calculateTEFC(weightToSlaughterhouse, currentWeight, fcr) ;
		return tefc / dtme;
	}
	
	private long claculateDtME(Date dateToSlaughterhouse) {
		Date currentDate = new Date();
		long diff = dateToSlaughterhouse.getTime() - currentDate.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	// Total Weight Gain Expected and Feed Conversion Rate
	public double calculateTEFC(double weightToSlaughterhouse, double currentWeight, double fcr) {
		double twge = claculateTWGE(weightToSlaughterhouse, currentWeight);
		return twge * fcr;
	}
	
	private double claculateTWGE(double weightToSlaughterhouse, double currentWeight) {
		return weightToSlaughterhouse- currentWeight;
	}
}
