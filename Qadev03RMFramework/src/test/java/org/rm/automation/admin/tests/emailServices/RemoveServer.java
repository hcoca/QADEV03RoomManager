package org.rm.automation.admin.tests.emailServices;

import org.testng.annotations.Test;

import junit.framework.Assert;

import java.io.IOException;
import java.util.Properties;
import org.rm.automation.admin.pageobjects.HomePage;
import org.rm.automation.admin.pageobjects.LoginPage;
import org.rm.automation.admin.pageobjects.emailServers.EmailServersPage;
import org.rm.automation.admin.pageobjects.emailServers.RemoveServerpage;
import org.rm.automation.utils.LogManager;
import org.rm.automation.utils.ReadPropertyValues;
import org.rm.automation.utils.TestBaseSetup;
import org.rm.automation.utils.api.ServicesRequests;
import org.testng.annotations.AfterClass;


public class RemoveServer extends TestBaseSetup {
 
	
	Properties settings = ReadPropertyValues
			.getPropertyFile("./Config/settings.properties");
	// properties login at RM
	String username = settings.getProperty("username");
	String password = settings.getProperty("password");

	
    LoginPage objLogin;
    HomePage objHomePage;
    EmailServersPage emailserver;
    RemoveServerpage removeserv;
  @Test
  public void TestRemoveServer() throws UnsupportedOperationException, IOException {

	ServicesRequests.AddServices();

	LogManager.info("RemoveServer Test: begin remove testcase");
	objLogin = new LoginPage(driver);
	objHomePage = objLogin.SignIn(username, password);
	emailserver = objHomePage.SelectEmailServersOption();
	removeserv=emailserver.clickbtnremove();
	emailserver = removeserv.yesdelete();
	Assert.assertTrue(emailserver.buttonAddIsVisible());

  }
/*  @BeforeTest
  public void beforeTest() {
	try {
		LogManager.info("RemoveServer Test: before test RemoveServer");
		ServicesRequests.AddServices();

	} 
	catch (UnsupportedOperationException | IOException e) {
		e.printStackTrace();
		LogManager.warn("RemoveServer: before test fail");
	}
  }
  */
  @AfterClass
  public void AfterTest(){
	  try {
			LogManager.info("RemoveServer Test: after test add service");
			ServicesRequests.AddServices();
		} 
		catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
			LogManager.warn("RemoveServer: after test fail");
		}
  }

}