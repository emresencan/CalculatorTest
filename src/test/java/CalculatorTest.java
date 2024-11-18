import common.Base;
import org.testng.annotations.Test;
import page.CalculatorPage;

public class CalculatorTest extends Base {

    @Test(description = "Calculator Test")
    public void CalculatorTest() {

        CalculatorPage calculatorPage = new CalculatorPage(windowsDriver);

        calculatorPage.clickNumber("Five")
                .clickNumber("Zero")
                .clickOperation("Plus")
                .clickNumber("Seven")
                .clickOperation("Equals")
                .checkResult("57");
    }

}
