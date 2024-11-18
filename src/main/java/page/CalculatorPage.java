package page;

import common.BaseLibrary;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class CalculatorPage extends BaseLibrary {


    public CalculatorPage(WindowsDriver wd) {
        super(wd);
    }

    protected WebElement getWebElementByName(String s) {
        return windowsDriver.findElementByName(s);
    }

    protected WebElement getWebElementByID(String s) {
        return windowsDriver.findElementByAccessibilityId(s);
    }

    public CalculatorPage clickNumber(String number) {
        clickElement(getWebElementByName(number));
        return this;
    }

    public CalculatorPage clickOperation(String operation) {
        clickElement(getWebElementByName(operation));
        return this;
    }

    public CalculatorPage checkResult(String result) {
        String rslt = getWebElementByID("CalculatorResults").getText().split(" is ")[1];
        if (rslt.equals(result))
            System.out.println(" Test Success ...");
        else{
            Assert.fail(" Result is not true ...");
        }
        return this;
    }
}
