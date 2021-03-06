package org.rm.automation.tablet.pageobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.rm.automation.tablet.pageobjects.homepage.HomePage;
import org.rm.automation.utils.LogManager;
import org.rm.automation.utils.ReadPropertyValues;
import org.rm.automation.utils.Waiters;

public class LoginPage {
	
	private WebDriver driver;

	private static List<String> roomNames = new ArrayList<String>();
	List<WebElement> listRooms = new ArrayList<WebElement>();
	private final String roomListPath = "//div[@class='list-group ng-scope']";
	private final String roomNamePath = "//strong";
	
	@FindBy(xpath=roomListPath) WebElement roomList;
		
	@FindBy(id ="service-url-input")
	private WebElement urlTextBox;

	@FindBy(id ="username")
	private WebElement usernameTextBox;

	@FindBy(id ="password")
	private WebElement passwordTextBox;
	
	@FindBy(xpath ="//div[@type='submit']")
	private WebElement signInButton;
	
	@FindBy(xpath ="//div[@type='button']")
	private WebElement roomSelector;
	
	@FindBy(xpath ="//div[@class='item-box']")
	private WebElement roomlist;
	
	@FindBy(css ="button.btn.btn-primary")
	private WebElement accessTablet;
	
	private String userName;
	private String userPw;
	private String server;
	private String port;
	private String urlserver;
	
	Properties settings = ReadPropertyValues
			.getPropertyFile("./Config/settings.properties");
	
	public LoginPage(){}
	public LoginPage(WebDriver driver){
		this.driver = driver;
		driver.get(settings.getProperty("url_tablet"));
		PageFactory.initElements(driver, this);
		
		userName = settings.getProperty("username");
	 	userPw = settings.getProperty("passwordES");
	 	server = settings.getProperty("server");
		port = settings.getProperty("port");	
		urlserver  = "http://" + server + ":" + port + "/";
	}
	
	public void setUrl(String url)
	{
		LogManager.info("LoginPage : Typing the URL from the RM server");
		urlTextBox.clear();
		urlTextBox.sendKeys(url);	
	}
		
	public void setUserName(String userName) {
		LogManager.info("LoginPage : Typing RM admininstrator's username");
		usernameTextBox.clear();
		usernameTextBox.sendKeys(userName);
	}
	
	public void setPassword(String password)
	{
		LogManager.info("LoginPage : Typing RM administrator's password");
		passwordTextBox.clear();
		passwordTextBox.sendKeys(password);
	}
	
	public LoginPage login()
	{
		Waiters.WaitByVisibilityOfWebElement(signInButton, driver);
		LogManager.info("LoginPage : Clicking on sign in button to authenticate the account and enable the room selection");
		signInButton.click();
		return this;
	}


	public void picker() {
		Waiters.WaitByVisibilityOfWebElement(roomSelector, driver);
		roomSelector.click();
	}


	public HomePage access(String url, String admin, String pass ,String roomName)
	{	
		LogManager.info("LoginPage : Accessing to the selected conference room");
		setUrl(url);
		setUserName(admin);
		setPassword(pass);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		login();
		selectRoom(roomName);				
		return getTabletHomePage();
	}
	
	public HomePage access(String roomName)
	{
		return access(urlserver, userName, userPw, roomName);
	}
	
	public HomePage getTabletHomePage(){
		Waiters.WaitByVisibilityOfWebElement(accessTablet, driver);
		accessTablet.click();
		return new HomePage(driver);
	}
	

	public void selectRoom(String room){
		picker();
		setRoomList();
		List<WebElement> roomlis = roomlist.findElements(By.xpath("//strong"));
		Waiters.WaitByVisibilityOfWebElement(roomlist, driver);
		for (WebElement roomEl : roomlis) {
			if(roomEl.getText().contains(room)){
				roomEl.click();
				LogManager.info("LoginPageTablet: select room");
				break;
			}
		}
	}

	/**
	 * Method to save in a list the rooms that display in the login page
	 */
	private void setRoomList() {
		LogManager.info("LoginPage : Setting the rooms of login");
		if(listRooms.isEmpty())
		{
			listRooms = roomList.findElements(By.xpath(roomNamePath));
			if(roomNames.isEmpty())
			{
				for (WebElement room : listRooms) {
					roomNames.add(room.getText());
				}
			}
		}
	}
	
	/**
	 * Method to get the rooms displayed in the login page
	 * @return
	 */
	public static List<String> getRoomList()
	{
		LogManager.info("LoginPage : Getting the rooms of login");
		return roomNames;
	}
	
	public String getUserLoginName() {
		return userName;
	}
}