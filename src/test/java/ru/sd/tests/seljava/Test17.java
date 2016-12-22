package ru.sd.tests.seljava;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Test17 {

    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 60);
    }

    private void login_to_admin_panel(String login, String password) throws InterruptedException {
        driver.navigate().to("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys(login);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
        wait.until(visibilityOfElementLocated((By.cssSelector("table[class='dataTable']"))));
    }

    private void open_link_and_check_log(WebElement element) {
        System.out.println("Проверяем страницу товара: " + element.getAttribute("text"));
        element.click();
        for (LogEntry l : driver.manage().logs().get("browser").getAll()) {
            System.out.println(l);
        }
    }

    @Test //Задание номер 17
    public void cart_main() throws InterruptedException {
        // зайти в админку
        login_to_admin_panel("admin", "admin");
        // открыть пункт меню Catalog
        driver.navigate().to("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        wait.until(visibilityOfElementLocated((By.xpath(".//*[@id='content']/form/table/tbody//td/a[contains(text(),' Duck')]"))));
        // получаем список всех товаров уток
        List<WebElement> elements = driver.findElements(By.xpath(".//*[@id='content']/form/table/tbody//td/a[contains(text(),' Duck')]"));
        int element_count = elements.size();
        // пробегаемся по всем товарам в списке
        for (int i=0; i<element_count; i++){
            elements = driver.findElements(By.xpath(".//*[@id='content']/form/table/tbody//td/a[contains(text(),' Duck')]"));
            // открываем товар и смотим в лог браузера
            open_link_and_check_log(elements.get(i));
            driver.navigate().to("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
            wait.until(visibilityOfElementLocated((By.xpath(".//*[@id='content']/form/table/tbody//td/a[contains(text(),' Duck')]"))));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
