package api;

import java.awt.Desktop;
import java.util.ArrayList;
import java.util.HashMap;

import exceptions.*;
import onData.ONData;

public class ApiController implements ONInterface
{
	private ONHttpsClient client;
	private DataCollector collector;
	private Desktop desktop;
	private String accessToken;
	private String pageSelectParameters = "id,title,links";
	private String notebookSelectParameters = "id,name";
	private String jsonContentType = "json";
	
	public ApiController()
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
	public boolean authorize(String uri) throws Exception {
		//resolve the uri's query
		HashMap<String, String> variableMap = collector.resolveQuery(uri);
		HttpsResponse response = client.requestAccessToken(variableMap.get("code"));
		if (response.getResponseCode() == 200)
		{			
			accessToken = collector.parseAuthorizationToken(response);
			System.out.println(accessToken);
			return true;			
		}
		return false;
	}

	
	
	@Override
	public void initializeNotebook(ONData notebook) {
		collector.setNotebookID(notebook.getID());
	}

	
	
	@Override
	public ArrayList<ONData> getNotebooks() {
		try {
			HttpsResponse response = client.getONContent("/notebooks?select=" + this.notebookSelectParameters , this.accessToken, this.jsonContentType);
			return collector.createONData(collector.parseJSON(response));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	
	
	@Override
	public void openContent(ONData content) {
		try {
			client.openONContent(content.getLinkUri(), desktop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	@Override
	public ArrayList<ONData> searchPage(String pageName) {
		String[] split = pageName.split(" ");
		pageName = split[0];
		for (int i = 1; i < split.length; i++)
		{
			pageName += "%" + "20" + split[i];
		}
		
		String query = collector.buildPageFilterQuery(pageName, this.pageSelectParameters);
		HttpsResponse response = null;
		try {
			//System.out.println(this.accessToken);
			response = client.getONContent(query, this.accessToken, this.jsonContentType);
			
		} catch (Exception e) {
			System.out.println(e.getMessage() + ": Some Problem uccured while interacting with Microsoft Graph");
		}
		return collector.createONData(collector.parseJSON(response)); 
	}

	
	//##GETTER METHODES''//-------------------------------------------------------------------------------------------------------
	
	public String getNotebookID()	{
		return collector.getNotebookID();
	}
}
