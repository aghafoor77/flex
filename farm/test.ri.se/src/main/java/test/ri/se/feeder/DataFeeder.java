
package test.ri.se.feeder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import test.ri.se.dtos.DTOAnimalExamination;
import test.ri.se.dtos.DTOAssignFeed;
import test.ri.se.dtos.DTODrugsTreatments;
import test.ri.se.dtos.DTOFeedPattern;
import test.ri.se.dtos.DTOGeneralHealthExamination;
import test.ri.se.dtos.DTOGeneralHealthObservation;
import test.ri.se.dtos.DTOMoveBullForHerd;
import test.ri.se.dtos.DTOOrderSemen;
import test.ri.se.dtos.DTORegisterAnimal;
import test.ri.se.dtos.DTOSemenUtilization;
import test.ri.se.dtos.DTOTemporaryMovement;
import test.ri.se.dtos.StringList;

public class DataFeeder {

	public static void main(String[] args) throws IOException {
		//String url = "http://localhost";
		String url = "http://192.168.132.131";
		
		new DataFeeder().executeFeeder(url);

	}
/*String mb4h = sendPOST(url + new DataFeederDelegator().get("MBH"),
				new DataFeederDelegator().addDTOMoveBullForHerd());
		DTOMoveBullForHerd mb4hR = mapper.readValue(mb4h, DTOMoveBullForHerd.class);
		System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mb4hR));
 */
	private String sendPOST(String url, Object obj) throws IOException {
		String result = "";
		System.out.println(url);
		HttpPost post = new HttpPost(url);
		// add request headers
		post.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		String json = new ObjectMapper().writeValueAsString(obj);
		StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);

		post.setEntity(requestEntity);

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {
			System.out.println("Status Code: " + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == 200) {
				return result = EntityUtils.toString(response.getEntity());
			} else {
				System.out.println("Status Code : " + response.getStatusLine().getStatusCode());
				result = EntityUtils.toString(response.getEntity());
				System.out.println(result);
				return result;
			}
		}
	}

	public void executeFeeder(String url) throws IOException {
		DataFeeder dataFeeder = new DataFeeder();
		String employee = "employee@lsc.se";
		ObjectMapper mapper = new ObjectMapper();

		String result = sendPOST(url + new DataFeederDelegator().get("AR"),
				new DataFeederDelegator().addFirstDTORegisterAnimal());
		DTORegisterAnimal animal = mapper.readValue(result, DTORegisterAnimal.class);
		System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(animal));

		// Prebirth data feed

		int i = 2;
		String dtoOrderSemen = sendPOST(url + new DataFeederDelegator().get("OS"),
				new DataFeederDelegator().addDTOOrderSemen(i));
		DTOOrderSemen orderS = mapper.readValue(dtoOrderSemen, DTOOrderSemen.class);
		System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderS));
		String animalID = animal.getAnimalID();

		String su = sendPOST(url + new DataFeederDelegator().get("SU"),
				new DataFeederDelegator().addDTOSemenUtilization(i, animalID, orderS));
		DTOSemenUtilization suti = mapper.readValue(su, DTOSemenUtilization.class);
		System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(suti));

		String animalE = sendPOST(url + new DataFeederDelegator().get("EX"),
				new DataFeederDelegator().addDTOAnimalExamination(animalID));
		DTOAnimalExamination animalER = mapper.readValue(animalE, DTOAnimalExamination.class);
		System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(animalER));

		String regAnimal = sendPOST(url + new DataFeederDelegator().get("AR"),
				new DataFeederDelegator().addDTORegisterAnimal(i));
		DTORegisterAnimal regAnimalR = mapper.readValue(regAnimal, DTORegisterAnimal.class);
		System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(regAnimalR));

		String feedP = sendPOST(url + new DataFeederDelegator().get("FP"),
				new DataFeederDelegator().addDTOFeedPattern(i));

		DTOFeedPattern feedPR = mapper.readValue(feedP, DTOFeedPattern.class);
		System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(feedPR));

		String assignF = sendPOST(url + new DataFeederDelegator().get("AF"),
				new DataFeederDelegator().addDTOAssignFeed(i, "FP-1697932697887", animalID));
		DTOAssignFeed assignFR = mapper.readValue(assignF, DTOAssignFeed.class);
		System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(assignFR));

		String ghe = sendPOST(url + new DataFeederDelegator().get("GHE"),
				new DataFeederDelegator().addDTOGeneralHealthExamination(animalID, i));
		DTOGeneralHealthExamination gheR = mapper.readValue(ghe, DTOGeneralHealthExamination.class);
		System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(gheR));

		String gho = sendPOST(url + new DataFeederDelegator().get("GHO"),
				new DataFeederDelegator().DTOGeneralHealthObservation(i, animalID));
		DTOGeneralHealthObservation ghoR = mapper.readValue(gho, DTOGeneralHealthObservation.class);
		System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ghoR));

		String dt = sendPOST(url + new DataFeederDelegator().get("DT"),
				new DataFeederDelegator().DTODrugsTreatments(i, animalID, ghoR.getGhoid(), "Observation"));
		DTODrugsTreatments dtR = mapper.readValue(dt, DTODrugsTreatments.class);
		System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dtR));

		StringList al = new StringList();
		List<String> data = new ArrayList<>();
		data.add("AR-1697141039106");
		al.setData(data);
		String tm = sendPOST(url + new DataFeederDelegator().get("TM"),
				new DataFeederDelegator().addDTOTemporaryMovement(i, al));
		DTOTemporaryMovement tmR = mapper.readValue(tm, DTOTemporaryMovement.class);
		System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tmR));

	}

}
