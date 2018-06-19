package api;

import java.util.ArrayList;

import Exceptions.InvalidStateException;
import ONData.ONData;

public interface ONInterface {
	
	/**
	 * Starts the authorization process. The standard Webbrowser will open to allow the user
	 * to log in with his Microsoft-account and accept the scope of access our program needs
	 * to operate. After this has been done please call authorize(String uri) with the URI that
	 * the user has received in his browser tab.
	 */
	public void login();
	
	
	
	/**
	 * Checks the URI for the accessCode and reviews the status of the Request
	 * After that has been done successfully the API-branch will go into datacollection mode.
	 * @return true if authorization as successful and false if it has not been successful
	 * @throws InvalidStateException 
	 * @throws Exception 
	 */
	public boolean authorize(String uri) throws InvalidStateException, Exception;
	
	
	
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
	
	
	
	/**
	 *\\BOOKMARK\\
	 * 
	 * @param pageName -> Name of the Page that is to be searched for.
	 * @return A List of ONData Entities which represent Pages with a fitting Name.
	 */
	public ArrayList<ONData> searchPage(String pageName);
}
