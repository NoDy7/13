package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By error = By.cssSelector("[data-test='error']");
    private final By subtotal = By.cssSelector("[data-test='subtotal-label']");
    private final By itemNames = By.cssSelector("[data-test='inventory-item-name']");
    private final By finishButton = By.id("finish");
    private final By completeHeader = By.cssSelector("[data-test='complete-header']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void fillInfo(String first, String last, String zip) {
        type(firstName, first);
        type(lastName, last);
        type(postalCode, zip);
    }

    public void clickContinue() {
        click(continueButton);
    }

    public String getErrorMessage() {
        return text(error);
    }

    public void waitUntilOverview() {
        wait.until(ExpectedConditions.urlContains("/checkout-step-two.html"));
        visible(finishButton);
    }

    public String getSubtotalText() {
        return text(subtotal);
    }

    public String getFirstItemName() {
        return text(itemNames);
    }

    public void clickFinish() {
        click(finishButton);
    }

    public String getCompleteHeader() {
        return text(completeHeader);
    }
}
