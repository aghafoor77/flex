package com.rise.se.slaughterhouse.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ri.se.commonentities.Message;
import com.rise.se.slaughterhouse.downloader.DownloadVDRData;
import com.rise.se.slaughterhouse.persistance.RAnimalSlaughterhouseData;
import com.rise.se.slaughterhouse.persistance.RAnimalSlaughterhouseDataService;
import com.rise.se.slaughterhouse.persistance.RCarrierSlaughterhouseData;
import com.rise.se.slaughterhouse.persistance.RCarrierSlaughterhouseDataService;
import com.rise.se.slaughterhouse.persistance.RSlaughterhouseData;
import com.rise.se.slaughterhouse.persistance.RSlaughterhouseDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/slaughterhouse")
@Api(value = "Slaughterhouse by RRB", description = "API used to provide CRUD operations for Slaughterhouse service ")
public class SlaughterhouseResource {

	private RSlaughterhouseDataService rslaughterhousedataService;
	private RCarrierSlaughterhouseDataService rcarrierslaughterhousedataService;
	private RAnimalSlaughterhouseDataService ranimalslaughterhousedataService;

	public SlaughterhouseResource(RSlaughterhouseDataService rslaughterhousedataService,
			RCarrierSlaughterhouseDataService rcarrierslaughterhousedataService,
			RAnimalSlaughterhouseDataService ranimalslaughterhousedataService) {
		this.rslaughterhousedataService = rslaughterhousedataService;
		this.rcarrierslaughterhousedataService = rcarrierslaughterhousedataService;
		this.ranimalslaughterhousedataService = ranimalslaughterhousedataService;
	}

	// ============================================
	@GET
	@Path("/download/vdr/{veid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored Slaughterhouse!", notes = "Get list of all stored Slaughterhouse!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response download(@PathParam("veid") final String veid) {

		DownloadVDRData downloadVDRData = new DownloadVDRData(rslaughterhousedataService,
				rcarrierslaughterhousedataService, ranimalslaughterhousedataService);

		String ip = "http://localhost";
		int port = 9030;
		long timeInMilli = 0;
		try {
			long updated = this.ranimalslaughterhousedataService.getLatest();
			timeInMilli = updated;

		} catch (Exception exp) {
			exp.printStackTrace();
		}
		System.err.println("================================================================");
		System.err.println("=================== HOW [SLAUGHTER] TO CHECK ===================");
		System.err.println("========================= USER ADDRESS =========================");
		System.err.println("================================================================");
		String resource = "/v1/sctransaction//updated/byreceiver/" + timeInMilli + "/" + veid;

		try {
			boolean result = downloadVDRData.downloadFromVDRData(ip, port, resource);

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		List<RAnimalSlaughterhouseData> animals = ranimalslaughterhousedataService.list();

		
		DTOSlaughterAnimalList dto = new DTOSlaughterAnimalList();
		for (RAnimalSlaughterhouseData ad : animals) {
			String tripNo = ad.getTripNo();
			List<RCarrierSlaughterhouseData> cshData = rcarrierslaughterhousedataService.getByTripNo(tripNo);
			RSlaughterhouseData shData = rslaughterhousedataService.get(tripNo);
			DTOSlaughterAnimal dtoSlaughterAnimal = new DTOSlaughterAnimal();
			dtoSlaughterAnimal.setAnimalID(Objects.isNull(ad.getAnimalID())?"-":ad.getAnimalID());
			dtoSlaughterAnimal.setBirthDate(Objects.isNull(ad.getBirthDate())?"-":ad.getBirthDate());
			dtoSlaughterAnimal.setBirthPlace(Objects.isNull(ad.getBirthPlace())?"-":ad.getBirthPlace());
			dtoSlaughterAnimal.setBreed(Objects.isNull(ad.getBreed())?"-":ad.getBreed());
			dtoSlaughterAnimal.setCarrierId(Objects.isNull(cshData.get(0).getCarrierId())?"-":cshData.get(0).getCarrierId());
			dtoSlaughterAnimal.setCarrierNumber(Objects.isNull(cshData.get(0).getCarrierNumber())?"-":cshData.get(0).getCarrierNumber());
			dtoSlaughterAnimal.setCID(Objects.isNull(cshData.get(0).getCID())?"-":cshData.get(0).getCID());
			dtoSlaughterAnimal.setCurrentWeight(ad.getCurrentWeight());
			dtoSlaughterAnimal.setFarmId(Objects.isNull(ad.getFarmId())?"-":ad.getFarmId());
			dtoSlaughterAnimal.setLocation(Objects.isNull(shData.getLocation())?"-":shData.getLocation());
			dtoSlaughterAnimal.setSex(Objects.isNull(ad.getSex())?"-":ad.getSex());
			dtoSlaughterAnimal.setSlaughterhousename(Objects.isNull(shData.getSlaughterhousename())?"-":shData.getSlaughterhousename());
			dtoSlaughterAnimal.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(shData.getStartDate())));
			dtoSlaughterAnimal.setExpectedArrivalDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(shData.getExpectedArrivalDate())));
			
			
			dtoSlaughterAnimal.setTripNo(Objects.isNull(tripNo)?"-":tripNo);
			dto.add(dtoSlaughterAnimal);
		}

		return Response.status(Response.Status.OK).entity(dto).build();
	}
}
