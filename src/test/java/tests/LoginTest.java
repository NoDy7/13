package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.Config;

public class LoginTest extends BaseTest {

    @Test(description = "Positive: standard_user lands on /inventory.html with 'Products' title")
    public void positiveLogin() {
        new LoginPage(driver).open().login(Config.STANDARD_USER, Config.PASSWORD);
        InventoryPage inventory = new InventoryPage(driver);
        inventory.waitUntilOpened();

        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"),
                "Expected URL to contain /inventory.html but was: " + driver.getCurrentUrl());
        Assert.assertEquals(inventory.getTitle(), "Products", "Unexpected page title");
    }

    @Test(description = "Negative: locked_out_user sees an error")
    public void negativeLockedOutUser() {
        LoginPage page = new LoginPage(driver).open();
        page.login(Config.LOCKED_OUT_USER, Config.PASSWORD);

        String error = page.getErrorMessage();
        Assert.assertTrue(error.contains("Epic sadface") && error.contains("locked out"),
                "Expected 'locked out' error but got: " + error);
    }

    @Test(description = "Negative: wrong password sees an error")
    public void negativeWrongPassword() {
        LoginPage page = new LoginPage(driver).open();
        page.login(Config.STANDARD_USER, "wrong_password");

        String error = page.getErrorMessage();
        Assert.assertTrue(error.contains("do not match"),
                "Expected 'do not match' error but got: " + error);
    }
}
