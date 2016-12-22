package ru.sd.tests.seljava.pages;

/**
 * Created by 1 on 22.12.2016.
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends Page {

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("http://localhost/litecart/");
    }
    public WebElement get_duck(String duckName){
        WebElement duck = null;
        if (duckName.equals("Red Duck")) duck = redDuck;
        if (duckName.equals("Blue Duck")) duck = blueDuck;
        if (duckName.equals("Green Duck")) duck = greenDuck;
        return duck;
    }

    public Integer get_count_prod_in_basket(){
        return Integer.parseInt(count.getText());
    }

    @FindBy(xpath=".//*[@id='cart']//span[@class='quantity']")
    public WebElement count;

    @FindBy(xpath=".//*[@id='box-most-popular']//a[@class='link' and @title='Red Duck']")
    public WebElement redDuck;

    @FindBy(xpath=".//*[@id='box-most-popular']//a[@class='link' and @title='Blue Duck']")
    public WebElement blueDuck;

    @FindBy(xpath=".//*[@id='box-most-popular']//a[@class='link' and @title='Green Duck']")
    public WebElement greenDuck;


}