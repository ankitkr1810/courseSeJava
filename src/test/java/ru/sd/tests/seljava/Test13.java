package ru.sd.tests.seljava;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Test13 {

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start() {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, 3);
            return;
        }
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(FirefoxDriver.MARIONETTE, true);
        driver = new FirefoxDriver(caps);
        tlDriver.set(driver);
        //System.out.println(((HasCapabilities) driver).getCapabilities());
        wait = new WebDriverWait(driver, 60);

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> { driver.quit(); driver = null; }));
    }

    private void open_and_add_product_to_cart(WebElement prodact) throws InterruptedException {
        prodact.click();
        wait.until(visibilityOfElementLocated(By.name("add_cart_product")));
        Integer countBeforeAdd;
        Integer countAfterAdd = 0;
        countBeforeAdd = Integer.parseInt(driver.findElement(By.xpath(".//*[@id='cart']//span[@class='quantity']")).getText());
        driver.findElement(By.name("add_cart_product")).click();
        // подождать, пока счётчик товаров в корзине обновится
        for (int i=0; i<5; i++) {
            countAfterAdd = Integer.parseInt(driver.findElement(By.xpath(".//*[@id='cart']//span[@class='quantity']")).getText());
            //System.out.println("In Cart: " + countAfterAdd + " product(s)");
            if (countAfterAdd > countBeforeAdd) break;
            Thread.sleep(1000);
        }
        Assert.assertTrue(countAfterAdd > countBeforeAdd);
        // вернуться на главную страницу
        driver.navigate().to("http://localhost/litecart/");
        wait.until(visibilityOfElementLocated(By.id("box-logotypes")));
    }
    private void deleteFromCartOneProduct() throws InterruptedException {
        Integer sizeBeforeDelete = driver.findElements(By.xpath(".//*[@id='order_confirmation-wrapper']/table/tbody/tr")).size();
        Integer sizeAfterDelete = 55555;
        driver.findElement(By.name("remove_cart_item")).click();
        // после каждого удаления ждем пока внизу обновится таблица
        for (int i=0; i<50; i++) {
            sizeAfterDelete = driver.findElements(By.xpath(".//*[@id='order_confirmation-wrapper']/table/tbody/tr")).size();
            if (sizeAfterDelete < sizeBeforeDelete) break;
            Thread.sleep(100);
        }
        Assert.assertTrue(sizeAfterDelete < sizeBeforeDelete);
    }
    @Test //Задание номер 13
    public void cart_main() throws InterruptedException {
        driver.navigate().to("http://localhost/litecart/");
        wait.until(visibilityOfElementLocated(By.id("box-logotypes")));
        // открыть страницу товара и добавить его в корзину
        WebElement redDuck = driver.findElement(By.xpath(".//*[@id='box-most-popular']//a[@class='link' and @title='Red Duck']"));
        open_and_add_product_to_cart(redDuck);
        WebElement blueDuck = driver.findElement(By.xpath(".//*[@id='box-most-popular']//a[@class='link' and @title='Blue Duck']"));
        open_and_add_product_to_cart(blueDuck);
        WebElement greenDuck = driver.findElement(By.xpath(".//*[@id='box-most-popular']//a[@class='link' and @title='Green Duck']"));
        open_and_add_product_to_cart(greenDuck);
        // 5) открыть корзину (в правом верхнем углу кликнуть по ссылке Checkout)
        driver.findElement(By.xpath(".//*[@id='cart']/a[@class='link']")).click();
        wait.until(visibilityOfElementLocated(By.id("box-checkout-summary")));
        // удалить все товары из корзины один за другим
        deleteFromCartOneProduct();
        deleteFromCartOneProduct();
        deleteFromCartOneProduct();
    }

    @After
    public void stop() {
        //driver.quit();
        //driver = null;
    }
}