package hb.tests;

import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.testng.Assert;

public class Film extends TestBase {
	
	@Test
	public void testFilmAddPositive() throws Exception {
	    WebElement addMovie = driver.findElement(By.cssSelector("img[alt=\"Add movie\"]"));
	    addMovie.click();
	    WebElement nameField = driver.findElement(By.name("name"));
	    WebElement yearField = driver.findElement(By.name("year"));
	    nameField.sendKeys("Name of the film1");
	    yearField.sendKeys("1961");
	    driver.findElement(By.id("own_no")).click();
	    driver.findElement(By.id("seen_no")).click();
	    driver.findElement(By.id("submit")).click();
	    Assert.assertTrue(driver.findElement(By.id("movie")).isDisplayed());
	    driver.findElement(By.cssSelector(".center>nav>ul>li>a")).click(); 
	    }
	
	@Test
	public void testFilmAddNegative() throws Exception {
	    WebElement addMovie = driver.findElement(By.cssSelector("img[alt=\"Add movie\"]"));
	    addMovie.click();
	    WebElement nameField = driver.findElement(By.name("name"));
	    WebElement yearField = driver.findElement(By.name("year"));
	    nameField.sendKeys("Name of the film2");
	    //yearField.sendKeys("1961");
	    driver.findElement(By.id("own_no")).click();
	    driver.findElement(By.id("seen_no")).click();
	    driver.findElement(By.id("submit")).click();
	    Assert.assertTrue(driver.findElement(By.id("own_no")).isDisplayed());
	    driver.findElement(By.cssSelector(".center>nav>ul>li>a")).click(); 
	    }
	
	@Test
	public void testFilmxDelete() throws Exception {

		driver.findElement(By.cssSelector("div[title=\"Name of the film1\"]")).click();
		driver.findElement(By.cssSelector("img[title=\"Remove\"]")).click();
		driver.switchTo().alert().accept();
	}
}
