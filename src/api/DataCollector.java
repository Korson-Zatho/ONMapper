package api;

import java.util.HashMap;

import Exceptions.*;

public class DataCollector {
	
	/**
	 * 
	 * @param response
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
	 * @return the authorizationCode in the Query
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
	
	public String buildQuerry()
	{
		return null;
	}
}
