package org.rm.automation.admin.tests.conferenceRooms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.rm.automation.admin.pageobjects.HomePage;
import org.rm.automation.admin.pageobjects.LoginPage;
import org.rm.automation.admin.pageobjects.conferenceRooms.ConferenceRoomsPage;
import org.rm.automation.admin.pageobjects.conferenceRooms.OutOfOrderPlanningPage;
import org.rm.automation.admin.pageobjects.conferenceRooms.RoomInfoPage;
import org.rm.automation.base.TestBaseSetup;
import org.rm.automation.utils.ReadPropertyValues;
import org.rm.automation.utils.api.ConferenceRoomsRequests;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class verifylogoStateVisible extends TestBaseSetup{
	private LoginPage login;
	private HomePage homePage;
	private ConferenceRoomsPage conferenceRoom;
	private RoomInfoPage roomInfo;
	private OutOfOrderPlanningPage ooop;
	
	// room properties 
	private String roomName;
	
	// login properties
	private Properties settings = ReadPropertyValues
			.getPropertyFile("./Config/settings.properties");
	private String userName = settings.getProperty("username");
	private String password = settings.getProperty("password");
	
	
 	@BeforeTest
 	public void setup() throws UnsupportedOperationException, IOException{
		ArrayList<JSONObject> allRooms = ConferenceRoomsRequests.getRooms();
		roomName = allRooms.get(0).get("displayName").toString();
 	}
	
 	
 	/// test not testing 
	@Test
	public void test(){
		login = new LoginPage(driver);
		homePage = login.SignIn(userName, password);
		conferenceRoom = homePage.SelectRoomsOption();
		/* Temporarily Out of Order       (Order , Temporarily)
		 * Closed for maintenance         (maintenance)
		 * Closed for reparations         (reparations)
		 * Reserved for a special event   (Reserved , special)*/
		String typeOOO = "maintenance";
		
		
		roomInfo = conferenceRoom.doubleClickConferenceRoom(roomName);
		ooop = roomInfo.clickOutOfOrderPlanningBtn();

		conferenceRoom = ooop.createOOOPactive(typeOOO);
		/// steps extra
		login =conferenceRoom.SignOut();
		homePage = login.SignIn(userName, password);
		conferenceRoom = homePage.SelectRoomsOption();
		Boolean espected = conferenceRoom.IsvisibleOOOIcon(typeOOO);
		Assert.assertTrue(espected);
		
	}
}
