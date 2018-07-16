package ONData;

public class MappingPoint {

	//final machen? eigentlich schon sie werden gelöscht wenn nicht mehr gebraucht sonst nur neue erstellt
	private final ONData onData;
	private final int x;
	private final int y;
	private final String information;
	
	/**
	 * To construct such an object you need an ONData object to map to a page in a notebook
	 * as well as coordinates on the map to determine where it should be displayed at.
	 * Some non-default things you could add include hover-over text which displays on a hover
	 * or anything like this. Maybe one could also allow a List of ONData objects?
	 * 
	 * @param daten
	 */
	public MappingPoint(ONData data, int x, int y)
	{
		this.onData = data;
		this.x = x;
		this.y = y;
		this.information = null;
	}
	
	public MappingPoint(ONData data, int x, int y, String information)
	{
		this.onData = data;
		this.x = x;
		this.y = y;
		this.information = information;
	}
	
	
	
	/**
	 * @return A representation of the MappingPoint object as a String which allows saving in a ONM-File
	 */
	public String toString()
	{
		StringBuffer buff = new StringBuffer();
		buff.append(x + ";");
		buff.append(y + ";");
		buff.append(onData.toString());
		return buff.toString();
	}
}
