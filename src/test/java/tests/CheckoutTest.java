package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;

public class CheckoutTest extends BaseTest {

    private static final String BACKPACK = "sauce-labs-backpack";

    private CheckoutPage startCheckout() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.addToCart(BACKPACK);
        inventory.waitForCartCount(1);
        inventory.openCart();
        CartPage cart = new CartPage(driver);
        cart.waitUntilOpened();
        cart.clickCheckout();
        return new CheckoutPage(driver);
    }

    @Test(description = "Positive: full checkout ends with 'Thank you for your order!'")
    public void completeCheckout() {
        CheckoutPage checkout = startCheckout();
        checkout.fillInfo("Ivan", "Ivanov", "12345");
        checkout.clickContinue();
        checkout.waitUntilOverview();

        Assert.assertEquals(checkout.getFirstItemName(), "Sauce Labs Backpack", "Wrong item on overview");
        Assert.assertTrue(checkout.getSubtotalText().contains("$29.99"),
                "Expected subtotal $29.99 but got: " + checkout.getSubtotalText());

        checkout.clickFinish();
        Assert.assertEquals(checkout.getCompleteHeader(), "Thank you for your order!",
                "Order confirmation text mismatch");
    }

    @Test(description = "Negative: empty First Name blocks Continue")
    public void checkoutRequiresFirstName() {
        CheckoutPage checkout = startCheckout();
        checkout.fillInfo("", "Ivanov", "12345");
        checkout.clickContinue();

        String error = checkout.getErrorMessage();
        Assert.assertTrue(error.contains("First Name is required"),
                "Expected 'First Name is required' but got: " + error);
    }
}
