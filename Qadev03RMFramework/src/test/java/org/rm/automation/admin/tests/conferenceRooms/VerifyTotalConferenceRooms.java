package org.rm.automation.admin.tests.conferenceRooms;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.util.List;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.rm.automation.admin.pageobjects.HomePage;
import org.rm.automation.admin.pageobjects.LoginPage;
import org.rm.automation.admin.pageobjects.conferenceRooms.ConferenceRoomsPage;
import org.rm.automation.utils.LogManager;
import org.rm.automation.utils.ReadPropertyValues;
import org.rm.automation.utils.TestBaseSetup;
import org.rm.automation.utils.api.ConferenceRoomsRequests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class VerifyTotalConferenceRooms extends TestBaseSetup{
	private Properties settings = ReadPropertyValues
			.getPropertyFile("./Config/settings.properties");
	String userName = settings.getProperty("username");
	String password = settings.getProperty("password");

	private LoginPage loginPage;
	private HomePage homePage;
	private ConferenceRoomsPage conferenceRoom;
	
	private int numberOfRooms;
	private int actualJSONResult;
	private int expectedResult;
	private int actualResult;
	
	@BeforeClass
 	public void setup(){
 		List<JSONObject> roomsList = ConferenceRoomsRequests.getRooms();
 		LogManager.info("VerifyTotalConferenceRooms: Executing Precondition, getting all rooms");
 		numberOfRooms = roomsList.size();
 	}
	
	@Test
	public void verifyTotalConferenceRooms(){
		LogManager.info("VerifyTotalConferenceRooms: Executing Test Case");
		
		loginPage = new LoginPage(driver);
		homePage = loginPage.SignIn(userName, password);
		conferenceRoom = homePage.SelectRoomsOption();
		
		expectedResult = conferenceRoom.getNumberOfRoomsFromLabel();
		actualJSONResult = numberOfRooms;
		actualResult = conferenceRoom.getNumberOfRoomsFromContainer();
		
		AssertJUnit.assertEquals(expectedResult, actualJSONResult);
		AssertJUnit.assertEquals(expectedResult, actualResult);
	}
}
