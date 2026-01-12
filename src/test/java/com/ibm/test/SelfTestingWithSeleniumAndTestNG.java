package com.ibm.test;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
		WebElement table;
				
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
		public void SignupTest(String username, String password) throws InterruptedException {
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
		
		@Test(dependsOnMethods = {"SignupTest"})
		public void LoginCredential() {
			//driver.navigate().refresh();
			wait.until(ExpectedConditions.elementToBeClickable(By.id("login2"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername"))).sendKeys("Gur");
			driver.findElement(By.id("loginpassword")).sendKeys("gur");	
			driver.findElement(By.cssSelector("#logInModal > div > div > div.modal-footer > button.btn.btn-primary")).click();
		
		}
		
		@Test(dependsOnMethods = {"LoginCredential"})
		public void gotoCatagories() {
			driver.navigate().refresh();
			List<WebElement> CatogryItem = driver.findElements(By.id("itemc"));
			int len = CatogryItem.size();
			WebElement w = CatogryItem.get(len - 2);
			wait.until(ExpectedConditions.visibilityOf(w));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("CATEGORIES")));
//			driver.findElement(By.linkText("Laptops")).click();
			w.click();
			driver.findElement(By.cssSelector("#next2")).click();
			driver.findElement(By.linkText("MacBook Pro")).click();
			driver.findElement(By.linkText("Add to cart")).click();
			wait.until(ExpectedConditions.alertIsPresent()).accept();
			//driver.switchTo().alert().accept();
			
		}
		
		@Test(dependsOnMethods = {"gotoCatagories"})
		public void gotoCart() {
			driver.findElement(By.linkText("Cart")).click();
		}
		
		
		@Test(dependsOnMethods = {"addOtherItems"})
		public void checkCart() {
			
			table = driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[1]/div/table"));
			List<WebElement> rows = table.findElements(By.tagName("tr"));
////			System.out.println("No of Items in Cart is - " + rows.size());
////			System.out.println("All Done Bravo !! ");
			int count = 0;
			for(int i = 1;i<rows.size();i++) {
				List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
				
				
				for(int j = 0;j<cols.size();j++) {
					String data = cols.get(j).getText().trim();
					count++;
					System.out.println(data);
					System.out.println("This is number of items in cart " + count);
//					//WebElement delete = driver.findElement(By.linkText("Delete"));
//					if(data.equalsIgnoreCase("Delete")) {
//						count++;
//						
					}
			}
			}
		
		@Test(dependsOnMethods = {"gotoCart"})
		public void addOtherItems() {
		    // ALWAYS re-locate Home element freshly
		    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"navbarExample\"]/ul/li[1]/a"))).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Phones")));
		    System.out.println("Now Adding more items - ");
		    driver.findElement(By.linkText("Sony xperia z5")).click();
		    driver.findElement(By.linkText("Add to cart")).click();
			wait.until(ExpectedConditions.alertIsPresent()).accept();
		    driver.findElement(By.linkText("Cart")).click();
		}
			//return a;
			//System.out.println("No of Items in cart is - " + count);
		
		@AfterSuite
		public void tearDown() {
		driver.quit();
		}
		
		
}
