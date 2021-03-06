package org.rm.automation.tablet.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.rm.automation.tablet.pageobjects.homepage.HomePage;
import org.rm.automation.tablet.pageobjects.meetings.MeetingsPage;
import org.rm.automation.tablet.pageobjects.search.SearchPage;
import org.rm.automation.utils.LogManager;
import org.rm.automation.utils.Waiters;

//import framework.conferenceRooms.ConferenceRoomsPage;

public class TabletPage {	
	
	protected final String bttnSchedule="rm-panel-option.tile-column-option.tile-column-option-landscape > div.tile-button-schedule";
	protected final String bttnSearch="rm-panel-option.tile-column-option.tile-column-option-landscape > div.tile-button-search";
	protected final String icnHome="go-home";
	protected final String icnSearch="go-search";
	protected final String icnSchedule="go-schedule";
	
	protected WebDriver driver;

	@FindBy(css=bttnSchedule)
	WebElement scheduleButton;
	@FindBy(css=bttnSearch)
	WebElement searchButton;
	@FindBy(id=icnHome)
	WebElement homeCommonButton;
	@FindBy(id=icnSearch)
	WebElement searchCommonButton;
	@FindBy(id=icnSchedule)
	WebElement scheduleCommonButton;
	
	public TabletPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}

	public MeetingsPage selectSchedulePage()
	{
		LogManager.info("HomePage <=> TabletPage : Clicking on Schedule button");
		Waiters.WaitByCss(bttnSchedule, driver);
		if(scheduleButton.isDisplayed())
			scheduleButton.click();
		return new MeetingsPage(driver);
	}
	
	public SearchPage selectSearchPage(){
		LogManager.info("HomePage <=> TabletPage : Clicking on Search button");
		Waiters.WaitByCss(bttnSearch, driver);
		if(searchButton.isDisplayed())
			searchButton.click();
		return new SearchPage(driver);
	}
	
	public HomePage goHomePage(){	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LogManager.info("HomePage <=> TabletPage : Clicking on Home icon");		
		Waiters.WaitById(icnHome, driver);
		if(homeCommonButton.isDisplayed())
			homeCommonButton.click();
		return new HomePage(driver);
	}
	
	public SearchPage goSearchPage(){	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LogManager.info("HomePage <=> TabletPage : Clicking on Search icon");		
		Waiters.WaitById(icnSearch, driver);
		if(searchCommonButton.isDisplayed())
			searchCommonButton.click();
		return new SearchPage(driver);
	}
	
	public MeetingsPage goSchedulePage(){	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LogManager.info("HomePage <=> TabletPage : Clicking on Schedule icon");		
		Waiters.WaitById(icnSchedule, driver);
		if(scheduleCommonButton.isDisplayed())
			scheduleCommonButton.click();
		return new MeetingsPage(driver);
	}
	
	public WebDriver getDriver() {
		return driver;
	}
}
