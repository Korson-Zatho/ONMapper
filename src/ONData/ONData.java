package ONData;

import Exceptions.BadValueException;

public class ONData {
	
	private final String id;
	private final String name;
	private final String type;
	private final String linkUri;
	
	ONData(String id, String name, String type) throws BadValueException
	{
		this.id = id;
		this.name = name;
		if (type == "notebooks" || type == "sections" || type == "pages")
		{
			this.type = type;
		}	else	{
			throw new BadValueException("Value was: " + type + " , but should have been"
										 + "\"notebooks\", \"pages\" or \"sections\"");
		}
		this.linkUri = null;
	}
	
	ONData(String id, String name, String type, String linkUri) throws BadValueException
	{
		this.id = id;
		this.name = name;
		if (type == "notebooks" || type == "pages")
		{
			this.type = type;
		}	else	{
			throw new BadValueException("Value was: " + type + " , but should have been"
										 + "\"notebooks\", \"pages\"");
		}
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
	 * @return The type of this ONData-Entity can be ("notebooks", "sections", "pages")
	 */
	public String getType()
	{
		return type;
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
