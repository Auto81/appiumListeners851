package helloListeners.pages.widgets;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContentWidget extends Widget {
    public ContentWidget(WebElement element) {
        super(element);
        //Needs included when calling explicit new ContentWidget() from TopPage, 'Normal WebElement'
        //PageFactory.initElements(new AppiumFieldDecorator(element), this);
        System.out.println("ContentWidget constructor called");
    }

    @FindBy(xpath = ".//p")
    private WebElement paragraph1;

    @FindBy(xpath = ".//p")
    private WebElement paragraph2;

    public String getParagraph1Text() {
        System.out.println("------------------------------");
        return paragraph1.getText();
    }
}
