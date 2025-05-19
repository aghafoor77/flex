/*
 * package analytics.utils;
 * 
 * import java.util.Date; import java.util.List; import java.util.Objects;
 * import java.util.concurrent.TimeUnit;
 * 
 * import com.ri.se.animalreg.persistance.RegisterAnimal; import
 * com.ri.se.animalreg.persistance.RegisterAnimalService; import
 * com.ri.se.assignfeed.persistance.AssignFeed; import
 * com.ri.se.assignfeed.persistance.AssignFeedService; import
 * com.ri.se.feedpattern.persistance.FeedPattern; import
 * com.ri.se.feedpattern.persistance.FeedPatternService; import
 * com.ri.se.generalhealthobservation.persistance.GeneralHealthObservation;
 * import com.ri.se.generalhealthobservation.persistance.
 * GeneralHealthObservationService;
 * 
 * import analytics.dto.FeedConsRateCurrent; import
 * analytics.dto.FeedConsRateCurrentList;
 * 
 * public class UC1Delegator {
 * 
 * private FeedPatternService feedPatternService; private AssignFeedService
 * assignFeedService; private RegisterAnimalService registerAnimalService;
 * private GeneralHealthObservationService generalHealthObservationService; //
 * Initial weight when animal born or registered in the farm private double
 * initalWeight; // Current weight of the animal private double currentWeight;
 * // Numbers of days lapsed private long ndl;
 * 
 * // Feed consumed between two period of times private double feedConsumed;
 * private String animalID = null;
 * 
 * public UC1Delegator(RegisterAnimalService registerAnimalService,
 * GeneralHealthObservationService generalHealthObservationService,
 * FeedPatternService feedPatternService, AssignFeedService assignFeedService) {
 * this.registerAnimalService = registerAnimalService;
 * this.generalHealthObservationService = generalHealthObservationService;
 * this.feedPatternService = feedPatternService; this.assignFeedService =
 * assignFeedService;
 * 
 * }
 * 
 * // Total weight gain - Current private double twgC() { return currentWeight -
 * initalWeight; }
 * 
 * 
 * 
 * // Check it later public double calculateFCR() { if
 * (Objects.isNull(this.animalID)) {
 * System.err.println("Please call init method !"); return 0; } if (feedConsumed
 * == 0) { return currentWeight - initalWeight; } return (currentWeight -
 * initalWeight) / feedConsumed; }
 * 
 * // Calculate ADG public double calculateADG() { if
 * (Objects.isNull(this.animalID)) {
 * System.err.println("Please call init method !"); return 0; } double adg = 0;
 * if (ndl == 0) { adg = twgC(); } else { adg = twgC() / ndl; } return adg; }
 * 
 * // Calculate Total weight gained - expected. This is used to find // when the
 * animal is ready for slaughterhouse private double calcualteTWGE(double
 * projectedWeight) { return projectedWeight - currentWeight; }
 * 
 * // Calculate days to reach the market weight public double
 * calculateDtM(double projectedWeight) { if (Objects.isNull(this.animalID)) {
 * System.err.println("Please call init method !"); return 0; } double weightTem
 * = calcualteTWGE(projectedWeight); return weightTem / calculateADG(); }
 * 
 * // Display notification for the farm owner. We need to get input from the
 * farm // owner public int adjustFeedingProgramNotification(int
 * daystoSlaughter, double projectedWeight) {
 * 
 * if (calculateDtM(projectedWeight) < daystoSlaughter) { return -1; //
 * "Slow down the feed and add more fiber in it !"; } else if
 * (calculateDtM(projectedWeight) > daystoSlaughter) { return 1; //
 * "Give more feed to the animal !"; } return 0;// Keep the current ratio for
 * feed ! }
 * 
 * public int init(String animalID) { this.animalID = animalID; RegisterAnimal
 * animal = registerAnimalService.get(animalID); if (Objects.isNull(animal)) {
 * return 0; } List<GeneralHealthObservation> observations =
 * generalHealthObservationService.getByAnimal(animalID); if
 * (Objects.isNull(observations) || observations.size() == 0) { return 0; }
 * initalWeight = animal.getWeight(); Date regdate =
 * animal.getRegistrationDate(); GeneralHealthObservation
 * generalHealthObservation = observations.get(0); String currentWeightStr =
 * generalHealthObservation.getWight(); currentWeight =
 * Double.parseDouble(currentWeightStr); Date obsrvDate =
 * generalHealthObservation.getGheDate(); long diff = obsrvDate.getTime() -
 * regdate.getTime(); ndl = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
 * feedConsumed = getFeedConsumed(animalID); return 1; }
 * 
 * public double getFeedConsumed(String animalID) {
 * 
 * // Get all feed patterns from AssignedFeed List<AssignFeed> fpids =
 * assignFeedService.getFPByrAnimalID(animalID); // Get number of animal in each
 * feed assignedFeed double feedConsumed = 0.0; for (AssignFeed af : fpids) {
 * List<AssignFeed> temp = assignFeedService.getAnimalsByFP(af.getFpid()); int
 * totalAnimalFeedAssignedTo = temp.size(); FeedPattern feedPattern =
 * feedPatternService.get(af.getFpid()); double vol = feedPattern.getVolume();
 * feedConsumed += vol / totalAnimalFeedAssignedTo; } return feedConsumed; } }
 */