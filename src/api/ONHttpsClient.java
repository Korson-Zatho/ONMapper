package api;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.net.URI;
import java.awt.Desktop;

import javax.net.ssl.HttpsURLConnection;


public class ONHttpsClient {

	private final String accessCodeUri = "https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize?"
											+ "client_id=6845e81b-27b5-4654-af46-aea20c0e8d4c"
											+ "&response_type=code"
											+ "&redirect_uri=https://login.microsoftonline.com/common/oauth2/nativeclient"
											+ "&response_mode=query"
											+ "&scope=user.read%20notes.read"
											+ "&state=12345";
	
	private final String accessTokenUri = "https://login.microsoftonline.com/consumers/oauth2/v2.0/token";
	private final String serviceRootUri = "https://graph.microsoft.com/v1.0/me/onenote";
	
	
	
	/**
	 * 
	 * @param desktop - A Desktop instance to start a browser window
	 * @throws Exception
	 * 
	 * Opens default browser with accessTokenUri to let the user log himself into his Microsoft-Account
	 * and then agree to the scopes ONMapper needs access to.
	 */
	public void requestCode(Desktop desktop) throws Exception
	{
		URI obj = new URI(accessCodeUri);
		desktop.browse(obj);
	}
	
	/**
	 * 
	 * @param authorizationCode - The authorizationCode acquired in the first step of the authorization flow
	 * @return A HttpsRespons containing the responsCode and responsString
	 * @throws Exception
	 */
	public HttpsResponse requestAccessToken(String authorizationCode) throws Exception {
					 
		URL obj = new URL(accessTokenUri);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("POST", "/common/oauth2/v2.0/token HTTP/1.1");
		con.setRequestProperty("Host", "https://login.microsoftonline.com");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		String urlParameters = "client_id=6845e81b-27b5-4654-af46-aea20c0e8d4c"
				 			   + "&scope=user.read%20notes.read"
				 			   + "&code=" + authorizationCode
				 			   + "&redirect_uri=https://login.microsoftonline.com/common/oauth2/nativeclient"
				 			   + "&grant_type=authorization_code";

		// Send post request over the DataOutputStream
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		//return a new HttpsResponse with responseCode and response as parameters
		return new HttpsResponse(responseCode, response.toString());
	}
	
	
	public HttpsResponse getONContent(String querry, String accessToken, String contentType) throws IOException
	{
		URL obj = new URL(serviceRootUri + querry);
		System.out.println(querry);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		//con.setRequestProperty("Host", "https://graph.microsoft.com");
		con.setRequestProperty("Authorization", "Bearer " + accessToken);
		con.setRequestProperty("Accept", contentType);
		
		//responseCode tells me if the request was successful or not!
		int responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//return the response as a String
		return new HttpsResponse(responseCode, response.toString());
	}
	

	
	public void openONContent(String url, Desktop desktop) throws Exception
	{
		URI uri = new URI(url);
		desktop.browse(uri);
	}
}