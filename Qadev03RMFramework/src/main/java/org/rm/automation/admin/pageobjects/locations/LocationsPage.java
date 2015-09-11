package org.rm.automation.admin.pageobjects.locations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.rm.automation.admin.pageobjects.HomePage;
import org.testng.Assert;

public class LocationsPage extends HomePage{
	private Actions ac;

	private By addBtn = By.xpath("//button[@ui-sref='admin.locations.modal.add']");
	private By removeBtn = By.xpath("//button[@ui-sref='admin.locations.remove']");
	private By cellName;
	private By cellDisplayName;
	private By cellConfRoom;

	public LocationsPage(WebDriver driver) {
		super(driver);
		ac = new Actions(driver);
	}
	public boolean verifyLocationName(String name) {
		cellName = By.xpath("//span[contains(.,'"+name+"')]");;
		WebElement element = driver.findElement(cellName);
		String locationName = element.getText();
		String expectedLocationName = name;
		return locationName.equals(expectedLocationName);
	}
	public boolean verifyLocationDisplayName(String displayName) {		
		cellDisplayName = By.xpath("//div[@ng-dblclick='editLocation(row.entity)'and contains(.,'"+displayName+"')]");
		WebElement element = driver.findElement(cellDisplayName);
		String locationDisplayName = element.getText();
		String expectedLocationDisplayName = displayName;
		return locationDisplayName.contains(expectedLocationDisplayName);
	}
	public boolean verifyLocationConfRooms(String confRooms) {
		cellConfRoom = By.xpath("//small[contains(.,'"+confRooms+"')]");
		WebElement element = driver.findElement(cellConfRoom);
		String locationConfRooms = element.getText();
		String expectedLocationConfRooms = confRooms;
		return locationConfRooms.equals(expectedLocationConfRooms);
	}
	public LocationsPage verifyLocationsWasCreated(String name, String displayName, String confRooms ){
		Assert.assertTrue(verifyLocationName(name), "Location Name doesn't match");
		Assert.assertTrue(verifyLocationDisplayName(displayName), "Location Display Name doesn't match");
		Assert.assertTrue(verifyLocationConfRooms(confRooms), "Location COnference Rooms doesn't match");
		return this;
	}
	public boolean verifyAddButton() {
		WebElement element = driver.findElement(addBtn);
		String buttonText = element.getText();
		String expectedButtonText = "Add";
		return buttonText.contains(expectedButtonText);
	}
	public boolean verifyRemoveButton() {
		WebElement element = driver.findElement(removeBtn);
		String buttonText = element.getText();
		String expectedButtonText = "Remove";
		return buttonText.contains(expectedButtonText);
	}
	public FormLocationPage clickonAddButton() {
		WebElement element=driver.findElement(addBtn);
		if(element.isDisplayed()||element.isEnabled())
			element.click();
		return new FormLocationPage(driver);
	}
	public FormLocationPage clickonLocation(String locationToChange) {
		if(verifyLocationDisplayName(locationToChange))
		{
			WebElement element=driver.findElement(cellDisplayName);
			if(element.isDisplayed())
				ac.doubleClick(element).perform();
		}
		return new FormLocationPage(driver);
	}
	
	
}
