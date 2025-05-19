package analytics.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.ri.se.assignfeed.persistance.AssignFeed;
import com.ri.se.assignfeed.persistance.AssignFeedService;
import com.ri.se.feedpattern.persistance.FeedPattern;
import com.ri.se.feedpattern.persistance.FeedPatternService;

import analytics.dto.FeedConsRateCurrent;
import analytics.dto.FeedConsRateCurrentList;

public class FeedConsumptionRate {

	private FeedPatternService feedPatternService = null;
	private AssignFeedService assignFeedService = null;
	private String animalID = null;

	public FeedConsumptionRate(FeedPatternService feedPatternService, AssignFeedService assignFeedService,
			String animalID) {
		this.feedPatternService = feedPatternService;
		this.assignFeedService = assignFeedService;
		this.animalID = animalID;
	}

	// Calculate feed consumption Rate Current
	public double calculateFConsRCurrent() {
		// Get the feed assigned date and then minus the

		List<AssignFeed> feedListByAnimal = assignFeedService.getFeedByAnimal(animalID);
		FeedConsRateCurrentList feedConsRateCurrentList = new FeedConsRateCurrentList();

		for (AssignFeed af : feedListByAnimal) {
			FeedConsRateCurrent temp = new FeedConsRateCurrent();
			temp.setFpid(af.getFpid());

			temp.setExpectedFinishDate(af.getExpectedFinishDate());
			temp.setStartDate(af.getAssignedDate());

			long diff = (af.getExpectedFinishDate().getTime() - af.getAssignedDate().getTime());
			long daysTemp = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			temp.setDays(daysTemp);

			// Find the volum per animal share in the feed pattern
			List<AssignFeed> tempAF = assignFeedService.getAnimalsByFP(af.getFpid());
			int totalAnimalFeedAssignedTo = tempAF.size();
			FeedPattern feedPattern = feedPatternService.get(af.getFpid());
			double vol = feedPattern.getVolume();
			temp.setVolume(vol / totalAnimalFeedAssignedTo);
			feedConsRateCurrentList.add(temp);
		}
		double vol = 0.0;
		long daysConsumedFeed = 0;
		for (FeedConsRateCurrent fcrc : feedConsRateCurrentList) {
			vol += fcrc.getVolume();
			daysConsumedFeed += fcrc.getDays();
		}
		double feedConsumptionRate = vol / daysConsumedFeed;
		return feedConsumptionRate;
	}
	
	public double claculateFeedConversionRate(double adg) {
		// Feed Consumption rate/ADG 
		return  calculateFConsRCurrent()/adg;		
	}	
}
