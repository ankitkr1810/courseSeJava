package ru.sd.tests.seljava.app;

/**
 * Created by 1 on 22.12.2016.
 */
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.sd.tests.seljava.pages.CartPage;
import ru.sd.tests.seljava.pages.MainPage;
import ru.sd.tests.seljava.pages.ProductPage;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class Application {

    private WebDriver driver;
    private WebDriverWait wait;

    private MainPage mainPage;
    private CartPage cartPage;
    private ProductPage productPage;

    public Application() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(FirefoxDriver.MARIONETTE, true);
        driver = new FirefoxDriver(caps);
        wait = new WebDriverWait(driver, 10);

        mainPage = new MainPage(driver);
        cartPage = new CartPage(driver);
        productPage = new ProductPage(driver);
    }

    public void quit() {
        driver.quit();
    }
    public void add_product_to_cart(String duckName) throws InterruptedException {
        mainPage.open();
        Integer countBeforeAdd;
        Integer countAfterAdd = 0;
        // Товаров в корзине
        countBeforeAdd = mainPage.get_count_prod_in_basket();
        //Выбрать утку
        mainPage.get_duck(duckName).click();
        //Добавить в корзину
        wait.until(visibilityOf(productPage.addProd));
        productPage.addProd.click();
        // подождать, пока счётчик товаров в корзине обновится
        for (int i=0; i<5; i++) {
            countAfterAdd = mainPage.get_count_prod_in_basket();
            if (countAfterAdd > countBeforeAdd) break;
            Thread.sleep(1000);
        }
        Assert.assertTrue(countAfterAdd > countBeforeAdd);

    }

    public void deleteFromCartOneProduct() throws InterruptedException {
        Integer sizeBeforeDelete = cartPage.get_cart_size();
        Integer sizeAfterDelete = 55555;
        cartPage.delButton.click();
        // после каждого удаления ждем пока внизу обновится таблица
        for (int i=0; i<50; i++) {
            sizeAfterDelete = cartPage.get_cart_size();
            if (sizeAfterDelete < sizeBeforeDelete) break;
            Thread.sleep(100);
        }
        Assert.assertTrue(sizeAfterDelete < sizeBeforeDelete);
    }

    public void open_basket() {
        cartPage.open();
    }
}