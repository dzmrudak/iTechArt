package testData.staticProviders;

import org.testng.annotations.DataProvider;

public class LoginProvider {

    @DataProvider(name = "Use Incorrect Credentials")
    public Object[][] createIncorrectCredential() {
        return new Object[][]{
                {"email@email.re", "11111111"}
        };
    }
}
