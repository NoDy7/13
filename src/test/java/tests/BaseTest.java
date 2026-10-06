package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.InventoryPage;
import pages.LoginPage;
import utils.Config;

import java.util.Map;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        // Suppress Chrome's password-manager / "data breach" popups that can cover the page
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false));
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /** Shared precondition: fresh session, logged in as standard_user. */
    protected InventoryPage loginAsStandardUser() {
        new LoginPage(driver).open().login(Config.STANDARD_USER, Config.PASSWORD);
        InventoryPage inventory = new InventoryPage(driver);
        inventory.waitUntilOpened();
        return inventory;
    }
}
