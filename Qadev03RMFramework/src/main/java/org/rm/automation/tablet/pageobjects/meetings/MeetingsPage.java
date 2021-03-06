package org.rm.automation.tablet.pageobjects.meetings;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.rm.automation.tablet.pageobjects.TabletPage;
import org.rm.automation.utils.ReadPropertyValues;
import org.rm.automation.utils.RoomManagerTime;
import org.rm.automation.utils.Waiters;
import org.rm.automation.tablet.locators.meetings.MeetingsPageLocator;

public class MeetingsPage extends TabletPage{
	
	private WebElement element;
	
	private Properties settings = ReadPropertyValues
			.getPropertyFile("./Config/settings.properties");
	private String server = settings.getProperty("server");
	private String port = settings.getProperty("port");
	private String url = "http://"+server+":"+port;
	
	@FindBy	(id=MeetingsPageLocator.txtboxOrganizer)
	protected WebElement tbOrganizer;	
	@FindBy	(id=MeetingsPageLocator.txtboxSubject)
	protected WebElement tbSubject;	
	@FindBy(xpath=MeetingsPageLocator.txtboxAttendees)
	protected WebElement tbAttendees;	
	@FindBy	(id=MeetingsPageLocator.txtboxBody)
	protected WebElement tbBody;	
	@FindBy	(xpath=MeetingsPageLocator.txtboxStart)
	protected WebElement tbStartTime;	
	@FindBy	(xpath=MeetingsPageLocator.txtboxEnd)
	WebElement tbEndTime;
	@FindBy	(xpath=MeetingsPageLocator.txtboxStartDate)
	WebElement tbStartDate;	
	@FindBy	(xpath=MeetingsPageLocator.txtboxEndDate)
	WebElement tbEndDate;
	@FindBy	(xpath=MeetingsPageLocator.bttnCreate)
	protected WebElement btnCreate;	
	@FindBy	(xpath=MeetingsPageLocator.txtboxPassword)
	protected WebElement tbPassword;	
	@FindBy	(xpath=MeetingsPageLocator.bttnSave)
	protected WebElement btnConfirmMeeting;	
	@FindBy	(xpath=MeetingsPageLocator.bttnSaveDelete)
	protected WebElement btnConfirmDeleteMeeting;	
	@FindBy	(xpath=MeetingsPageLocator.dvMeeting)
	protected WebElement divMeeting;	
	@FindBy	(xpath=MeetingsPageLocator.bttnUpdate)
	protected WebElement btnUpdate;
	@FindBy	(xpath=MeetingsPageLocator.bttnRemove)
	protected WebElement btnRemove;	
	@FindBy	(xpath=MeetingsPageLocator.dvAdvice)
	protected WebElement divMeetingAdvice;	
	@FindBy	(xpath=MeetingsPageLocator.dvRoomName)
	protected WebElement divRoomName;
	@FindBy(xpath=MeetingsPageLocator.icnRemoveAttendee)
	protected WebElement iconRemoveAttendee;
	@FindBy(xpath=MeetingsPageLocator.spnAttendee)
	protected WebElement spanAttendee;
	@FindBy(xpath=MeetingsPageLocator.spnTime)
	protected WebElement spanTime;
	@FindBy(xpath=MeetingsPageLocator.spnScheduleTitle)
	protected WebElement spanSchedulePageTitle;
	@FindBy(xpath=MeetingsPageLocator.divErrMsSubject)
	WebElement divErrMsjSubject;
	@FindBy(xpath=MeetingsPageLocator.divErrMsOrganizer)
	WebElement divErrMsjOrganizer;
	@FindBy(xpath=MeetingsPageLocator.divErrMsStartTime)
	WebElement divErrMsjStartTime;
	@FindBy(xpath=MeetingsPageLocator.divConfirmUpdateMs)
	WebElement divConfirmUpdateMsj;
	
	private WebDriver driver;
	
	public MeetingsPage(WebDriver driver){
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}	
	
	public MeetingsPage setOrganizer(String name){	
		Waiters.WaitByVisibilityOfWebElement(tbOrganizer,driver);		
		tbOrganizer.sendKeys(name);
		return this;
	}
	
	public MeetingsPage setSubject(String subject){
		Waiters.WaitByVisibilityOfWebElement(tbSubject,driver);		
		tbSubject.clear();
		tbSubject.sendKeys(subject);
		return this;
	}
	
	public MeetingsPage setAtendees(String name){
		Waiters.WaitByVisibilityOfWebElement(tbAttendees,driver);
		tbAttendees.clear();
		tbAttendees.sendKeys(name);
		tbAttendees.sendKeys(Keys.ENTER);
		return this;
	}
	
	public MeetingsPage setBody(String body){
		Waiters.WaitByVisibilityOfWebElement(tbBody,driver);
		tbBody.clear();
		tbBody.sendKeys(body);		
		return this;
	}
	
	public MeetingsPage setDates(String begin, String end){
		Waiters.WaitByVisibilityOfWebElement(tbStartTime,driver);		
		tbStartTime.clear();
		tbStartTime.sendKeys(begin);
		
		Waiters.WaitByVisibilityOfWebElement(tbEndTime,driver);
		tbEndTime.clear();
		tbEndTime.sendKeys(end);		
		return this;
	}
	
	public MeetingsPage confirmMeeting(){
		Waiters.WaitByVisibilityOfWebElement(btnCreate,driver);
		btnCreate.click();
		return this;
	}
	
	public MeetingsPage confirmUser(String password){
		Waiters.WaitByVisibilityOfWebElement(tbPassword,driver);
		tbPassword.clear();
		tbPassword.sendKeys(password);
		return this;
	}
	
	public MeetingsPage saveMeeting(){
		Waiters.WaitByVisibilityOfWebElement(btnConfirmMeeting,driver);
		btnConfirmMeeting.click();		
		return this;
	}
	
	public MeetingsPage saveDeleteMeeting(){
		Waiters.WaitByVisibilityOfWebElement(btnConfirmDeleteMeeting,driver);
		btnConfirmDeleteMeeting.click();
		
		return this;
	}
	
	public MeetingsPage selectMeeting(){
		Waiters.WaitByVisibilityOfWebElement(divMeeting,driver);
		divMeeting.click();
		return this;
	}
	
	public MeetingsPage updateMeeting(){
		Waiters.WaitByVisibilityOfWebElement(btnUpdate,driver);
		btnUpdate.click();		
		return this;
	}
	
	public MeetingsPage deleteMeeting(){
		Waiters.WaitByVisibilityOfWebElement(btnRemove,driver);
		btnRemove.click();		
		return this;
	} 
	
	public String meetingAdvices(){		
		String result = "";
		Waiters.WaitByVisibilityOfWebElement(divMeetingAdvice,driver);		
		result = divMeetingAdvice.getText();		
		return result;
	}
	
	public boolean isTheRightRoom(String roomName){
		try{
			Thread.sleep(1000);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		boolean result = false;
		Waiters.WaitByVisibilityOfWebElement(divRoomName,driver);		
		result = divRoomName.getText().equals(roomName)?true:false;	
		return result;
	}
	
	public MeetingsPage removeAttendee(){
		Waiters.WaitByVisibilityOfWebElement(iconRemoveAttendee,driver);
		iconRemoveAttendee.click(); 
		return this;
	}	
	
	public boolean isAttendeeAdded(String attendeeName)
	{		
		boolean result = false;
		Waiters.WaitByVisibilityOfWebElement(spanAttendee,driver);		
		result = (spanAttendee.isDisplayed() && spanAttendee.getText().equals(attendeeName))? true : false;
		return result;
	} 
	
	public List<WebElement> attendeeRemoved(){
		List<WebElement> attendees = driver.findElements(By.xpath(MeetingsPageLocator.spnTime));				
		return attendees;
	}
	
	public boolean checkTime(){		
		boolean result = false;
		String currentTime = RoomManagerTime.currenTime();
		Waiters.WaitByVisibilityOfWebElement(spanTime, driver);
		result = spanTime.getText().equals(currentTime)?true:false;
		return result;
	}	
	
	public boolean checkIfIconRedirectsToSchedulePage(){
		boolean result = false;
		Waiters.WaitByVisibilityOfWebElement(spanSchedulePageTitle, driver);		
		result = (spanSchedulePageTitle.getText().equals("Schedule") && driver.getCurrentUrl().equals(url+"/tablet/#/schedule"))?true:false;
		return result;
	}
	
	/**
	 * @return the text associated to the Schedule page.
	 */
	public String getScheduleLabelText(){
		//Waiters.WaitByVisibilityOfWebElement("//span[text() = 'Schedule']",driver);
		element = driver.findElement(By.xpath("//span[text() = 'Schedule']"));

		return element.getText();
	}
	
	
	public MeetingsPage setStartTime(String begin){
		Waiters.WaitByVisibilityOfWebElement(tbStartTime,driver);
		tbStartTime.clear();
		tbStartTime.sendKeys(begin);		
		return this;
	}
	public MeetingsPage setEndTime(String end){
		Waiters.WaitByVisibilityOfWebElement(tbEndTime,driver);
		tbEndTime.clear();
		tbEndTime.sendKeys(end);		
		return this;
	}
	public MeetingsPage selectMeeting(String subjectMeeting){
		element = driver.findElement(By.xpath("//span[contains(.,'"+subjectMeeting+"')]"));
		element.click();
		return this;
		
	}
		public boolean verifyErrorSubjectMessage(){
		Waiters.WaitByVisibilityOfWebElement(divErrMsjSubject,driver);
		return (divErrMsjSubject.isDisplayed());
	} 
	
	public boolean verifyErrorOrganizerMessage(){
		Waiters.WaitByVisibilityOfWebElement(divErrMsjOrganizer,driver);
		return (divErrMsjOrganizer.isDisplayed());			
	}
	
	public boolean verifyErrorFromHigherThanToMessage(){
		Waiters.WaitByVisibilityOfWebElement(divErrMsjStartTime,driver);
		return (divErrMsjStartTime.isDisplayed());			
	}
	
	public String verifyCurrentDateInFromField(){
		Waiters.WaitByVisibilityOfWebElement(tbStartDate,driver);
		String fromValue = tbStartDate.getAttribute("value");
		return fromValue;	
	}
	public String verifyCurrentDateInToField(){
		Waiters.WaitByVisibilityOfWebElement(tbEndDate,driver);
		String toValue = tbEndDate.getAttribute("value");	
		return toValue;

	}
	public boolean verifyMeetingWasUpdated(){
		Waiters.WaitByVisibilityOfWebElement(divConfirmUpdateMsj,driver);
		return (divConfirmUpdateMsj.isDisplayed());
	} 
}
