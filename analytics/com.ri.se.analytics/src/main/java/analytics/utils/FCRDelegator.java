/*
 * package analytics.utils;
 * 
 * import java.text.SimpleDateFormat; import java.util.Date; import
 * java.util.List; import java.util.Objects; import
 * java.util.concurrent.TimeUnit;
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
 * public class FCRDelegator {
 * 
 * private FeedPatternService feedPatternService; private AssignFeedService
 * assignFeedService; private RegisterAnimalService registerAnimalService;
 * private GeneralHealthObservationService generalHealthObservationService;
 * 
 * public FCRDelegator(RegisterAnimalService registerAnimalService,
 * GeneralHealthObservationService generalHealthObservationService,
 * FeedPatternService feedPatternService, AssignFeedService assignFeedService) {
 * this.registerAnimalService = registerAnimalService;
 * this.generalHealthObservationService = generalHealthObservationService;
 * this.feedPatternService = feedPatternService; this.assignFeedService =
 * assignFeedService; }
 * 
 * public double calculateFCR(String animalID) { RegisterAnimal animal =
 * registerAnimalService.get(animalID); if (Objects.isNull(animal)) { return 0;
 * } List<GeneralHealthObservation> observations =
 * generalHealthObservationService.getByAnimal(animalID); if
 * (Objects.isNull(observations) || observations.size() == 0) { return 0; } int
 * initalWeight = animal.getWeight(); Date regdate =
 * animal.getRegistrationDate(); GeneralHealthObservation
 * generalHealthObservation = observations.get(0); String currentWeight =
 * generalHealthObservation.getWight(); Date obsrvDate =
 * generalHealthObservation.getGheDate(); long diff = obsrvDate.getTime() -
 * regdate.getTime(); long days = TimeUnit.DAYS.convert(diff,
 * TimeUnit.MILLISECONDS); System.out.println("Days: " + days);
 * 
 * int weight_total_gain = Integer.parseInt(currentWeight) - initalWeight;
 * System.out.println("weight_total_gain : "+weight_total_gain ); double
 * feedConsumed = getFeedConsumed(animalID);
 * System.out.println("feedConsumed : "+feedConsumed ); double fcr =
 * weight_total_gain / feedConsumed; System.out.println("FCR : "+fcr); return
 * fcr ; }
 * 
 * public double getFeedConsumed(String animalID) {
 * 
 * // Get all feed patterns from AssignedFeed List<AssignFeed> fpids =
 * assignFeedService.getFPByrAnimalID(animalID); // Get number of animal in each
 * feed assignedFeed double feedConsumed = 0.0; for(AssignFeed af: fpids) {
 * List<AssignFeed> temp = assignFeedService.getAnimalsByFP(af.getFpid()); int
 * totalAnimalFeedAssignedTo = temp.size(); FeedPattern feedPattern =
 * feedPatternService.get(af.getFpid()); double vol = feedPattern.getVolume();
 * feedConsumed += vol/totalAnimalFeedAssignedTo; } return feedConsumed; }
 * 
 * public calculateTEFC(double weightGained, double fcr) {
 * 
 * }
 * 
 * }
 */