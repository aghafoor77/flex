package com.rise.se.slaughterhouse.downloader;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.ws.rs.core.Response;

import com.ri.se.commonentities.Message;
import com.ri.se.commonentities.transporter.TransportedToSlaughter;
import com.ri.se.commonentities.transporter.TransportedToSlaughterList;
import com.rise.se.slaughterhouse.persistance.Slaughterhouse;
import com.rise.se.slaughterhouse.persistance.SlaughterhouseList;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new Tester().download();
	}
	//

	/*
	 * public void download() { String veid =
	 * "35685379632a6c545a67444b3b4739727e5754396448554978443473443b";
	 * DownloadVDRData downloadVDRData = new DownloadVDRData(); String ipfs = "";
	 * String ip = "http://localhost"; int port = 9030; long timeInMilli = 0; try {
	 * TransferedAnimal updated = this.transferedanimalService.getLatest(); if
	 * (!Objects.isNull(updated)) { timeInMilli = updated.getTransactionTime(); } }
	 * catch (Exception exp) {
	 * 
	 * } System.err.println(
	 * "================================================================");
	 * System.err.
	 * println("========================= HOW TO CHECK =========================");
	 * System.err.
	 * println("========================= USER ADDRESS =========================");
	 * System.err.println(
	 * "================================================================"); String
	 * resource = "/v1/sctransaction//updated/byreceiver/" + timeInMilli+"/"+ veid;
	 * TransportedToSlaughterList resources;
	 * 
	 * try { resources = downloadVDRData.downloadFromVDRData(ip, port, resource);
	 * System.out.println(""); } catch (Exception exp) {
	 * Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new
	 * Message(exp.getMessage())).build(); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * //Response.status(Response.Status.OK).entity().build(); }
	 */

}
