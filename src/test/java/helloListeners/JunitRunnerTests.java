package helloListeners;

import helloListeners.pages.TopPage;
import helloListeners.pages.widgets.ContentWidget;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

public class JunitRunnerTests {

    int pomCount = 5;

    @BeforeEach
    public void setup() {
        //encourage a GC
        System.gc();
    }

    //Listener size should be being Garbage collected between tests.
    //When TobPage is providing the ContentWidget via the new constructor, this works.
    //  Using 'Normal WebElement' comment lines in TopPage.java
    //  And PageFactory.initElements() in the Content widget
    //However when TobPage is providing the ContentWidget via FindBy Annotations,
    //  Some listeners are hanging over between test causing slow down eventually due to how listeners are iterated though
    //  Using 'Typed WebElement' comment lines in TopPage.java
    //  And comment out PageFactory.initElements() in the Content widget

    //To monitor the size of the proxy listeners, put watchers on the setListeners/getListeners methods in ProxyListenerContainer
    //'Normal WebElement' mode the listeners get emptied out, ie arent growing with each test
    //'Typed Widget' mode the listeners steadily grow. +5 for each test in this current setup


    @Test
    public void test1() {
        code();
    }

    @Test
    public void test2() {
        code();
    }

    @Test
    public void test3() {
        code();
    }

    @Test
    public void test4() {
        code();
    }

    @Test
    public void test5() {
        code();
    }

    private void code() {
        //Storage for loading lots of POMs
        List<TopPage> pages = new ArrayList<>();

        ChromeDriver driver = getDriver();

        //Given the test site is loaded
        driver.get("https://the-internet.herokuapp.com/challenging_dom");

        //When page/widgets are decorated 'pomCount' times
        System.out.println("Loading POMs");
        for (int i = 0; i <= pomCount + 1; i++) {
            System.out.println("POM " + i);
            pages.add(new TopPage
                    (driver)); //No hangover
        }

        System.out.println("Getting a POM");
        TopPage page = pages.get(pomCount);

        System.out.println("Getting text from the top level POM");
        String pageString = page.getParagraphTextDirectlyAtPageLevel(); //No listener hangover

        System.out.println("Getting widget from the top level POM");
        ContentWidget widget = page.getLargeContentWidget(); //No listener hangover

        System.out.println("Getting text from the widget");

        //each getParagraphText() below causes a listener hangover when decorated @FindBy(xxx) LargeContentWidget on the page
        //However when 'Normal WebElement' with a new ContentWidget constructor is used, the listeners don't hangover
        String widgetString = widget.getParagraphText();//this causes a listener hangover
        widget.getParagraphText(); //this causes a listener hangover
        widget.getParagraphText(); //this causes a listener hangover
        widget.getParagraphText(); //this causes a listener hangover
        widget.getParagraphText(); //this causes a listener hangover

        Assertions.assertEquals(
                pageString
                , widgetString
                ,"The located text from page and widget should be the same"
        );

        driver.quit();
    }

    private ChromeDriver getDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        return new ChromeDriver(options);
    }
}
