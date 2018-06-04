package ONData;

public class ONData {
	
	private final String id;
	private final String name;
	
	ONData(String id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	/**
	 * @return The id of this ON-Data-Entity
	 */
	public String getID()
	{
		return id;
	}
	
	/**
	 * @return The name of this ON-Data-Entity
	 */
	public String getName()
	{
		return name;
	}
}
