package org.rm.automation.utils.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.rm.automation.utils.LogManager;
import org.rm.automation.utils.ReadPropertyValues;

public class MeetingsRequests {

	static String token;
	static Properties settings = ReadPropertyValues
			.getPropertyFile("./Config/settings.properties");
	static Properties meetings = ReadPropertyValues
			.getPropertyFile("./Config/meetings.properties");
	
	/**
	 * API endpoints
	 */
	static String meetingEp = meetings.getProperty("meetings")
			.replace("[server]", settings.getProperty("server"))
			.replace("[port]", settings.getProperty("port"));
	
	static String meetingByIdEp = meetings.getProperty("meetingById")
			.replace("[server]", settings.getProperty("server"))
			.replace("[port]", settings.getProperty("port"));
	
	static String username = settings.getProperty("userES");
	static String password = settings.getProperty("passwordES");
	
	
	/**
	 * Get all the resources
	 * @throws UnsupportedOperationException
	 * @throws IOException
	 * @throws ParseException 
	 */
	public static ArrayList<JSONObject> getRoomMeetings() throws UnsupportedOperationException, IOException, ParseException
	{
		String service = ServicesRequests.getServiceId();
		String room = ConferenceRoomsRequests.getRoomId("Conference Room 1");
		String url = meetingEp
				.replace("[serviceId]", service)
				.replace("[roomId]", room);
		ArrayList<JSONObject> listResponse = new ArrayList<JSONObject>();
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpGet request = new HttpGet(url);
			
            request.setHeader("Content-type", "application/json");
            HttpResponse result = httpClient.execute(request);

            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            
            try {
                JSONParser parser = new JSONParser();
                Object resultObject = parser.parse(json);

                if (resultObject instanceof JSONArray) {
                    JSONArray array=(JSONArray)resultObject;
                    
                    for (Object object : array) {
                        JSONObject obj =(JSONObject)object;
                        listResponse.add(obj);
                    }

                }else if (resultObject instanceof JSONObject) {
                    JSONObject obj =(JSONObject)resultObject;                    
                    listResponse.add(obj);
                }
                return listResponse;

            } 
            catch (Exception e) {
            }
        } 
		catch (IOException ex) {
        }
		return listResponse;
	}
	
	public static ArrayList<JSONObject> getRoomMeetings(String roomName) throws UnsupportedOperationException, IOException, ParseException
	{
		String service = ServicesRequests.getServiceId();
		String room = ConferenceRoomsRequests.getRoomId(roomName);
		String url = meetingEp
				.replace("[serviceId]", service)
				.replace("[roomId]", room);
		ArrayList<JSONObject> listResponse = new ArrayList<JSONObject>();
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpGet request = new HttpGet(url);
			
            request.setHeader("Content-type", "application/json");
            HttpResponse result = httpClient.execute(request);

            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            
            try {
                JSONParser parser = new JSONParser();
                Object resultObject = parser.parse(json);

                if (resultObject instanceof JSONArray) {
                    JSONArray array=(JSONArray)resultObject;
                    
                    for (Object object : array) {
                        JSONObject obj =(JSONObject)object;
                        listResponse.add(obj);
                    }

                }else if (resultObject instanceof JSONObject) {
                    JSONObject obj =(JSONObject)resultObject;                    
                    listResponse.add(obj);
                }
                return listResponse;

            } 
            catch (Exception e) {
            }
        } 
		catch (IOException ex) {
        }
		return listResponse;
	}
	
	public static void postMeeting() throws UnsupportedOperationException, ParseException, IOException
	{
		String str = username+":"+password;
		byte[]   bytesEncoded = Base64.encodeBase64(str .getBytes());
		token = new String(bytesEncoded);
		
		String service = ServicesRequests.getServiceId();
		String room = ConferenceRoomsRequests.getRoomId("Conference Room 1");
		String url = meetingEp
				.replace("[serviceId]", service)
				.replace("[roomId]", room);
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpPost request = new HttpPost(url);
			
			/**
			 * Setting the headers
			 */
			request.setHeader("Content-type", "application/json");
			request.setHeader("Accept", "application/json");
			request.setHeader("Authorization", "Basic "+ token);
			
			/**
			 * Request's body
			 */
			JSONObject body = new JSONObject();
			body.put("organizer", "room.manager");
		  	body.put("title", "Whatever");
		  	body.put("start", "2015-09-10T23:00:00.000Z");
		  	body.put("end", "2015-09-10T23:30:00.000Z");
		  	body.put("location", "Conference Room1");
		  	body.put("roomEmail", "conferenceroom1@rmdom2008.lab");
		  	body.put("resources", new JSONArray());
		  	body.put("attendees", new JSONArray());
		  	
			StringEntity entity = new StringEntity(body.toString());
		    request.setEntity(entity);

            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            System.out.println(result.getStatusLine().getStatusCode());
            System.out.println(result.getStatusLine().getReasonPhrase());
            System.out.println(json);

        } 
		catch (IOException ex) {
        }
	}
	
	public static void postMeeting(String roomName, String meetingTitle, String startTime, String endTime, String[] attendees) throws UnsupportedOperationException, ParseException, IOException
	{		
		LogManager.info("MeetingRequests : Creating the meeting: "+meetingTitle+" in the room: "+roomName+" from: "+startTime+" to: "+endTime);
		
		String str = username+":"+password;		
		ArrayList attend = new ArrayList();		
		
		byte[] bytesEncoded = Base64.encodeBase64(str.getBytes());
		token = new String(bytesEncoded);
		
		String service = ServicesRequests.getServiceId();
		String roomId = ConferenceRoomsRequests.getRoomId(roomName);
		
		JSONObject conferenceRoom = ConferenceRoomsRequests.getRoom(roomId);
		String conferenceRoomEmail = conferenceRoom.get("emailAddress").toString();
		
		String url = meetingEp
				.replace("[serviceId]", service)
				.replace("[roomId]", roomId);
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpPost request = new HttpPost(url);
			
			/**
			 * Setting the headers
			 */
			request.setHeader("Content-type", "application/json");
			request.setHeader("Accept", "application/json");
			request.setHeader("Authorization", "Basic "+ token);
			
			/**
			 * Request's body
			 */
			JSONObject body = new JSONObject();
			body.put("organizer", username);
		  	body.put("title", meetingTitle);
		  	body.put("start", startTime);
		  	body.put("end", endTime);
		  	body.put("location", roomName);
		  	body.put("roomEmail", conferenceRoomEmail);		  	
		  	body.put("resources", new JSONArray().add(conferenceRoomEmail));
		  	
		  	if(attendees.length>=1){		  		
		  		for(int i=0;i<attendees.length;i++){
			  		attend.add(attendees[i]);		
			  	}		  		
		  	}
		  	
		  	body.put("attendees", attend);
		  	
			StringEntity entity = new StringEntity(body.toString());
		    request.setEntity(entity);

            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            System.out.println(result.getStatusLine().getStatusCode());
            System.out.println(result.getStatusLine().getReasonPhrase());
            System.out.println(json);

        } 
		catch (IOException ex) {
        }
	}
	
	public static void postMeeting(String roomName, String meetingTitle, String startTime, String endTime) throws UnsupportedOperationException, ParseException, IOException
	{		
		LogManager.info("MeetingRequests : Creating the meeting: "+meetingTitle+" in the room: "+roomName+" from: "+startTime+" to: "+endTime);
		
		// room.manager is the organizer and M@nager is the password
		String str = username+":"+password;			
		
		byte[] bytesEncoded = Base64.encodeBase64(str.getBytes());
		token = new String(bytesEncoded);
		
		String service = ServicesRequests.getServiceId();
		String roomId = ConferenceRoomsRequests.getRoomId(roomName);
		
		JSONObject conferenceRoom = ConferenceRoomsRequests.getRoom(roomId);
		String conferenceRoomEmail = conferenceRoom.get("emailAddress").toString();
		
		String url = meetingEp
				.replace("[serviceId]", service)
				.replace("[roomId]", roomId);
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpPost request = new HttpPost(url);
			
			/**
			 * Setting the headers
			 */
			request.setHeader("Content-type", "application/json");
			request.setHeader("Accept", "application/json");
			request.setHeader("Authorization", "Basic "+ token);
			
			/**
			 * Request's body
			 */
			JSONObject body = new JSONObject();
			body.put("organizer", username);
		  	body.put("title", meetingTitle);
		  	body.put("start", startTime);
		  	body.put("end", endTime);
		  	body.put("location", roomName);
		  	body.put("roomEmail", conferenceRoomEmail);		  	
		  	body.put("resources", new JSONArray().add(conferenceRoomEmail));
		  	body.put("attendees", new ArrayList());
		  	
			StringEntity entity = new StringEntity(body.toString());
		    request.setEntity(entity);

            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            System.out.println(result.getStatusLine().getStatusCode());
            System.out.println(result.getStatusLine().getReasonPhrase());
            System.out.println(json);

        } 
		catch (IOException ex) {
        }
	}
	
	public static void deleteMeeting(String meetingId) throws UnsupportedOperationException, IOException, ParseException
	{
		String service = ServicesRequests.getServiceId();
		String room = ConferenceRoomsRequests.getRoomId("Conference Room 1");
		String url = meetingByIdEp
				.replace("[serviceId]", service)
				.replace("[roomId]", room)
				.replace("[meetingId]", meetingId);
		
		String str = "room.manager:M@nager";
		byte[]   bytesEncoded = Base64.encodeBase64(str .getBytes());
		token = new String(bytesEncoded );
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpDelete request = new HttpDelete(url);

			request.setHeader("Content-type", "application/json");
            request.setHeader("Accept", "application/json");
			request.setHeader("Authorization", "Basic "+ token);

			HttpResponse result = httpClient.execute(request);

            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            
            System.out.println(result.getStatusLine().getStatusCode());
            System.out.println(result.getStatusLine().getReasonPhrase());
            System.out.println(json);
        } 
		catch (IOException ex) {
        }
	}
	
	public static void deleteMeeting(String meetingId, String roomName) throws UnsupportedOperationException, IOException, ParseException
	{
		LogManager.info("MeetingRequests : Deleting the meeting: "+meetingId+" in the room: "+roomName);
		
		String service = ServicesRequests.getServiceId();
		String room = ConferenceRoomsRequests.getRoomId(roomName);
		String url = meetingByIdEp
				.replace("[serviceId]", service)
				.replace("[roomId]", room)
				.replace("[meetingId]", meetingId);		
		
		String str = username + ":" +password;
		
		byte[]   bytesEncoded = Base64.encodeBase64(str .getBytes());
		token = new String(bytesEncoded );
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpDelete request = new HttpDelete(url);

			request.setHeader("Content-type", "application/json");
            request.setHeader("Accept", "application/json");
			request.setHeader("Authorization", "Basic "+ token);

			HttpResponse result = httpClient.execute(request);

            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            
            System.out.println(result.getStatusLine().getStatusCode());
            System.out.println(result.getStatusLine().getReasonPhrase());
            System.out.println(json);
        } 
		catch (IOException ex) {
        }
	}
	
	public static String getMeetingId(String name, String roomName) throws ParseException
	{
		String id = "";
		ArrayList<JSONObject> list;
		try {
			list = getRoomMeetings(roomName);
			for (JSONObject object : list) {
				if(object.get("title").toString().equals(name))
					id = object.get("_id").toString();
			}
		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
		}
		System.out.println(id);
		return id;
	}
	public static JSONObject getMeeting(String name, String roomName) throws ParseException
	{
		JSONObject res = null;
		ArrayList<JSONObject> list;
		try {
			list = getRoomMeetings(roomName);
			for (JSONObject object : list) {
				if(object.get("title").toString().equals(name))
					res = object;
			}
		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
		}
		return res;
	}
}
