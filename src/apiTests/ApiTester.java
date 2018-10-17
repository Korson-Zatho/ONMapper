package apiTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import api.ApiController;
import ONData.ONData;

class ApiTester {

	public ApiController api;
	public Scanner scanner;
	
	@Before
	void setup() throws Exception	{
		api = new ApiController();
//		scanner = new Scanner(System.in);
//		api.login();
//		String input = "";
//		while (true) 
//		{
//			input = scanner.nextLine();
//			if (!input.isEmpty())
//			{
//				break;
//			}
//		}
//		api.authorize(input);
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
	
//	@Test
//	void testInitializingNotebook() throws Exception
//	{
//		setup();
//		ArrayList<ONData> notebooks = api.getNotebooks();
//		notebooks = api.getNotebooks();
//		for (Iterator<ONData> iter = notebooks.iterator(); iter.hasNext();)
//		{
//			ONData data = iter.next();
//			if (data.getName().equals("TestBook"))
//			{
//				api.initializeNotebook(data);
//				assertEquals(data.getID(), api.getNotebookID());
//			}
//		}
//		scanner.close();
//		//Testing searchPage Flow
//		ArrayList<ONData> pages = api.searchPage("Nice Handshake");
//		System.out.println(pages.get(0).getLinkUri());
//		assertEquals("Nice Handshake", pages.get(0).getName());
//		api.openContent(pages.get(0));
//	}
	
	@Test
	void openContentTest() throws Exception 
	{
		setup();
		api.openContent(new ONData("123123", "1231424", "onenote:https://d.docs.live.net/0b023ea43d100762/Dokumente/TestBook/TestAbschnitt.one#Nice%20Handshake&section-id=ca63e5fb-1e10-4f7a-8d74-1d23bfd1dc34&page-id=920e345e-c8c4-4f25-adb2-28f6e3105d4b&end"));
	}
}
