package api;

import java.util.ArrayList;
import java.util.HashMap;

import Exceptions.*;
import ONData.ONData;;

public class DataCollector {
	
	/**
	 * 
	 * @param response The HttpsResponse from the Azure AD Authorize endpoint
	 * @return Filters the authorizationToken from the HttpsResponse
	 */
	public String parseAuthorizationToken(HttpsResponse response) throws BadResponseException
	{
		if (response.getResponseCode() != 200)
		{
			throw new BadResponseException("Responsecode:" + response.getResponseCode());
		}
		
		
		String[] keyValue = response.getResponseString().split("\"");
		
		return keyValue[4];
	}
	
	/**
	 * 
	 * @return A HashMap<String,String> which has the name of the variable as the Key and 
	 * the Value of the variable as Value.
	 */
	public HashMap<String,String> resolveQuery(String uri)
	{
		//Select the Query of the URI
		String query = uri.split("?")[1];
		
		//Split the Query into all the 'key'='value' Strings
		String[] variables = query.split("&");
		HashMap<String,String> variableMap = new HashMap<String,String>();
		
		//Go through each 'key'='value' String and split them at '='
		//Then put the Key and the Value in the Hashmap:variableMap for later use
		for (int i = 0; i < variables.length; i++)
		{
			String[] pair = variables[i].split("=");
			variableMap.put(pair[0], pair[1]);
		}
		
		return variableMap;
	}
	
	/**
	 * 
	 * @return A query String which needs to be attached to the standard Uri to get the 
	 * JSON response with content that matches the query.
	 */
	public String buildQuerry()
	{
		return null;
	}
	
	/**
	 * Parses a JSON returned from Microsoft Graph after a Post request has been made
	 * It creates an ArrayList of ONData which then represent the returned content in our program
	 * @return ArrayList<ONData> Which are all the content that were sent by Microsoft Graph as a response
	 */
	public static ArrayList<ONData> parseJSON(HttpsResponse response)
	{
		//Split the JSON into Strings which each represent 1 ONData entity
		String data = response.getResponseString().split("[")[1];
		String[] onDataStrings = data.split("}},{");
		
		//Now split each of those representative Strings into their variable-value pair
		ArrayList<ONData> onDataEntities = new ArrayList<ONData>();
		for (int i = 0; i < onDataStrings.length; i++)
		{
			//Clean each String from '{', '}', '"' and then split
			String[] variableValuePairs = onDataStrings[i].replaceAll("\\{|\\}|\"", "").split(",");	
			
			//create a new ONData object for the array of variable-value pairs
			//variableValuePairs[x].split(":")[1] takes a variable- value pair at position x
			//and splits it so variable and value are seperated in a String[] of length 2
			//then we simply take entry String[1] which is the value of the variable
			onDataEntities.add(i, new ONData(variableValuePairs[0].split(":")[1], variableValuePairs[3].split(":")[1], parseLinkUrl(variableValuePairs[7])));
		}
		return onDataEntities;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String parseLinkUrl(String jsonLinks)
	{
		//Clean the String from '{', '}', '"' and then split at ":" which will split the String into "link" | "oneNoteClientUrl" | "href" | the ONClientUri we are looking for | Rest
		//Meaning we will have to return the String at third place to get the oneNoteClientUrl we are looking for
		String[] data = jsonLinks.replaceAll("\\{|\\}|\"", "").split(":");	
		return data[3];
	}
}
