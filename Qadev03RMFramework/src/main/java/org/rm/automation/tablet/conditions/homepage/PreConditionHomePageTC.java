package org.rm.automation.tablet.conditions.homepage;


import java.util.ArrayList;
import java.util.Calendar;

import org.json.simple.JSONObject;
import org.rm.automation.utils.MeetingManager;
import org.rm.automation.utils.RoomManagerTime;
import org.rm.automation.utils.api.ConferenceRoomsRequests;
import org.rm.automation.utils.api.MeetingsRequests;


/**
 * @author luiscachi
 *
 */
public class PreConditionHomePageTC {

	private static String roomName = getRoomName();
	private static String startTimeCurrent = RoomManagerTime.substractMinutesToCurrentTime(2);
	private static String endTime = RoomManagerTime.addMinutesToCurrentTime(25);

	
	/**
	 * @return roomName
	 */
	public static String getRoomName()
	{
		ArrayList<JSONObject> allRooms = ConferenceRoomsRequests.getRooms();
		roomName = allRooms.get(0).get("displayName").toString();
		return roomName;
	}
	
	/**
	 * @return meeting ID
	 * create a meeting in current time
	 */
	public static String createCurrentMeeting(String roomName,String titleM){
		try {
			MeetingsRequests.postMeeting(roomName, titleM, startTimeCurrent, endTime);
			return MeetingsRequests.getMeetingId(titleM, roomName);
		}catch(Exception e){
			
		}
		return null;
	}
	
	/**
	 * @param roomName
	 * @param meetingTitle
	 * @param behindMinute 
	 * @param aheadMinute
	 * @return the ID of a meeting created at the execution time of the method call, the time difference 
	 * of the meeting created is given by the behindMinute and the aheadMinute parameters. 
	 */
	public static String createCurrentMeeting(String roomName, String meetingTitle, int behindMinute, int aheadMinute) {
		try {
			MeetingManager.createRunninMeeting(roomName, meetingTitle, behindMinute, aheadMinute);
			return MeetingsRequests.getMeetingId(meetingTitle, roomName);
		}catch(Exception e){
			
		}
		return null;
	}
	
	/**
	 * @return the meetingID
	 * create a meeting after the current time
	 */
	public static String createAfterMeeting(String roomName, String meetingT){
		try {
			String startTime = RoomManagerTime.addMinutesToCurrentTime(10);
			String endTime = RoomManagerTime.addMinutesToCurrentTime(12);
			MeetingsRequests.postMeeting(roomName, meetingT, startTime, endTime);
			return MeetingsRequests.getMeetingId(meetingT, roomName);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * @param meetingTitle
	 * @param startTime in format RM
	 * @param endTime in format RM
	 * @return MeetingId of the setup meeting
	 */
	public static String setupMeeting(String roomName,String meetingTitle , String startTime , String endTime){
		try {			
			MeetingsRequests.postMeeting(roomName, meetingTitle, startTime, endTime);
			return MeetingsRequests.getMeetingId(meetingTitle, roomName);
		}catch(Exception e){	
		}
		return null;
	}
	
	private static String getStarMeetingafternoon(){
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.HOUR_OF_DAY ,13);
      calendar.set(Calendar.MINUTE, 00);
      return RoomManagerTime.returnFormatRoomM(calendar.getTime());
	}
	
	private static String getEndMeetingafternoon(){
	      Calendar calendar = Calendar.getInstance();
	      calendar.set(Calendar.HOUR_OF_DAY, 14);
	      calendar.set(Calendar.MINUTE, 00);
	      return RoomManagerTime.returnFormatRoomM(calendar.getTime());
	}
	
	/**
	 * @return meeting Id
	 * 
	 * this method created a meeting in the afternoon between 13:00 - 14:00
	 * 
	 */
	public static String CreateMeetingInAfternoon(String roomName){
		try {
			String title = "meeting between 13:00 to 14:00";
			MeetingsRequests.postMeeting(roomName, title, getStarMeetingafternoon(), getEndMeetingafternoon());
			return MeetingsRequests.getMeetingId(title, roomName);
		}catch(Exception e){
			
		}
		return null;
	}
}
