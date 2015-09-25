package org.rm.automation.tablet.tests.meetings;
import org.rm.automation.tablet.pageobjects.LoginPage;
import org.rm.automation.tablet.pageobjects.homepage.HomePage;
import org.rm.automation.tablet.pageobjects.meetings.*;
import org.rm.automation.utils.LogManager;
import org.rm.automation.utils.ReadPropertyValues;
import org.rm.automation.utils.RoomManagerTime;
import org.rm.automation.utils.api.*;


import java.util.ArrayList;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.rm.automation.utils.TestBaseSetup;
import org.testng.Assert;
import org.testng.annotations.*;
public class VerifyEndTimeCanBeUpdated extends TestBaseSetup{
	private LoginPage loginPage;
	private HomePage homePage;
	private MeetingsPage meetingsPage;
	private String serviceURL;
	private String roomName;
	private Properties settings = ReadPropertyValues
			.getPropertyFile("./Config/settings.properties");
	private String userName = settings.getProperty("username");
	private String password  = settings.getProperty("passwordES");
	private String server = settings.getProperty("server");
	private String port = settings.getProperty("port");
	String subject = "New Meeting";
	//private String startTime = RoomManagerTime.substractMinutes(0);
	//private String endTime = RoomManagerTime.addMinutes(20);
	private String startTime = "2015-09-24T22:24:33.000Z";
	private String endTime = "2015-09-24T22:54:33.000Z";
	
	
	private String newEndTime = RoomManagerTime.addMinutesToDate(15);
	@BeforeMethod
 	public void setup(){
		try{
			ArrayList<JSONObject> allRooms = ConferenceRoomsRequests.getRooms();
			roomName = allRooms.get(0).get("displayName").toString();
			serviceURL = "http://" + server + ":" + port + "/";
			MeetingsRequests.postMeeting(roomName, subject, startTime, endTime);
		}
		catch(Exception e){
			e.printStackTrace();
		}		
 	}
	@Test
	public void testVerifyEndTimeCanBeUpdated(){
		System.out.println("NEW TIME: "+newEndTime);
		LogManager.info("Executing: Verify End Time can be updated test case");
		loginPage = new LoginPage(driver);
 		homePage = loginPage.access(serviceURL, userName, password, roomName);
 		meetingsPage = homePage.selectSchedulePage();
 		meetingsPage.selectMeeting(subject)
 					.setEndTime(newEndTime)
 					.updateMeeting()
 					.confirmUser(password)
 					.saveMeeting();
 		Assert.assertTrue(meetingsPage.verifyMeetingWasUpdated());

	}
	@AfterMethod 
	public void deleteMeeting(){
		String id = "";
		try{
			id = MeetingsRequests.getMeetingId(subject, roomName);
			MeetingsRequests.deleteMeeting(id, roomName);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
