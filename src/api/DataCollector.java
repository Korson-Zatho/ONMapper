package api;

import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Exceptions.*;
import ONData.ONData;;

public class DataCollector {
	
	private String notebookQuery;
	
	/**
	 * Parses the Response when requesting an authorization token 
	 * Builds HashMap so can be used to return all keyValuePairs sent by Microsoft not just the token
	 * @param response The HttpsResponse from the Azure AD Authorize endpoint
	 * @return Filters the authorizationToken from the HttpsResponse
	 */
	public String parseAuthorizationToken(HttpsResponse response) throws BadResponseException
	{
		if (response.getResponseCode() != 200)
		{
			throw new BadResponseException("Responsecode:" + response.getResponseCode());
		}
//		String[] keyValue = response.getResponseString().split("\"");
		String content = response.getResponseString().replaceAll("\\{|\\}|\"", "");
		String[] keyValuePairs = content.split(",");
		HashMap<String,String> map = new HashMap<String,String>();
		for (int i = 0; i < keyValuePairs.length; i++)
		{
			String[] splitPairs = keyValuePairs[i].split(":");
			map.put(splitPairs[0], splitPairs[1]);
		}
		return map.get("access_token");
	}
	
	
	
	/**
	 * 
	 * @return A HashMap<String,String> which has the name of the variable as the Key and 
	 * the Value of the variable as Value.
	 */
	public HashMap<String,String> resolveQuery(String uri)
	{
		//Select the Query of the URI
		String query = uri.split("\\?")[1];
		
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
	 * Builds Query which allows filtering of pages by a name and 
	 * gets specified selectParameters in the JSON-Response
	 * 
	 * @param name :The name which should be filtered with,
	 * @param selectParameters :The parameters that should be selected by the JSON-Response
	 * @return A query String which needs to be attached to the standard URI to get the 
	 * JSON response with content that matches the query.
	 */
	public String buildPageFilterQuery(String name, String selectParameters)
	{
		return (notebookQuery + "/pages?filter=title%20eq%20'" + name + "'&select=" + selectParameters);
	}
	
	
	
	// WARNING: DEPRICATED IS NOT USEFULL ANYMORE.
	// SHOULD BE TURNED INTO A PARSER RETURNING A LIST OF HASHMAPS WITH ALL VARIABLE VALUE PAIRS
	// FOR EACH PAGE/NOTEBOOK
	/**
	 * Parses a JSON returned from Microsoft Graph after a Post request has been made
	 * It creates an ArrayList of ONData which then represent the returned content in our program
	 * @param response The HttpsResponse entity which contents is to be parsed.
	 * @return ArrayList<ONData> Which are all the content that were sent by Microsoft Graph as a response
	 */
	public ArrayList<ONData> parseJSONold(HttpsResponse response)
	{
		//Split the JSON into Strings which each represent 1 ONData entity
		String data = response.getResponseString().split("\\[")[1];
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
	 * Filters the ClientUrlLink form the link variable in a JSON-Response from Microsoft-Graph
	 * @return The ClientUrlLink from a JSON of a Page or a Notebook
	 */
	public String parseLinkUrl(String jsonLinks)
	{
		//Clean the String from '{', '}', '"' and then split at ":" which will split the String into "link" | "oneNoteClientUrl" | "href" | the ONClientUri we are looking for | Rest
		//Meaning we will have to return the String at third place to get the oneNoteClientUrl we are looking for
		String[] data = jsonLinks.replaceAll("\\{|\\}|\"", "").split(":");	
		return data[3];
	}
	
	
	
	public ArrayList<HashMap<String,String>> parseJSON(HttpsResponse response)
	{
		//Split the JSON into Strings which each represent 1 ONData entity
		String data = response.getResponseString().split("\\[")[1];
		String[] onDataStrings = data.split("\\}\\},\\{");
				
		//Now split each of those representative Strings into their variable-value pair
		//And create a new List of HashMaps to store them in to be returned.
		ArrayList<HashMap<String,String>> hashList = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < onDataStrings.length; i++)
		{
			//Clean each String from '{', '}', '"' and then split the variable-value pairs
			String[] variableValuePairs = onDataStrings[i].replaceAll("\\{|\\}|\"|\\]", "").split(",");	
			
			//create a new HashMap object for the array of variable-value pairs
			//then go through each entry of variableValuePairs and split at ":"
			//then put the split Key-Value pair into the HashMap as Key and Value
			HashMap<String,String> map = new HashMap<String,String>();
			for (int j = 0; j < variableValuePairs.length; j++)
				{
					String[] split = variableValuePairs[j].split(":");
					System.out.println(split[0] + split[1]);
					if (split[0] == "links")	
					{
						map.put(split[0], parseLinkUrl(variableValuePairs[j]));
					}	else	{
						map.put(split[0], split[1]);
					}
				}
			System.out.println(map.toString());
			//Add the HashMap to the ArrayList
			hashList.add(i, map);
		}	
		return hashList;
	}
	
	
	
	/**
	 * Turns Variable-Value pairs in the form of HashMaps into ONData entities.
	 * This Method should be called after a HttpsResponse was parsed to get ONData
	 * that can be used for later purposes.
	 * 
	 * @param keyValue A List of HashMap<String,String> objects which you want to turn into ONData objects.
	 * @return ArrayList of ONData entities
	 */
	public ArrayList<ONData> createONData(ArrayList<HashMap<String,String>> keyValueMap)
	{
		ArrayList<ONData> dataList = new ArrayList<ONData>();
		for (Iterator<HashMap<String,String>> e = keyValueMap.iterator(); e.hasNext(); ) {
			HashMap<String,String> element = e.next();
			for (Iterator<String> iter = element.keySet().iterator(); iter.hasNext();)
			{
				if (element.containsKey("links"))	{
					dataList.add(new ONData(element.get(iter.next()), element.get(iter.next()), element.get(iter.next())));
				} else {
					dataList.add(new ONData(element.get(iter.next()), element.get(iter.next()), null));
				}
			}
		}
		System.out.println(dataList.get(0).toString());
		return dataList;
	}
	
	
	public void setNotebookQuery(String notebookQuery)	{
		this.notebookQuery = notebookQuery;
	}
	
	public String getNotebookQuery()
	{
		return this.notebookQuery;
	}
}
