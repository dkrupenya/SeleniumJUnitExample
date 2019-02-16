package ru.dkrpn.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.dkrpn.ComObj;
import ru.dkrpn.components.PanelComponent;
import ru.dkrpn.pages.FirstPage;

public class FirstTest {

    //Declare a Webdriver variable
    public WebDriver driver;

    //Declare a test URL variable
    public String testURL = "http://www.swtestacademy.com/";

    @Before
    public void setupTest (){
        System.setProperty( "webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe" );

        //Create a new ChromeDriver
        driver = new ChromeDriver();

        //Go to www.swtestacademy.com
        driver.navigate().to(testURL);
    }

    //-----------------------------------Tests-----------------------------------
    @Test
    public void firstTest () {
        //Get page title
        String title = driver.getTitle();

        //Print page's title
        System.out.println("Page Title: " + title);

        //Assertion
//        Assert.assertEquals(title, "Software Test Academy");

        FirstPage page = ComObj.definePage(FirstPage.class, driver);

        PanelComponent panel = page.getPanels().get(0);

        String text = panel.element(panel.title).getText();
        System.out.println("Page header: " + text);

        String votes = panel.element(panel.answers).getText();
        System.out.println("Page votes: " + votes);

        Assert.assertTrue(text.contains("Files API in JAVA 8"));
    }

    //-----------------------------------Test TearDown-----------------------------------
    @After
    public void teardownTest (){
        //Close browser and end the session
        driver.quit();
    }}
