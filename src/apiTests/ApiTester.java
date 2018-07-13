package apiTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import ONData.ONData;
import api.ApiController;

class ApiTester {

	public ApiController api;
	public Scanner scanner;
	
	@Before
	void setup() throws Exception	{
		api = new ApiController();
		scanner = new Scanner(System.in);
		api.login();
		String input = "";
		while (true) 
		{
			input = scanner.nextLine();
			if (!input.isEmpty())
			{
				break;
			}
		}
		api.authorize(input);
	}
	
//	@Test
//	/**
//	 * Tests login and authentication process of ApiController and subclasses
//	 */
//	void testLoginAndAuthentification() 
//	{
//		api.login();
//		try {
//			String input = "";
//			while (true) 
//			{
//				input = scanner.nextLine();
//				if (!input.isEmpty())
//				{
//					break;
//				}
//			}
//			scanner.close();
//			assertTrue(api.authorize(input));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	@Test
	void testInitializingNotebook() throws Exception
	{
		setup();
		ArrayList<ONData> notebooks = api.getNotebooks();
		notebooks = api.getNotebooks();
		for (Iterator<ONData> iter = notebooks.iterator(); iter.hasNext();)
		{
			ONData data = iter.next();
			System.out.println(data.toString());
			if (data.getName().equals("TestBook"))
			{
				api.initializeNotebook(data);
				assertEquals(data.getID(), api.getNotebookID());
			}
		}
		scanner.close();
		//Testing searchPage Flow
		ArrayList<ONData> pages = api.searchPage("Nice Handshake");
		for (Iterator<ONData> iter = pages.iterator(); iter.hasNext();)
		{
			System.out.println(iter.next().toString());
		}
		assertEquals("Nice Handshake", pages.get(0).getName());
	}
}
