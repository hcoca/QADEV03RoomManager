package org.rm.automation.admin.tests.conferenceRooms;


import java.util.Properties;

import org.rm.automation.admin.conditions.conferenceRooms.PostConditionConferenceRooms;
import org.rm.automation.admin.conditions.conferenceRooms.PreConditionConferenceRooms;
import org.rm.automation.admin.pageobjects.HomePage;
import org.rm.automation.admin.pageobjects.LoginPage;
import org.rm.automation.admin.pageobjects.conferenceRooms.ConferenceRoomsPage;
import org.rm.automation.admin.pageobjects.conferenceRooms.RoomInfoPage;
import org.rm.automation.utils.LogManager;
import org.rm.automation.utils.ReadPropertyValues;
import org.rm.automation.utils.TestBaseSetup;
import org.rm.automation.utils.api.ConferenceRoomsRequests;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * @author Pedro David Fuentes Antezana.
 * 
 * This test case is to verify that the room capacity selected through GUI 
 * can be modified. 
 */
public class VerifyRoomCapacityUpdate extends TestBaseSetup{
	private Properties settings = ReadPropertyValues
			.getPropertyFile("./Config/settings.properties");
	private String userName = settings.getProperty("username");
	private String password = settings.getProperty("password");
	
	private LoginPage loginPage;
	private HomePage homePage;
	private ConferenceRoomsPage conferenceRoom;
	private RoomInfoPage roomInfo;

	private String roomId;
	private String roomCapacity;
	private String roomName;
	private String updatedCapacity = "571";
	
	private String expectedResult;
	private String actualJSONResult;
	private boolean actualResult;
	
 	@BeforeClass
 	public void setup(){
 		LogManager.info("VerifyRoomCapacityUpdate: Executing Precondition, getting a room");
 		roomId = PreConditionConferenceRooms.getRoomId();
		roomName = PreConditionConferenceRooms.getRoomName();
		System.out.println("The room before getting room capacity: \n" + ConferenceRoomsRequests.getRoom(roomId));
		roomCapacity = PreConditionConferenceRooms.getCapacity();
		System.out.println("The room after getting room capacity: \n" + ConferenceRoomsRequests.getRoom(roomId));
 	}
	
	@Test
	public void verifyRoomCapacityUpdate(){
		LogManager.info("VerifyRoomCapacityUpdate: Executing Test Case");
		
		loginPage = new LoginPage(driver);
		homePage = loginPage.SignIn(userName, password);
		conferenceRoom = homePage.SelectRoomsOption();
		roomInfo = conferenceRoom.doubleClickConferenceRoom(roomName);
		roomInfo = roomInfo.setCapacity(updatedCapacity);
		conferenceRoom = roomInfo.clickSaveBtn();
		System.out.println("The room after updating capacity: \n" + ConferenceRoomsRequests.getRoom(roomId));
		
		expectedResult = updatedCapacity;
		actualJSONResult = ConferenceRoomsRequests.getRoom(roomId).get("capacity").toString();
		AssertJUnit.assertEquals(expectedResult, actualJSONResult);
		
		actualResult = conferenceRoom.isCapacityUpdated(updatedCapacity, roomName);
		AssertJUnit.assertTrue(actualResult);
	}
	
	@AfterClass
	public void tearDown(){
		LogManager.info("VerifyRoomCapacityUpdate: Executing Postcondition, updating capacity to its original value");
		PostConditionConferenceRooms.setConferenceRoomCapacity(roomId, "capacity", roomCapacity);
		System.out.println("The room after restoring capacity: \n" + ConferenceRoomsRequests.getRoom(roomId));
	}
}