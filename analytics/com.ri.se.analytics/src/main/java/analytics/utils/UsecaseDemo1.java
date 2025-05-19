package analytics.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;

import com.ri.se.animalreg.persistance.RegisterAnimal;
import com.ri.se.animalreg.persistance.RegisterAnimalService;
import com.ri.se.assignfeed.persistance.AssignFeed;
import com.ri.se.assignfeed.persistance.AssignFeedService;
import com.ri.se.feedpattern.persistance.FeedPattern;
import com.ri.se.feedpattern.persistance.FeedPatternService;
import com.ri.se.generalhealthobservation.persistance.GeneralHealthObservation;
import com.ri.se.generalhealthobservation.persistance.GeneralHealthObservationService;

import analytics.dto.FeedConsRateCurrent;
import analytics.dto.FeedConsRateCurrentList;

public class UsecaseDemo1 {

	FeedPatternService feedPatternService;
	AssignFeedService assignFeedService;
	String animalID;
	RegisterAnimalService registerAnimalService;
	GeneralHealthObservationService generalHealthObservationService;

	public UsecaseDemo1(FeedPatternService feedPatternService, AssignFeedService assignFeedService,
			RegisterAnimalService registerAnimalService,
			GeneralHealthObservationService generalHealthObservationService, String animalID) {
		this.animalID = animalID;
		this.registerAnimalService = registerAnimalService;
		this.generalHealthObservationService = generalHealthObservationService;
		this.feedPatternService = feedPatternService;
		this.assignFeedService = assignFeedService;
	}

	public void demo() throws Exception {

		FeedConsumptionRate feedConsumptionRate = new FeedConsumptionRate(feedPatternService, assignFeedService,
				animalID);
		ADGDelegator adgDelegator = new ADGDelegator(registerAnimalService, generalHealthObservationService, animalID);
		double adg = adgDelegator.calculateADG();
		System.out.println("ADG : " + adg);
		System.out.println("calculateFConsRCurrent : " + feedConsumptionRate.calculateFConsRCurrent());
		System.out.println("claculateFeedConversionRate : " + feedConsumptionRate.claculateFeedConversionRate(adg));

		double weightToSlaughterhouse = 25;
		double currentWeight = adgDelegator.getCurrentWeight();
		double fcr = feedConsumptionRate.claculateFeedConversionRate(adg);
		Date dateToSlaughterhouse = DateUtils.addMonths(new Date(), 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("calcualteFCR_E : " + new WeightPrediction().calcualteFCR_E(weightToSlaughterhouse,
				currentWeight, fcr, dateToSlaughterhouse) + " kg");

		double calculateFConsRCurrent = feedConsumptionRate.calculateFConsRCurrent();
		double calcualteFCR_E = new WeightPrediction().calcualteFCR_E(weightToSlaughterhouse, currentWeight, fcr,
				dateToSlaughterhouse);
		System.out.println("Adjusting feed to meet certain weight gain (market weight) "
				+ (calcualteFCR_E - calculateFConsRCurrent));
	}
}
