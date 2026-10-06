package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage extends BasePage {

    private final By title = By.cssSelector("[data-test='title']");
    private final By cartLink = By.cssSelector("[data-test='shopping-cart-link']");
    private final By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");
    private final By sortSelect = By.cssSelector("[data-test='product-sort-container']");
    private final By itemNames = By.cssSelector("[data-test='inventory-item-name']");
    private final By itemPrices = By.cssSelector("[data-test='inventory-item-price']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public void waitUntilOpened() {
        wait.until(ExpectedConditions.urlContains("/inventory.html"));
        visible(title);
    }

    public String getTitle() {
        return text(title);
    }

    /** slug example: "sauce-labs-backpack" */
    public void addToCart(String slug) {
        click(By.id("add-to-cart-" + slug));
    }

    public void removeFromCart(String slug) {
        click(By.id("remove-" + slug));
    }

    /** Returns 0 when the badge is not rendered (empty cart). */
    public int getCartCount() {
        List<WebElement> badge = driver.findElements(cartBadge);
        return badge.isEmpty() ? 0 : Integer.parseInt(badge.get(0).getText().trim());
    }

    public void waitForCartCount(int expected) {
        if (expected == 0) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(cartBadge));
        } else {
            wait.until(ExpectedConditions.textToBe(cartBadge, String.valueOf(expected)));
        }
    }

    public void openCart() {
        click(cartLink);
    }

    /** value: az | za | lohi | hilo */
    public void sortBy(String value) {
        new Select(visible(sortSelect)).selectByValue(value);
    }

    public List<String> getProductNames() {
        visible(itemNames);
        return driver.findElements(itemNames).stream().map(e -> e.getText().trim()).toList();
    }

    public List<Double> getProductPrices() {
        visible(itemPrices);
        return driver.findElements(itemPrices).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "").trim()))
                .toList();
    }
}
