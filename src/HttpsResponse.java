
public class HttpsResponse {
	
	private int responseCode;
	private String responseString;
	
	HttpsResponse(int responseCode, String responseString)
	{
		this.responseCode = responseCode;
		this.responseString = responseString;
	}
	
	/**
	 * 
	 * @return The associated responsCode of the HttpsRespons
	 */
	public int getResponseCode()
	{
		return responseCode;
	}
	
	/**
	 * 
	 * @return The associated responsString of the HttpsRespons 
	 */
	public String getResponseString()
	{
		return responseString;
	}
}
