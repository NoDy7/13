package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingTest extends BaseTest {

    @Test(description = "Sort by name A to Z")
    public void sortNameAscending() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.sortBy("az");

        List<String> actual = inventory.getProductNames();
        List<String> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        Assert.assertEquals(actual, expected, "Products are not sorted A to Z");
    }

    @Test(description = "Sort by name Z to A")
    public void sortNameDescending() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.sortBy("za");

        List<String> actual = inventory.getProductNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());
        Assert.assertEquals(actual, expected, "Products are not sorted Z to A");
    }

    @Test(description = "Sort by price low to high")
    public void sortPriceAscending() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.sortBy("lohi");

        List<Double> actual = inventory.getProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        Assert.assertEquals(actual, expected, "Prices are not sorted low to high");
    }

    @Test(description = "Sort by price high to low")
    public void sortPriceDescending() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.sortBy("hilo");

        List<Double> actual = inventory.getProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());
        Assert.assertEquals(actual, expected, "Prices are not sorted high to low");
    }
}
