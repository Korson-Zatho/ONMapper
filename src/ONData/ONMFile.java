package ONData;

import java.util.ArrayList;
import java.util.Iterator;

public class ONMFile {

	private final ArrayList<MappingPoint> mappingPoints;
	private final String notebookId;
	private final String mapUrl;
	
	public ONMFile(ArrayList<MappingPoint> mappingPoints, String notebookId, String mapUrl)
	{
		if (mappingPoints == null || notebookId == null || mapUrl == null)
		{
			throw new IllegalArgumentException();
		}
		this.mappingPoints = mappingPoints;
		this.notebookId = notebookId;
		this.mapUrl = mapUrl;
	}
	
	public String toString()
	{
		StringBuffer buff = new StringBuffer();
		//Add Header to the String with Fileidentification notebookId and mapUrl all in new lines
		buff.append("#ONMF V1.0#");
		buff.append("\n\r");
		buff.append(notebookId);
		buff.append("\n\r");
		buff.append(mapUrl);
		buff.append("\n\r");
		//Iterate over mappingPoints and turn them to strings each MappingPoint object in a new line
		for (Iterator<MappingPoint> iter = mappingPoints.iterator(); iter.hasNext(); )
		{
			buff.append(iter.next().toString());
			buff.append("\n\r");
		}
		return buff.toString();
	}
}
