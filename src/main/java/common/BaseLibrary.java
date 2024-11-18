package common;

import com.google.common.util.concurrent.Uninterruptibles;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class BaseLibrary {

    public WindowsDriver windowsDriver;
    private static final int RETRY_COUNT = 20;

    //Constructor
    public BaseLibrary (WindowsDriver wd){
        this.windowsDriver = wd;
    }

    protected void clickElement(WebElement element, boolean... retry) {
        try {
            element.click();
        } catch (WebDriverException e) {
            retryToClick(element);
        }
    }

    private void retryToClick(WebElement element) {
        int i = 0;
        while (i < RETRY_COUNT) {
            try {
                WebElement refreshedElement = refreshWebElement(element);
                refreshedElement.click();
                Uninterruptibles.sleepUninterruptibly(500, TimeUnit.MILLISECONDS);
                return;
            } catch (WebDriverException e) {
                i++;
                Uninterruptibles.sleepUninterruptibly(300, TimeUnit.MILLISECONDS);
            }
        }
    }

    private WebElement refreshWebElement(WebElement element) {
        String elementInfo = element.toString();
        elementInfo = elementInfo.substring(elementInfo.indexOf("->"));
        String elementLocator = elementInfo.substring(elementInfo.indexOf(": "));
        elementLocator = elementLocator.substring(2, elementLocator.length() - 1);

        WebElement webElement = null;
        if (elementInfo.contains("-> link text:")) {
            webElement = findElement(By.linkText(elementLocator));
        } else if (elementInfo.contains("-> name:")) {
            webElement = findElement(By.name(elementLocator));
        } else if (elementInfo.contains("-> id:")) {
            webElement = findElement(By.id(elementLocator));
        } else if (elementInfo.contains("-> xpath:")) {
            webElement = findElement(By.xpath(elementLocator));
        } else if (elementInfo.contains("-> class name:")) {
            webElement = findElement(By.className(elementLocator));
        } else if (elementInfo.contains("-> css selector:")) {
            webElement = findElement(By.cssSelector(elementLocator));
        } else if (elementInfo.contains("-> partial link text:")) {
            webElement = findElement(By.partialLinkText(elementLocator));
        } else if (elementInfo.contains("-> tag name:")) {
            webElement = findElement(By.tagName(elementLocator));
        } else {
        }
        return webElement;
    }

    protected WebElement findElement(By by, boolean... waitForPageLoad) {
        try {

            WebElement webElement = windowsDriver.findElement(by);
            if (webElement.isEnabled() && webElement.isDisplayed()) {
                return webElement;
            }
            return retryTofindElement(by);
        } catch (WebDriverException e) {
            return retryTofindElement(by);
        }
    }

    private WebElement retryTofindElement(By by, boolean... failTest) {
        int i = 0;
        while (i < RETRY_COUNT) {
            try {
                WebElement webElement = windowsDriver.findElement(by);
                if (webElement.isEnabled() && webElement.isDisplayed()) {
                    return webElement;
                } else {
                    i++;
                     Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
                }

            } catch (WebDriverException e) {
                i++;
                Uninterruptibles.sleepUninterruptibly(300, TimeUnit.MILLISECONDS);
            }
        }

        if (failTest.length > 0 && !failTest[0]) {
            return null;
        }
        return null;
    }
}
