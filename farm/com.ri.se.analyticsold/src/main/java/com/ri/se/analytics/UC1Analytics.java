package com.ri.se.analytics;

public class UC1Analytics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void fetechADGData() {
		// Get date of registration from RegisterAnimal 
		// Get initial weight from RegisterAnimal 
		// Get the current date from the GenralhealthObservation
		// Get weight from the GenralhealthObservation
		
		
	}
	
	public float ADG_score(float weight_initial, float weight_day_measured,
			int number_of_days_start_current_weight) {
		float ADG_score;
		float weight_total_gain;
		weight_total_gain = weight_day_measured - weight_initial;
		ADG_score = weight_total_gain / number_of_days_start_current_weight;
		return ADG_score;
	} 
	

}
