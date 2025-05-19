package com.ri.se.aggregation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.ri.se.commonentities.DTOCentralHostRegistration;
import com.ri.se.commonentities.DTOCentralHostRegistrationList;

public class FarmAggregation {

	public static void main(String[] args) throws Exception {

		String centralRegistry = "http://10.114.72.234:9090";

		FarmAggregation farmAggregation = new FarmAggregation();
		DTOCentralHostRegistrationList dtoCentralHostRegistrationList = farmAggregation
				.fetchAllServicesMetaData(centralRegistry);
		
		Map<String, String> msMap = farmAggregation.extractRequiredServices(farmAggregation.getRegisteredServices(),
				dtoCentralHostRegistrationList);
		farmAggregation.fetchMicroServiceData(msMap, "AR-1616756825455");//AR-1614698765941");//"AR-1614698765991");//"AR-111-333-222");
	}

	public void fetchData() {
		
	}
	
	
	
	public Map<String, String> extractRequiredServices(String registeredServices,
			DTOCentralHostRegistrationList dtoCentralHostRegistrationList) throws Exception {
		String[] registeredServicesArray = registeredServices.split(",");
		List<String> registeredServicesList = new ArrayList<String>();
		for (String str : registeredServicesArray) {
			registeredServicesList.add(str);
		}
		HashMap<String, String> regServicesData = new HashMap<String, String>();
		for (DTOCentralHostRegistration f : dtoCentralHostRegistrationList) {
		
			if (registeredServicesList.contains(f.getName()) && f.getStatus().equals("1")) {
				String address = f.getPort() == 0 ? f.getAddress() : f.getAddress() + ":" + f.getPort();
				System.out.println(f.getName());
				if(f.getName().endsWith("ctrl")) {
				System.out.println(f.getName().substring(0, f.getName().length()-4));	
				regServicesData.put(f.getName().substring(0, f.getName().length()-4), address.startsWith("http") ? address : ("http://" + address));
				} else {
				regServicesData.put(f.getName(), address.startsWith("http") ? address : ("http://" + address));
				}
			}
		}
		return regServicesData;
	}

	public DTOCentralHostRegistrationList fetchAllServicesMetaData(String centralRegistry) throws Exception {
		RestClient rc = RestClient.builder().baseUrl(centralRegistry).build();
		Representation rep = rc.get("/application/v1/centralhostregistration", null);
		
		System.out.println(rep.getBody().toString());
		DTOCentralHostRegistrationList dtoCentralHostRegistrationList = new ObjectMapper()
				.readValue(rep.getBody().toString(), DTOCentralHostRegistrationList.class);
		if (Objects.isNull(dtoCentralHostRegistrationList) || dtoCentralHostRegistrationList.size() == 0) {
			throw new Exception("Data not found from central registry !");
		}
		return dtoCentralHostRegistrationList;
	}

	public static String getRegisteredServices() {
		return "drugstreatmentsctrl,animalderegisterctrl,animalexaminationctrl,registeranimalctrl,centralhostregistrationctrl,feedpatternctrl,generalhealthexaminationctrl,movebullforherdctrl,ordersemenctrl,reportslaughterhousectrl,semenutilizationctrl,temporarymovementctrl";
	}

	public void fetchMicroServiceData(Map<String, String> microServcies, String identity) throws Exception {
		microServcies.forEach((k, v) -> {
			try {
				new ServiceAggragator().collect(k, v, identity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}


/*
 * 'AM':'http://localhost:9000', // account management
 * 'AR':'http://localhost:9002', 'OS':'http://localhost:9004',
 * 'MB':'http://localhost:9006', 'SU':'http://localhost:9007',
 * 'AE':'http://localhost:9008', 'BD':'http://localhost:9010',
 * 'GHE':'http://localhost:9011', 'GHO':'http://localhost:9011',
 * 'FP':'http://localhost:9013', 'DT':'http://localhost:9014',
 * 'TM':'http://localhost:9015', 'ADR':'http://localhost:9016',
 * 'RS':'http://localhost:9017', 'CHR':'http://172.17.0.2:9090'
 */
