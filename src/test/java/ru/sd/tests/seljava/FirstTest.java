        package ru.sd.tests.seljava;

        import org.apache.commons.lang3.text.WordUtils;
        import org.junit.After;
        import org.junit.Assert;
        import org.junit.Before;
        import org.junit.Test;
        import org.openqa.selenium.By;
        import org.openqa.selenium.HasCapabilities;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.firefox.FirefoxDriver;
        import org.openqa.selenium.remote.DesiredCapabilities;
        import org.openqa.selenium.support.ui.WebDriverWait;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;

        import static org.openqa.selenium.support.ui.ExpectedConditions.*;

        public class FirstTest {

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
        System.out.println(((HasCapabilities) driver).getCapabilities());
        wait = new WebDriverWait(driver, 10);

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> { driver.quit(); driver = null; }));
    }

/*    @Test
    public void myFirstTest() {
        driver.navigate().to("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(visibilityOfElementLocated(By.id("sidebar")));
    }
    @Test
    public void mySecondTest() {
        driver.navigate().to("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(visibilityOfElementLocated(By.id("sidebar")));

        List<WebElement> items = driver.findElements(By.xpath("//ul[@id='box-apps-menu']/li"));
        int i = items.size();
        for (int n=1; n<=i; n++) {
            System.out.println("Текущий основной пункт: " + n);
            WebElement item = driver.findElement(By.xpath("//ul[@id='box-apps-menu']/li["+n+"]"));
            item.click();
            int j;
            try {
                wait.until(visibilityOfElementLocated(By.xpath("//ul[@id='box-apps-menu']/li["+n+"]/ul[@class='docs']")));
                items = driver.findElements(By.xpath("//ul[@id='box-apps-menu']/li[@id='app-' and @class='selected']/ul[@class='docs']/li"));
                j = items.size();
            }
            catch (org.openqa.selenium.TimeoutException e) {
                j = 0;
            }
            System.out.println("Вложенных = " + j);
            if (j == 0) {
                item = driver.findElement(By.xpath("//ul[@id='box-apps-menu']/li[" + n + "]"));
                wait.until(visibilityOfElementLocated(By.xpath("./*//*[@id='content']/h1")));
            }
            if (j > 0) {
                for (int m=1; m<=j; m++) {
                    driver.findElement(By.xpath("//li[@id='app-' and @class='selected']/ul[@class='docs']/li["+m+"]")).click();
                    wait.until(attributeContains(By.xpath("//li[@id='app-' and @class='selected']/ul[@class='docs']/li["+m+"]"), "class" , "selected"));
                }
            }
        }
    }
    @Test
    public void Test8() {
        driver.navigate().to("http://localhost/litecart/");
        wait.until(visibilityOfElementLocated(By.id("box-logotypes")));
        List<WebElement> Ducks = driver.findElements(By.cssSelector("div[class*='image-wrapper']"));
        System.out.println("Ducks: " + Ducks.size());
        for(WebElement Duck: Ducks){
            List<WebElement> stickers = Duck.findElements(By.cssSelector("div[class*='sticker']"));
            System.out.println("Duck: " + Duck + " found stickers: " + stickers.size());
            Assert.assertTrue(stickers.size()==1);
        }
    }*/
    @Test
    public void Test9() {
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(visibilityOfElementLocated((By.cssSelector("table[class='dataTable']"))));
        List<WebElement> Countries = driver.findElements(By.cssSelector(".row>td>a:not([title='Edit'])"));
        System.out.println("Countries: " + Countries.size());
        List<String> CountriesList = new ArrayList<String>();
        for(WebElement Countrie: Countries){
            //System.out.println(Countrie.getAttribute("textContent"));
            CountriesList.add(Countrie.getAttribute("textContent"));
            //Assert.assertTrue(stickers.size()==1);
        }
        Collections.sort(CountriesList);
        int size = CountriesList.size();
        for(int i=0; i<size; i++){
            System.out.println(CountriesList.get(i) + " - " + Countries.get(i).getAttribute("textContent"));
            Assert.assertTrue(CountriesList.get(i).equals(Countries.get(i).getAttribute("textContent")));
        }
    }
    @After
    public void stop() {
        //driver.quit();
        //driver = null;
    }
}