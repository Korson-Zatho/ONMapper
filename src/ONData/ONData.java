package ONData;

public class ONData {
	
	private final String id;
	private final String name;
	private final String linkUri;
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param type
	 * @param linkUri
	 * @throws BadValueException
	 */
	public ONData(String id, String name, String linkUri)
	{
		this.id = id;
		this.name = name;
		this.linkUri = linkUri;
	}
	
	/**
	 * @return The id of this ONData-Entity
	 */
	public String getID()
	{
		return id;
	}
	
	/**
	 * @return The name of this ONData-Entity
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * 
	 * @return The link Uri that leads to this ONData-Entity in Microsoft Graph
	 * This is needed to open the page/notebook in a ON-Instance
	 */
	public String getLinkUri()
	{
		return linkUri;
	}
}
