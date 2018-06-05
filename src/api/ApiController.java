package api;

import java.awt.Desktop;
import java.util.ArrayList;
import java.util.HashMap;

import Exceptions.*;
import ONData.ONData;

public class ApiController implements ONInterface
{
	ONHttpsClient client;
	DataCollector collector;
	Desktop desktop;
	String accessToken;
	
	ApiController()
	{
		client = new ONHttpsClient();
		desktop = Desktop.getDesktop();
		collector = new DataCollector();
	}
	
	@Override
	public void login()
	{
		try {
			client.requestCode(desktop);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	
	@Override
	public boolean authorize(String uri) throws InvalidStateException, BadResponseException, Exception {
		//resolve the uri's query
		HashMap<String, String> variableMap = collector.resolveQuery(uri);
		
		//## CHECK REQUEST STATUS ##//
		
		//## Maybe add a Try Catch Block to catch Exceptions due to Website inactivity etc. ##//
		HttpsResponse response = client.requestAccessToken(variableMap.get("code"));
		
		collector.parseAuthorizationToken(response);
		
		return false;
	}

	
	@Override
	public void initializeNotebook(ONData notebook) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<ONData> getNotebooks() {
		try {
			client.getONContent("/notebooks", accessToken, "application/json");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public void openContent(ONData content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<ONData> searchPage(String pageName) {
		// TODO Auto-generated method stub
		return null;
	}

}
