package com.ibm.test;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class LoginTest {
	
	//What is a test cycle???
	//Test Cycle is a logical grouping of tests 
	//- > SmokeTests, SanityTests, Regresssion Tests, End2EndTests etc
	
	WebDriver driver;
	
	@Test(dependsOnMethods = {"verifyRegistration"},groups = {"sanityTests"},dependsOnGroups = "smokeTest")
  public void verifyLoginWithValidCredential() {
	  
	  System.out.println("Login Works..");
	  
  }
  @Test(groups = {"smokeTest"})
  public void verifyRegistration() {
	  System.out.println("Verifying the reg for user..");
	  // By asssert we are checking amounting to check point >= Testing
	  
	  assertTrue(5>1);
	  //assertEquals("some title", driver.getTitle());
  }
  
  
  
  @Test(dependsOnMethods = {"verifyLoginWithValidCredential"},groups = {"regressionTest"},dependsOnGroups = "sanityTests")
  public void verifyLoginWithValidUsernameAndInvalidPassword() {
	  System.out.println("Invalid Password...try again");
  }
  @Test(dependsOnMethods = {"verifyLoginWithValidUsernameAndInvalidPassword","verifyLoginWithValidCredential"},groups = {"end2endtests"},dependsOnGroups = "regressionTest")
  public void verifyLoginWithInValidUsernameAndValidPassword() {
	  System.out.println("Invalid Username... try again");
  }
  
  
}
