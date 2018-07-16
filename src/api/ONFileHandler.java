package api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import ONData.MappingPoint;
import ONData.ONData;
import ONData.ONMFile;

public class ONFileHandler implements ONMIO{

	private FileWriter fWriter;
	private FileReader fReader;
	private BufferedReader buffReader;
	private InputStream inStream;
	private OutputStream outStream;
	
	
	
	@Override
	public ONMFile read(String url) throws FileNotFoundException {
		this.fReader = new FileReader(url);
		this.buffReader = new BufferedReader(fReader);
		String line = "";
		
		//Parameters that need to be read to create a ONMFile object
		String notebookId = "";
		String mapImageUrl = "";
		ArrayList<MappingPoint> mappingPoints = new ArrayList<MappingPoint>();
		
		//Starting Reading from the file
		try {
			// Read Headline to confirm this is a ONMFile if not throw Error
			if(!buffReader.readLine().equals("#ONMF V1.0#"))
			{
				//throw Exception wrong file.
			}
			
			//Read the next two lines containing notebookID and mapImageUrl
			notebookId = buffReader.readLine();
			mapImageUrl = buffReader.readLine();
			
			//Read until EOF all the MappingPoint objects on the file
			while((line = buffReader.readLine()) != null)
			{
				/* Split line at splitpoints and then create a MappingPoint object and add it to the list
				 * At the moment the following pattern is used:
				 * x-coord;y-coord;pageName;pageId;linkUri
				 */
				String[] features = line.split(";");
				ONData onData = new ONData(features[2], features[3], features[4]);
				MappingPoint point = new MappingPoint(onData, Integer.parseInt(features[0]), Integer.parseInt(features[1]));
				mappingPoints.add(point);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ONMFile(mappingPoints, notebookId, mapImageUrl);
	}
	
	
	
	@Override
	public boolean save(ONMFile onmToSave, String url)
	{
		try {
			this.fWriter = new FileWriter(url);
			fWriter.write(onmToSave.toString());
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	
}
