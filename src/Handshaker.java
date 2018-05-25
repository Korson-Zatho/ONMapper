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


public class Handshaker {
	
	public static void main(String[] args) throws Exception
	{
		Handshaker shake = new Handshaker();
		System.out.println("Testing 1 - Send Http GET request");
		shake.requestToken();
		System.out.println("Enter Code:");
		Scanner scan = new Scanner(System.in);
		String input = "";
		while (true) 
		{
			input = scan.nextLine();
			if (!input.isEmpty())
			{
				break;
			}
		}
		scan.close();
		String output = shake.sendPost(input);
		String[] splitlist = output.split("\"");
		for (int i = 0; i < splitlist.length; i++)
		{
			System.out.println(splitlist[i]);
		}
		System.out.println(splitlist[splitlist.length-2]);
		
		shake.sendGet(splitlist[splitlist.length-2]);
	}

	private void requestToken() throws Exception
	{
		String accessTokenUrl = "https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize?"
								+ "client_id=6845e81b-27b5-4654-af46-aea20c0e8d4c"
								+ "&response_type=code"
								+ "&redirect_uri=https://login.microsoftonline.com/common/oauth2/nativeclient"
								+ "&response_mode=query"
								+ "&scope=user.read%20notes.read"
								+ "&state=12345";
		URI obj = new URI(accessTokenUrl);
		Desktop d = Desktop.getDesktop();
		System.out.println(d.isSupported(Desktop.Action.BROWSE));
		d.browse(obj);
	}
	
	private void sendGet(String accessToken) throws Exception {

		String url = "https://graph.microsoft.com/v1.0/me/onenote/sections";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		//con.setRequestProperty("Host", "https://graph.microsoft.com");
		con.setRequestProperty("Authorization", "Bearer " + accessToken);
		con.setRequestProperty("Accept", "json");
		
		//responseCode tells me if the request was successful or not!
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}
	
	private String sendPost(String code) throws Exception {

		String url = "https://login.microsoftonline.com/consumers/oauth2/v2.0/token";
					 
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("POST", "/common/oauth2/v2.0/token HTTP/1.1");
		con.setRequestProperty("Host", "https://login.microsoftonline.com");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		String urlParameters = "client_id=6845e81b-27b5-4654-af46-aea20c0e8d4c"
				 			   + "&scope=user.read%20notes.read"
				 			   + "&code=" + code
				 			   + "&redirect_uri=https://login.microsoftonline.com/common/oauth2/nativeclient"
				 			   + "&grant_type=authorization_code";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(con.getContentEncoding());
		//print result
		System.out.println(response.toString());
		return response.toString();
	}	
}