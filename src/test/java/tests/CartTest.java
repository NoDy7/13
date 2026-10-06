package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;

import java.util.List;

public class CartTest extends BaseTest {

    private static final String BACKPACK = "sauce-labs-backpack";

    @Test(description = "Add Backpack: badge = 1, cart shows its name and price")
    public void addItemToCart() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.addToCart(BACKPACK);
        inventory.waitForCartCount(1);
        Assert.assertEquals(inventory.getCartCount(), 1, "Cart badge should show 1");

        inventory.openCart();
        CartPage cart = new CartPage(driver);
        cart.waitUntilOpened();

        Assert.assertEquals(cart.getItemNames(), List.of("Sauce Labs Backpack"), "Unexpected cart content");
        Assert.assertEquals(cart.getItemPrices(), List.of("$29.99"), "Unexpected item price");
    }

    @Test(description = "Add then remove Backpack: badge disappears")
    public void removeItemFromCart() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.addToCart(BACKPACK);
        inventory.waitForCartCount(1);

        inventory.removeFromCart(BACKPACK);
        inventory.waitForCartCount(0);
        Assert.assertEquals(inventory.getCartCount(), 0, "Cart should be empty after removal");
    }
}
