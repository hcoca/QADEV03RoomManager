package org.rm.automation.tablet.pageobjects.search;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.rm.automation.tablet.locators.search.SearchLocators;
import org.rm.automation.tablet.pageobjects.LoginPage;
import org.rm.automation.tablet.pageobjects.TabletPage;
import org.rm.automation.tablet.pageobjects.meetings.MeetingsPage;
import org.rm.automation.utils.LogManager;
import org.rm.automation.utils.Waiters;

public class SearchPage extends TabletPage{

	WebDriver driver;
	WebElement element;

	private List<WebElement> roomListSearch;
	private List<String> roomListLogin;
	
	@FindBy(id = SearchLocators.advancedButtonPath) WebElement advancedButton;
	@FindBy(id = SearchLocators.roomNameTextboxPath) WebElement roomNameTextbox;
	@FindBy(id = SearchLocators.capacityTextboxPath) WebElement capacityTextbox;
	@FindBy(id = SearchLocators.locationComboBoxPath) WebElement locationComboBox;
	@FindBy(xpath = SearchLocators.advancedLabelPath) WebElement advancedLabel;
	@FindBy(xpath = SearchLocators.clearButtonPath) WebElement clearButton;
	@FindBy(xpath = SearchLocators.scheduleTablePath) WebElement scheduleTable;
	@FindBy(xpath = SearchLocators.resourcesListPath) List<WebElement> resourcesList;
	@FindBy(xpath = SearchLocators.notFoundMessagePath) WebElement notFoundMessage;
	@FindBy(xpath = SearchLocators.roomTitle) WebElement spanSearchPageTitle;
	
	public SearchPage(WebDriver driver){
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	//***PAGE ACTIONS SECTION***//
	/**
	 * Select a resource
	 * @param resourceName
	 * @return
	 */
	public SearchPage selectResource(String resourceName){
		LogManager.info("SearchPage: Selecting a resource");

		Waiters.WaitByXPath(SearchLocators.resourcesListPath, driver);

		for (WebElement resource : resourcesList) {
			if(resource.getText().equals(resourceName)){
				resource
				.findElement(By.xpath(SearchLocators.resourceButtonPath))
				.click();
			}
		}
		return this;
	}
	
	/**
	 * Click the advanced search button
	 * @return
	 */
	public SearchPage enableAdvancedSearch(){
		LogManager.info("SearchPage: Enabling advanced search");

		Waiters.WaitById(SearchLocators.advancedButtonPath, driver);
		advancedButton.click();
		return this;
	}
	
	/**
	 * Set the Room Name
	 * @param roomName
	 * @return
	 */
	public SearchPage setRoomName(String roomName)
	{
		LogManager.info("SearchPage: Setting a room name");

		Waiters.WaitById(SearchLocators.roomNameTextboxPath,driver);
		roomNameTextbox.clear();
		roomNameTextbox.sendKeys(roomName);
		return this;
	}
	
	/**
	 * @param roomName
	 * @return
	 */
	public MeetingsPage selectRoom(String roomName){
		Waiters.WaitByXPath("//button[contains(.,'"+roomName+"')]",driver);
		element = driver.findElement(By.xpath("//button[contains(.,'"+roomName+"')]"));
		element.click();		
		return new MeetingsPage(driver);		
	}		
	/**
	 * Set the capacity
	 * @param capacity
	 * @return
	 */
	public SearchPage setCapacity(String capacity)
	{
		LogManager.info("SearchPage: Setting a capacity");

		Waiters.WaitById(SearchLocators.capacityTextboxPath, driver);
		capacityTextbox.clear();
		capacityTextbox.sendKeys(capacity);
		return this;
	}
	
	/**
	 * Set a location
	 * @param location
	 * @return
	 */
	public SearchPage setLocation(String location)
	{
		LogManager.info("SearchPage: Selecting a location");

		WebElement loc;
		loc = locationComboBox
				.findElement(By.xpath(SearchLocators.locationOptionPath.replace("location", location)));
		loc.click();
		return this;
	}
	
	/**
	 * Click in the "Clear" button
	 * @return
	 */
	public SearchPage clickClearButton()
	{
		LogManager.info("SearchPage: Clicking the clear button");

		Waiters.WaitByXPath(SearchLocators.clearButtonPath, driver);
		clearButton.click();
		return this;
	}
	
	//***GET SECTION***//
	/**
	 * Get the message when a room is not found
	 * @return
	 */
	public String getMessageNotFound()
	{
		return notFoundMessage.getText();
	}
	
	/**
	 * Get the list of rooms in the search page
	 */
	public void getRoomList()
	{
		roomListSearch = driver.findElements(By.xpath(SearchLocators.roomPath));
	}
	
	/**
	 * Method to get the room name after a search
	 * @return
	 */
	public String getSearchRoomName()
	{
		getRoomList();
		String roomNameActual = roomListSearch.get(0).getText();
		
		return roomNameActual;
	}
	
	/**
	 * Method to get the label of Advanced Search
	 * @return
	 */
	public String getLabelAdvancedSearh()
	{
		String label = advancedLabel.getText();
		return label;
	}
	
	/**
	 * Get the value of the room name text field
	 * @return
	 */
	public String getRoomName()
	{
		String roomName = roomNameTextbox.getText();
		return roomName;
	}
	
	/**
	 * Get the value of the location combo box
	 * @return
	 */
	public String getLocation()
	{
		WebElement loc;
		loc = locationComboBox.findElement(By.xpath(SearchLocators.selectedPath));
		String location = loc.getText();
		return location;
	}
	
	/**
	 * Get the value of the capacity text field
	 * @return
	 */
	public String getCapacity()
	{
		String capacity = capacityTextbox.getText();
		return capacity;
	}
	
	/**
	 * Get the name of a meeting
	 * @return
	 */
	public String getMeetingTitle()
	{
		WebElement meetingTitle = scheduleTable
				.findElement(By.xpath(SearchLocators.meetingTitlePath));
		String meetingTitleActual = meetingTitle.getText();
		return meetingTitleActual;
	}
	
	/**
	 * Get the list of rooms in the search page
	 * @return
	 */
	public List<WebElement> getRoomListSearch()
	{
		getRoomList();
		return roomListSearch;
	}
	
	/**
	 * Get the list of rooms in the login page
	 * @return
	 */
	public List<String> getRoomListLogin()
	{
		roomListLogin = LoginPage.getRoomList();
		return roomListLogin;
	}
	
	public boolean checkIfIconRedirectsToSearchPage(){
		LogManager.info("SearchPage : Verifying than the search icon redirects to the Search page");
		boolean result = false;
		Waiters.WaitByXPath(SearchLocators.roomTitle, driver);
		result = spanSearchPageTitle.getText().equals("Search")?true:false;
		return result;
	}
}
