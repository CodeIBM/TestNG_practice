package com.ibm.test;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SelfTestingWithSeleniumAndTestNG {
	
		WebDriver driver;
		WebDriverWait wait;
		Alert a;
		
		@BeforeSuite
		public void setup() {
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			driver.get("https://www.demoblaze.com/index.html");
			wait = new WebDriverWait(driver, Duration.ofSeconds(4));
		}
		
		@DataProvider(name = "SignupData")
		
		public Object[][] signupData(){
			return new Object[][] {
				{"Gur","gur"},
				{"aa","aaa"},
				{"gur","Gur"},
				{"Preet","123"}
			};			
		}
		
		@Test(dataProvider = "SignupData")
		public void ValidSignUp(String username, String password) throws InterruptedException {
			//Reset UI state
			wait.until(ExpectedConditions.elementToBeClickable(By.id("signin2"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sign-username"))).clear();
//			driver.findElement(By.id("signin2")).click();
			
			driver.findElement(By.xpath("//*[@id=\"sign-username\"]")).sendKeys(username);
			driver.findElement(By.id("sign-password")).clear();
			driver.findElement(By.id("sign-password")).sendKeys(password);
//			Assert.assertEquals()   //*[@id="signInModal"]/div/div/div[3]/button[2]
			driver.findElement(By.xpath("//*[@id=\"signInModal\"]/div/div/div[3]/button[2]")).click();
//			a = driver.switchTo().alert();
//			a.accept();
			try {
				wait.until(ExpectedConditions.alertIsPresent());
				a = driver.switchTo().alert();
				System.out.println("ALERT: " + a.getText());
				a.accept();
			}catch(Exception e) {
				System.out.println("NO ALERT for: " + username);
			}
			
			driver.navigate().refresh();
			
		}
		
//		@AfterMethod
//		public static void closeDriver() {
//			driver.close();
//		}
//		
		@Test
		public static void InvalidSignUp() {
			
		}
		
		@Test
		public static void ValidLoginCredential() {
			
		}
		
		@Test
		public static void InvalidLoginCredential() {
			
		}
		
		@Test
		public static void gotoCatagories() {
			
		}
		
		@Test
		public static void gotoCart() {
			
		}
		
		@AfterSuite
		public void tearDown() {
			driver.quit();
		}
		
		
}
