package helloListeners.pages;

import helloListeners.pages.widgets.ContentWidget;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TopPage {

    ChromeDriver driver;

    public TopPage(ChromeDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
        System.out.println("TopPage constructor called");
    }

    @FindBy(css = ".example > p")
    private WebElement pageContent;

    public String getParagraphTextDirectlyAtPageLevel() {
        return pageContent.getText();
    }

    @FindBy(css = ".example")
//    private ContentWidget content;              //Typed Widget
    private WebElement content;               //Normal WebElement

    public ContentWidget getLargeContentWidget() {
//        return content;                         //Typed Widget
        return new ContentWidget(content);    //Normal WebElement
    }


}
