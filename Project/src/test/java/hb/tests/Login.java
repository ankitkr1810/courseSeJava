package hb.tests;

import org.testng.annotations.*;
import org.openqa.selenium.*;

public class Login extends TestBase {
  
  
  @Test
  public void testLoginAsAdmin() throws Exception {
    driver.get(baseUrl + "/php4dvd/");
    WebElement userNameField = driver.findElement(By.id("username"));
	userNameField.clear();
    String userName = "admin";
	userNameField.sendKeys(userName);
    WebElement passwordField = driver.findElement(By.name("password"));
	passwordField.clear();
    passwordField.sendKeys("admin");
    driver.findElement(By.name("submit")).click();
  }
}
