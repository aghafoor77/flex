package analytics.utils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.ri.se.animalreg.persistance.RegisterAnimal;
import com.ri.se.animalreg.persistance.RegisterAnimalService;
import com.ri.se.generalhealthobservation.persistance.GeneralHealthObservation;
import com.ri.se.generalhealthobservation.persistance.GeneralHealthObservationService;

public class ADGDelegator {

	private String animalID = null;
	private RegisterAnimalService registerAnimalService = null;
	private GeneralHealthObservationService generalHealthObservationService = null;
	private long ndl = 0;
	private double currentWeight, initalWeight;

	public ADGDelegator(RegisterAnimalService registerAnimalService,
			GeneralHealthObservationService generalHealthObservationService, String animalID) throws Exception {
		this.animalID = animalID;
		this.registerAnimalService = registerAnimalService;
		this.generalHealthObservationService = generalHealthObservationService;
		init();
	}

	public void init() throws Exception {
		RegisterAnimal animal = registerAnimalService.get(animalID);
		if (Objects.isNull(animal)) {
			throw new Exception("Animal not found !");
		}
		List<GeneralHealthObservation> observations = generalHealthObservationService.getByAnimal(animalID);
		if (Objects.isNull(observations) || observations.size() == 0) {
			throw new Exception("No animal observation record found !");
		}
		initalWeight = animal.getWeight();
		Date regdate = animal.getRegistrationDate();
		GeneralHealthObservation generalHealthObservation = observations.get(0);
		String currentWeightStr = generalHealthObservation.getWight();
		currentWeight = Double.parseDouble(currentWeightStr);
		Date obsrvDate = generalHealthObservation.getGheDate();
		long diff = obsrvDate.getTime() - regdate.getTime();
		ndl = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

	// Total weight gain - Current
	private double twgC() {
		return currentWeight - initalWeight;
	}

	// Calculate ADG
	public double calculateADG() {
		if (Objects.isNull(this.animalID)) {
			System.err.println("Please call init method !");
			return 0;
		}
		double adg = 0;
		if (ndl == 0) {
			adg = twgC();
		} else {
			adg = twgC() / ndl;
		}
		return adg;
	}

	public double getCurrentWeight() {
		return currentWeight;
	}

	public double getInitalWeight() {
		return initalWeight;
	}
}
