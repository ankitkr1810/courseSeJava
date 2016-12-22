package ru.sd.tests.seljava.pages;

/**
 * Created by 1 on 22.12.2016.
 */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends Page {

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public CartPage open() {
        driver.get("http://localhost/litecart/en/checkout");
        return this;
    }

    public Integer get_cart_size(){
       return  productsRows.size();
    }

    @FindBy(xpath = ".//*[@id='order_confirmation-wrapper']/table/tbody/tr")
    public List<WebElement> productsRows;

    @FindBy(name = "remove_cart_item")
    public WebElement delButton;

}
