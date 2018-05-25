import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.net.URI;
import java.awt.Desktop;

import javax.net.ssl.HttpsURLConnection;


public class ONHttpsClient {

	private final String accessTokenUri = "https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize?"
											+ "client_id=6845e81b-27b5-4654-af46-aea20c0e8d4c"
											+ "&response_type=code"
											+ "&redirect_uri=https://login.microsoftonline.com/common/oauth2/nativeclient"
											+ "&response_mode=query"
											+ "&scope=user.read%20notes.read"
											+ "&state=12345";
	
	private void requestToken(Desktop desktop) throws Exception
	{
		URI obj = new URI(accessTokenUri);
		desktop.browse(obj);
	}
	
	/**
	 * 
	 * @param authorizationCode - The authorizationCode aquired in the first step of the authorization flow
	 * @return The String 
	 * @throws Exception
	 */
	private String sendPost(String authorizationCode) throws Exception {

		String url = "https://login.microsoftonline.com/consumers/oauth2/v2.0/token";
					 
		URL obj = new URL(url);
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

		// Send post request over the datastream
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
		System.out.println(con.getContentEncoding());
		//return the accessToken send by the Azure AD Endpoint
		return response.toString();
	}
}
