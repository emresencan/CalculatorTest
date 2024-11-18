package common;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Base {

    public WindowsDriver windowsDriver;

    @BeforeClass
    public void setup() {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");

        //Create a Chrome driver. All test classes use this.
        try {
            windowsDriver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            windowsDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void teardown() {
        windowsDriver.quit();
    }
}
