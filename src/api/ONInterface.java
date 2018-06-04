package api;

import java.util.ArrayList;

import ONData.ONData;

public interface ONInterface {
	
	/**
	 * Starts the authorization process which will open the standard-browser in
	 * order to allow the user to log in with his Microsoft-account and accept the
	 * scope of access our program needs to operate. After that has been done the
	 * API-branch will go into datacollection mode.
	 * 
	 * @return true if authorization as successful and false if it has not been successful
	 */
	public boolean authorize();
	
	/**
	 * Needs to be called after authorize and given a notebook id in order to access Data
	 * @param id of the notebook to be searched for content
	 */
	public void initializeNotebook(ONData notebook);
	
	/**
	 * Use this to to get the ONData instances needed to call initializeNotebook
	 * @return A List of all notebooks on the Users OneDrive
	 */
	public ArrayList<ONData> getNotebooks();
	
	/**
	 * Opens a OneNoteClient with content as the presented page/section/notebook
	 * @param content
	 */
	public void openContent(ONData content);
}
