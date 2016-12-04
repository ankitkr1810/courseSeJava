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
        //System.out.println(((HasCapabilities) driver).getCapabilities());
        wait = new WebDriverWait(driver, 60);

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> { driver.quit(); driver = null; }));
    }
/*
    @Test
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
    }
    @Test
    public void Test9() {
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(visibilityOfElementLocated((By.cssSelector("table[class='dataTable']"))));
        //Список веб элементов стран
        List<WebElement> Countries = driver.findElements(By.cssSelector(".row>td>a:not([title='Edit'])"));
        //System.out.println("Countries: " + Countries.size());
        // Список строковый для сортировки
        List<String> CountriesList = new ArrayList<String>();
        // Список из количеств зон для каждой из стран
        ArrayList<Integer> ZoneList = new ArrayList<Integer>();
        int z=2;
        // Заполняем списки названий и количеств зон
        for(WebElement Countrie: Countries){
            WebElement zones = driver.findElement(By.xpath(".//*[@id='content']/form/table/tbody/tr["+z+"]/td[6]"));
            z++;
            int zoneCount = Integer.parseInt(zones.getAttribute("textContent"));
            //System.out.println(Countrie.getAttribute("textContent") +" - "+ zoneCount);
            CountriesList.add(Countrie.getAttribute("textContent"));
            ZoneList.add(zoneCount);
        }
        Collections.sort(CountriesList);
        int size = CountriesList.size();
        // Пробегаемся по спискам и сравниваем сортированный с веб элементами
        for(int i=0; i<size; i++){
            System.out.println(CountriesList.get(i) + " - " + Countries.get(i).getAttribute("textContent"));
            Assert.assertTrue(CountriesList.get(i).equals(Countries.get(i).getAttribute("textContent")));
            // Если зон больше 0 то аналогично сохраняем в список, сортируем и сравниваем
            if (ZoneList.get(i) > 0) {
                Countries.get(i).click();
                wait.until(visibilityOfElementLocated((By.cssSelector("table[id='table-zones']"))));
                List<WebElement> ZonesNames = driver.findElements(By.cssSelector("#table-zones>tbody>tr>td>input[name*='[name]'][type='hidden']"));
                List<String> ZoneNameList = new ArrayList<String>();
                for (WebElement ZonesName: ZonesNames) {
                    ZoneNameList.add(ZonesName.getAttribute("value"));
                }
                Collections.sort(ZoneNameList);
                int zonesize = ZoneNameList.size();
                // Пробегаемся по спискам и сравниваем сортированный список зон с веб элементами зон
                for(int j=0; j<zonesize; j++) {
                    System.out.println(ZoneNameList.get(j) + " - " + ZonesNames.get(j).getAttribute("value"));
                    Assert.assertTrue(ZoneNameList.get(j).equals(ZonesNames.get(j).getAttribute("value")));
                }
                // Проверили страну с зонами больше 0 и возвращаемся обратно к странам
                driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
                wait.until(visibilityOfElementLocated((By.cssSelector("table[class='dataTable']"))));
                Countries = driver.findElements(By.cssSelector(".row>td>a:not([title='Edit'])"));
            }
        }
    }
    @Test
    public void Test9_2() {
        driver.navigate().to("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(visibilityOfElementLocated((By.cssSelector("table[class='dataTable']"))));
        //Список веб элементов стран
        List<WebElement> Countries = driver.findElements(By.cssSelector(".row>td>a:not([title='Edit'])"));
        int countrySize = Countries.size();
        for(int i=0; i<countrySize; i++){
                String countryName = Countries.get(i).getAttribute("textContent");
                Countries.get(i).click();
                wait.until(visibilityOfElementLocated((By.cssSelector("table[id='table-zones']"))));
                String selector = driver.findElement(By.cssSelector(".select2-hidden-accessible>option[selected='selected']")).getAttribute("value");
                List<WebElement> ZonesNames = driver.findElements(By.cssSelector("option[selected='selected']:not([value='" + selector + "'])"));
                List<String> ZoneNameList = new ArrayList<String>();
                for (WebElement ZonesName: ZonesNames) {
                    ZoneNameList.add(ZonesName.getAttribute("textContent"));
                }
                Collections.sort(ZoneNameList);
                int zonesize = ZoneNameList.size();
                // Пробегаемся по спискам и сравниваем сортированный список зон с веб элементами зон
                for(int j=0; j<zonesize; j++) {
                    System.out.println(ZoneNameList.get(j) + " - " + ZonesNames.get(j).getAttribute("textContent"));
                    Assert.assertTrue(ZoneNameList.get(j).equals(ZonesNames.get(j).getAttribute("textContent")));
                }
                // Проверили страну с зонами больше 0 и возвращаемся обратно к странам
                driver.navigate().to("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
                wait.until(visibilityOfElementLocated((By.cssSelector("table[class='dataTable']"))));
                Countries = driver.findElements(By.cssSelector(".row>td>a:not([title='Edit'])"));
        }
    }
    @Test
    public void Test10() {
                driver.navigate().to("http://localhost/litecart/");
                wait.until(visibilityOfElementLocated(By.id("box-logotypes")));
                // All Ducks in Campaigns
                List<WebElement> Ducks = driver.findElements(By.xpath("//h3[text()='Campaigns']/..//a[contains(@title,'Duck') and @class='link']"));
                for (WebElement Duck: Ducks) {
                    WebElement weRegularPrice = Duck.findElement(By.xpath("//div/s"));
                    String regularPrice = weRegularPrice.getAttribute("textContent");
                    WebElement weCampaingPrice = Duck.findElement(By.xpath("//div/strong"));
                    String campaingPrice = weCampaingPrice.getAttribute("textContent");
                    String name = Duck.getAttribute("title");
                    String manufacturer = Duck.findElement(By.xpath("//div[@class='manufacturer']")).getAttribute("textContent");
                    String campaingPriceColor = weCampaingPrice.getCssValue("color");
                    String regularPriceColor = weRegularPrice.getCssValue("color");
                    String regularPriceSize = weRegularPrice.getCssValue("font-size");
                    String campaingPriceSize = weCampaingPrice.getCssValue("font-size");
                    String regularPriceWeight = weRegularPrice.getCssValue("font-weight");
                    String campaingPriceWeight = weCampaingPrice.getCssValue("font-weight");
                    String regularPriceDecoration = weRegularPrice.getCssValue("text-decoration");
                    //System.out.println(name + ", "+ manufacturer + ": " + regularPrice + " - " + campaingPrice);
                    //System.out.println("CampaingPriceColor: " + campaingPriceColor);
                    System.out.println("CampaingPriceSize: " + campaingPriceSize);
                    System.out.println("CampaingPriceWeight: " + campaingPriceWeight);
                    System.out.println("RegularPriceColor: " + regularPriceColor);
                    System.out.println("RegularPriceSize: " + regularPriceSize);
                    //System.out.println("RegularPriceWeight: " + regularPriceWeight);
                    //System.out.println("RegularPriceDecoration: " + regularPriceDecoration);
                    Duck.click();
                    wait.until(visibilityOfElementLocated(By.id("box-similar-products")));
                    //System.out.println(driver.findElement(By.xpath(".//*[@id='box-product']/div/h1[@itemprop='name']")).getAttribute("textContent"));
                    Assert.assertTrue(name.equals(driver.findElement(By.xpath(".//*[@id='box-product']/div/h1[@itemprop='name']")).getAttribute("textContent")));
                    WebElement weCampaingPrice2 = driver.findElement(By.xpath(".//*[@itemprop='price']"));
                    WebElement weRegularPrice2 = driver.findElement(By.xpath(".//s[@class='regular-price']"));
                    Assert.assertTrue(regularPrice.equals(weRegularPrice2.getAttribute("textContent")));
                    Assert.assertTrue(campaingPrice.equals(weCampaingPrice2.getAttribute("textContent")));
                    System.out.println(weRegularPrice2.getCssValue("color"));
                    //Assert.assertTrue(regularPriceColor.equals(weRegularPrice2.getCssValue("color")));
                    System.out.println(weRegularPrice2.getCssValue("font-size"));
                    //Assert.assertTrue(regularPriceSize.equals(weRegularPrice2.getCssValue("font-size")));
                    Assert.assertTrue(regularPriceWeight.equals(weRegularPrice2.getCssValue("font-weight")));
                    Assert.assertTrue(regularPriceDecoration.equals(weRegularPrice2.getCssValue("text-decoration")));
                    System.out.println(weCampaingPrice2.getCssValue("font-size"));
                    //Assert.assertTrue(campaingPriceSize.equals(weCampaingPrice2.getCssValue("font-size")));
                    System.out.println(weCampaingPrice2.getCssValue("font-weight"));
                    //Assert.assertTrue(campaingPriceWeight.equals(weCampaingPrice2.getCssValue("font-weight")));
                    Assert.assertTrue(campaingPriceColor.equals(weCampaingPrice2.getCssValue("color")));
                }
            }
    @Test // Customer Login
    public void Test11() {
        driver.navigate().to("http://localhost/litecart/");
        wait.until(visibilityOfElementLocated(By.id("box-logotypes")));
        driver.findElement(By.xpath(".//*[@id='box-account-login']/div/form/table/tbody//a")).click();
        wait.until(visibilityOfElementLocated(By.id("create-account")));
        // Create
        String firstName = "mr.First";
        String lastName = "mr.Last";
        String password = "^9#8d7%6.2Y";
        driver.findElement(By.cssSelector("input[name='firstname']")).sendKeys(firstName);
        driver.findElement(By.cssSelector("input[name='lastname']")).sendKeys(lastName);
        driver.findElement(By.cssSelector("input[name='address1']")).sendKeys("Krivolapova st.");
        driver.findElement(By.cssSelector("input[name='postcode']")).sendKeys("640000");
        driver.findElement(By.cssSelector("input[name='city']")).sendKeys("Kurgan");
        String email = "e" + java.util.UUID.randomUUID().toString().substring(0,7) + "@mail.com";
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name='phone']")).sendKeys("+79128220000");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys(password);
        driver.findElement(By.cssSelector("input[name='confirmed_password']")).sendKeys(password);
        //Login
        driver.findElement(By.name("create_account")).click();
        wait.until(visibilityOfElementLocated(By.id("box-account")));
        //Logout
        driver.findElement(By.xpath(".//*[@id='box-account']/div/ul/li[4]/a")).click();
        wait.until(visibilityOfElementLocated(By.xpath(".//button[@name='login']")));
        //New Login
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys(password);
        driver.findElement(By.name("login")).click();
        wait.until(visibilityOfElementLocated(By.id("box-account")));
    }
*/
    @Test
    public void Test12() {
        driver.navigate().to("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(visibilityOfElementLocated(By.id("sidebar")));
        //Catalog menu
        driver.findElement(By.xpath(".//*[@id='app-']/a/span[contains(text(),'Catalog')]")).click();
        wait.until(visibilityOfAllElementsLocatedBy(By.xpath(".//*[@id='content']/h1[contains(text(),'Catalog')]")));
        //Add new product
        driver.findElement(By.xpath(".//*[@id='content']/div[1]/a[contains(text(),'Add New Product')]")).click();
        //General tab
        wait.until(visibilityOfAllElementsLocatedBy(By.xpath(".//*[@id='content']/h1[contains(text(),'Add New Product')]")));
        driver.findElement(By.xpath(".//*[@id='tab-general']/table/tbody//input[@name='status' and @value='1']")).click();
        driver.findElement(By.xpath(".//input[@name='name[en]']")).sendKeys("The New Brave Duck!");
        driver.findElement(By.xpath(".//input[@name='code']")).sendKeys("Duckman 47");
        driver.findElement(By.xpath(".//input[@name='categories[]' and @data-name='Rubber Ducks']")).click();
        driver.findElement(By.xpath(".//input[@name='quantity']")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(By.xpath(".//input[@name='quantity']")).sendKeys("47");
        Select soldOut = new Select(driver.findElement(By.xpath(".//select[@name='sold_out_status_id']")));
        soldOut.selectByValue("2");
        driver.findElement(By.xpath(".//input[@name='date_valid_from']")).sendKeys("2016-12-03");
        driver.findElement(By.xpath(".//input[@name='date_valid_to']")).sendKeys("2016-12-31");
        driver.findElement(By.xpath(".//input[@name='new_images[]']")).sendKeys("e:\\duck.jpg");
        //Information tab
        driver.findElement(By.xpath(".//*[@id='content']/form/div/ul/li//a[@href='#tab-information']")).click();
        wait.until(visibilityOfAllElementsLocatedBy(By.xpath(".//div[@class='trumbowyg-editor']")));
        Select manufacturer = new Select(driver.findElement(By.name("manufacturer_id")));
        manufacturer.selectByValue("1");
        driver.findElement(By.xpath(".//input[@name='keywords']")).sendKeys("new duck");
        driver.findElement(By.xpath(".//input[@name='short_description[en]']")).sendKeys("The Brave New Duck and Co.");
        driver.findElement(By.xpath(".//div[@class='trumbowyg-editor']")).sendKeys("Each of the ducklings hatched from eggs wants to be one of the bravest duck");
        driver.findElement(By.xpath(".//input[@name='head_title[en]']")).sendKeys("Head title duck");
        driver.findElement(By.xpath(".//input[@name='meta_description[en]']")).sendKeys("Meta description duck");
        //Prices
        driver.findElement(By.xpath(".//*[@id='content']/form/div/ul/li//a[@href='#tab-prices']")).click();
        wait.until(visibilityOfAllElementsLocatedBy(By.xpath(".//div[@id='tab-prices']")));
        driver.findElement(By.xpath(".//input[@name='purchase_price']")).sendKeys(Keys.chord(Keys.CONTROL,"a"));
        driver.findElement(By.xpath(".//input[@name='purchase_price']")).sendKeys("25");
        Select purchase_price = new Select(driver.findElement(By.name("purchase_price_currency_code")));
        purchase_price.selectByValue("USD");
        driver.findElement(By.xpath(".//input[@name='prices[USD]']")).sendKeys(Keys.chord(Keys.CONTROL,"a"));
        driver.findElement(By.xpath(".//input[@name='prices[USD]']")).sendKeys("35");
        //Save and check
        driver.findElement(By.xpath(".//button[@name='save']")).click();
        driver.findElement(By.xpath(".//*[@id='doc-catalog']")).click();
        wait.until(visibilityOfAllElementsLocatedBy(By.xpath(".//*[@id='content']/form/table/tbody//a[contains(text(),'The New Brave Duck!')]")));
        Assert.assertTrue(driver.findElements(By.xpath(".//*[@id='content']/form/table/tbody//a[contains(text(),'The New Brave Duck!')]")).size() > 0);
    }
    @After
    public void stop() {
        //driver.quit();
        //driver = null;
    }
}