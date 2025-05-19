package com.ri.se.breed.utils;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReaderUtils {

	public static void main(String[] args) throws Exception {
		//
		String fileBreed = "/home/ag/Desktop/RISE/development/formas/farmms/com.ri.se.generaldata/breedsdata.txt";
		String fileFeed = "/home/ag/Desktop/RISE/development/formas/farmms/com.ri.se.generaldata/feedtype.txt";
		System.out.println(new ReaderUtils().getBreeds(fileBreed));
		System.out.println(new ReaderUtils().getFeeds(fileFeed));
	}

	public ArrayList<String> getBreeds(String fileName) throws Exception {
		ObjectMapper obj = new ObjectMapper();
		ArrayList<String> arrayList = obj.readValue(new File(fileName), ArrayList.class);
		return arrayList;
	}

	public ArrayList<String> getFeeds(String fileName) throws Exception {
		ObjectMapper obj = new ObjectMapper();
		ArrayList<String> arrayList = obj.readValue(new File(fileName), ArrayList.class);
		return arrayList;
	}

}
