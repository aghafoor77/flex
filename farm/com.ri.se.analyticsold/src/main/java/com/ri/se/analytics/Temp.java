package com.ri.se.analytics;

public class Temp {

	float weight_initial;
	float weight_day_measured;

	// 1. Average Daily Gain (ADG)
	// Average Daily Gain (ADG) - the expected amount of weight gained per day.
	// Average Daily Gain (ADG) = Total weight gainÂ /Â Number of days between start
	// weight and Current Weight
	int number_of_days_start_current_weight;

	public static float ADG_score(float weight_initial, float weight_day_measured,
			int number_of_days_start_current_weight) {
		float ADG_score;
		float weight_total_gain;
		weight_total_gain = weight_day_measured - weight_initial;
		ADG_score = weight_total_gain / number_of_days_start_current_weight;
		return ADG_score;
	}

	// 2. Feed Conversion Rate (FCR)
	float feed_total_consumed;

	public static float FCR_score(float weight_initial, float weight_day_measured, float feed_total_consumed) {
		float FCR_score;
		float weight_total_gain;
		weight_total_gain = weight_day_measured - weight_initial;
		FCR_score = weight_total_gain / feed_total_consumed;
		return FCR_score;
	}

	// 3. Total estimated feed usage to reach market weights (TEFC)

	public static void main(String[] args) {
		Temp farm1 = new Temp();

		// 1. Average Daily Gain (ADG)
		farm1.weight_initial = 100.0f;
		farm1.weight_day_measured = 450.0f;
		farm1.number_of_days_start_current_weight = 30;
		float ADG_Score = farm1.ADG_score(farm1.weight_initial, farm1.weight_day_measured,
				farm1.number_of_days_start_current_weight);
		System.out.println(ADG_Score);

		// 2. Feed Conversion Rate (FCR)
		farm1.feed_total_consumed = 100f;
		farm1.weight_initial = 100.0f;
		farm1.weight_day_measured = 450.0f;
		float FCR_Score = farm1.FCR_score(farm1.weight_initial, farm1.weight_day_measured, farm1.feed_total_consumed);
		System.out.println(FCR_Score);

		// 3. Total estimated feed usage to reach market weights (TEFC)
		farm1.weight_initial = 100.0f;
		farm1.weight_day_measured = 450.0f;
		float weight_total_gain = farm1.weight_day_measured - farm1.weight_initial;
		float TEFC_Score = weight_total_gain * FCR_Score;
		System.out.println(TEFC_Score);

		// print ('Total estimated feed usage to reach market weight: ' +
		// str(feed_total_feed_usage))
		// Predicted slaughter weight (Kg at date X) - Expected market weight

	}

}
