package ru.sd.tests.seljava;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Set;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Test14 {

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

    private void login_to_admin_panel(String login, String password) throws InterruptedException {
        driver.navigate().to("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys(login);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
        wait.until(visibilityOfElementLocated((By.cssSelector("table[class='dataTable']"))));
    }
    private String anyOfNotExisting(Set<String> old) throws InterruptedException {
        String newWindow="";
        Set<String> allNew = driver.getWindowHandles();
        for (int i=0; i<10; i++){
            allNew.removeAll(old);
            if (allNew.size()==1) {
                newWindow = allNew.iterator().next();
                break;
            }
            Thread.sleep(500);
            allNew = driver.getWindowHandles();
        }
        Assert.assertFalse(old.contains(newWindow));
        Thread.sleep(1000);
        return newWindow;
    }

    private void open_link(WebElement link) throws InterruptedException {
        String mainWindow = driver.getWindowHandle();
        Set<String> allOldWindows = driver.getWindowHandles();
        link.click();
        // ожидание появления нового окна идентификатор которого отсутствует в списке
        driver.switchTo().window(anyOfNotExisting(allOldWindows));
        driver.close();
        driver.switchTo().window(mainWindow);
    }

    @Test //Задание номер 14
    public void cart_main() throws InterruptedException {
        //1) зайти в админку
        login_to_admin_panel("admin", "admin");
        //2) открыть пункт меню Countries (или страницу http://localhost/litecart/admin/?app=countries&doc=countries)
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
        wait.until(visibilityOfElementLocated((By.cssSelector("form[name='countries_form']"))));
        //3) открыть на редактирование какую-нибудь страну или начать создание новой
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=edit_country&country_code=AF");
        wait.until(visibilityOfElementLocated((By.cssSelector("form[method='post']"))));
       //4) возле некоторых полей есть ссылки с иконкой в виде квадратика со стрелкой -- они ведут на внешние страницы и открываются в новом окне, именно это и нужно проверить.
        for(WebElement element: driver.findElements(By.xpath(".//*[@id='content']/form/table//a[@target='_blank']"))) {
            open_link(element);
        }
    }

    @After
    public void stop() {
        //driver.quit();
        //driver = null;
    }
}
