package ri.se.farm.dto;

public class FarmFacility {
	private DTOFacilityList dtoFacilityList;
	private DTOFarm farm;

	public DTOFacilityList getDtoFacilityList() {
		return dtoFacilityList;
	}

	public void setDtoFacilityList(DTOFacilityList dtoFacilityList) {
		this.dtoFacilityList = dtoFacilityList;
	}

	public DTOFarm getFarm() {
		return farm;
	}

	public void setFarm(DTOFarm farm) {
		this.farm = farm;
	}
}
