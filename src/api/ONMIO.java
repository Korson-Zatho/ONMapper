package api;

import java.io.FileNotFoundException;
import java.io.IOException;

import ONData.ONMFile;

public interface ONMIO {

	/**
	 * Reads Data from the ONM-object.
	 * @param url - The Url to the ONM-object.
	 * @return ONMData-object which contains Methods to access the data it provides.
	 * @throws FileNotFoundException 
	 */
	public ONMFile read(String url) throws FileNotFoundException;

	/**
	 * 
	 * @param onmToSave - The File that is to be saved
	 * @param url - The Path where the File is to be saved at
	 * @return - true if saving was successful, false if not
	 * @throws IOException 
	 */
	public boolean save(ONMFile onmToSave, String url) throws IOException;

	

}
