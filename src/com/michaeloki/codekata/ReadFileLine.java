package com.michaeloki.codekata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.json.*;

public class ReadFileLine {
	public static void main (String[]args) {
		BufferedReader br;
		try {
			String root = System.getProperty("user.dir");
			String filePath = "/src/resources/addresses.json";
			String abspath = root+filePath;
			
			br = new BufferedReader(new FileReader(abspath));
			String line = br.readLine();
			String fileContent = "";
			while (line!=null) {
				fileContent +=line;
				line = br.readLine();
			}
			br.close();
			JSONArray jsonArray = new JSONArray(fileContent);

			JSONObject jsonObj = jsonArray.getJSONObject(0);
			String country = jsonObj.getJSONObject("country").get("name").toString();
			String province = "";
			try {
				province = jsonObj.getJSONObject("provinceOrState").get("name").toString();
			  
			} catch (Exception npe) {}
			String city = jsonObj.get("cityOrTown").toString();
			String code = jsonObj.get("postalCode").toString();
			String type = jsonObj.getJSONObject("type").get("name").toString();
			String lineDetail1 = "";
			String lineDetail2 = "";
			String myLineDetail = "";
			try {
			lineDetail1 = jsonObj.getJSONObject("addressLineDetail").get("line2").toString();
			lineDetail2 = jsonObj.getJSONObject("addressLineDetail").get("line1").toString();
			myLineDetail = lineDetail1+" ".concat(lineDetail2);
			} catch(Exception e) {}
			prettyPrintAddress(type,myLineDetail,city,province,code,country);
			
			printAllAddresses(fileContent);
			printAddressType(fileContent);
			printValidAddress(fileContent);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
    }
	
	public static void prettyPrintAddress(String type,String lineDetail,
			String city,String province,String code,String country) {
		System.out.println("====Formatted address ====");
		String formattedAddress = type+":"+lineDetail +"-"+city+"-"+province+"-"+code+"-"+country;
		System.out.println(formattedAddress);
	}
	
	public static void printAllAddresses(String fileContent) {
		JSONArray jsonArray = new JSONArray(fileContent);
		System.out.println("\n"+"\n"+"====Print all addresses====");
		for(int entry=0;entry<jsonArray.length();entry++) {
		JSONObject jsonObj = jsonArray.getJSONObject(entry);
		String country = jsonObj.getJSONObject("country").get("name").toString();
		String province = "";
		try {
			province = jsonObj.getJSONObject("provinceOrState").get("name").toString();
		} catch (Exception npe) {}
		String city = jsonObj.get("cityOrTown").toString();
		String code = jsonObj.get("postalCode").toString();
		String type = jsonObj.getJSONObject("type").get("name").toString();
		String lineDetail1 = "";
		String lineDetail2 = "";
		String myLineDetail = "";
		try {
		lineDetail1 = jsonObj.getJSONObject("addressLineDetail").get("line2").toString();
		lineDetail2 = jsonObj.getJSONObject("addressLineDetail").get("line1").toString();
		myLineDetail = lineDetail1+" ".concat(lineDetail2);
		} catch(Exception e) {}
		
		String formattedAddresses = type+":"+myLineDetail +"-"+city+"-"+province+"-"+code+"-"+country;
		
		System.out.println(formattedAddresses);
	}
	}
	
	
	public static void printAddressType(String fileContent) {
		JSONArray jsonArray = new JSONArray(fileContent);
		System.out.println("\n"+"\n"+"====Print an address type====");
		for(int entry=0;entry<jsonArray.length();entry++) {
		JSONObject jsonObj = jsonArray.getJSONObject(entry);
		String type = jsonObj.getJSONObject("type").get("name").toString();
		
		String formattedAddressType = type;
		
		System.out.println(formattedAddressType);
	}
	}
	
	
	public static void printValidAddress(String fileContent) {
		Boolean status = false;
		Boolean countryStatus = false;
		Boolean postalCode = false;
		Boolean addressLine = false;
		
		JSONArray jsonArray = new JSONArray(fileContent);
		System.out.println("\n"+"\n"+"====Validate an address====");
		for(int entry=0;entry<jsonArray.length();entry++) {
		JSONObject jsonObj = jsonArray.getJSONObject(entry);
		String country = jsonObj.getJSONObject("country").get("name").toString();
		String CountryCode = jsonObj.getJSONObject("country").get("code").toString();
		
		if(country!=null && !country.isEmpty()) {
			countryStatus = true;
		}

		String province = "";
		try {
			province = jsonObj.getJSONObject("provinceOrState").get("name").toString();
		} catch (Exception npe) {}
		
		String code = jsonObj.get("postalCode").toString();
		try {
		if(Integer.parseInt(code)>0) {
			postalCode = true;
		}
		} catch(Exception e) {
			postalCode = false;
		}
		String lineDetail1 = "";
		String lineDetail2 = "";
		try {
		lineDetail1 = jsonObj.getJSONObject("addressLineDetail").get("line2").toString();
		lineDetail2 = jsonObj.getJSONObject("addressLineDetail").get("line1").toString();
		if((lineDetail1!=null && !lineDetail1.isEmpty())  &&
				(lineDetail2!=null && !lineDetail2.isEmpty()) ) {
			addressLine = true;
		}
		} catch(Exception e) {
			addressLine = false;
		}
		
		if(CountryCode.equalsIgnoreCase("ZA")) {
			if(province!=null && !province.isEmpty()) {
				if(countryStatus && postalCode && addressLine) {
					status = true;
					System.out.println(province + ", " +country +" has a valid address");
				} else {
					System.out.println("country, postal code and address line are not valid");
					status = false;
				}
			} else {
				System.out.println("Every ZA address requires a province");
				status = false;
			}
		} else {
			if(countryStatus && postalCode && addressLine) {
				status = true;
			} else {
				status = false;
				System.out.println(country +" is missing "+"postal code and address line");
			}
		}
	}
	}
}
