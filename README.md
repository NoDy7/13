# UI tests for Swag Labs (saucedemo.com)

Maven + Selenium 4 + TestNG, Page Object pattern. Practical work №13.

## Structure
```
src/test/java/pages/   Page Objects: BasePage, LoginPage, InventoryPage, CartPage, CheckoutPage
src/test/java/tests/   BaseTest + LoginTest, CartTest, CheckoutTest, SortingTest
src/test/java/utils/   Config (URL, timeout, demo credentials)
src/test/resources/    testng.xml
```

## Scenarios (11 tests)
| Class | Test | Type | Expected result |
|---|---|---|---|
| LoginTest | positiveLogin | positive | standard_user -> /inventory.html, title "Products" |
| LoginTest | negativeLockedOutUser | negative | "Epic sadface ... locked out" error |
| LoginTest | negativeWrongPassword | negative | "do not match" error |
| CartTest | addItemToCart | positive | badge = 1; cart shows Sauce Labs Backpack, $29.99 |
| CartTest | removeItemFromCart | positive | badge disappears after Remove |
| CheckoutTest | completeCheckout | positive | overview item/subtotal match; "Thank you for your order!" |
| CheckoutTest | checkoutRequiresFirstName | negative | "First Name is required" |
| SortingTest | sortNameAscending / sortNameDescending | positive | names ordered A-Z / Z-A |
| SortingTest | sortPriceAscending / sortPriceDescending | positive | prices ordered low-high / high-low |

Demo accounts: `standard_user`, `locked_out_user`, password `secret_sauce`.

## Run
```bash
mvn clean test                 # visible Chrome
mvn clean test -Pheadless      # headless (CI)
```
Reports: `target/surefire-reports/`.

## Design notes
- Explicit waits only (`WebDriverWait` + `ExpectedConditions`); no `implicitlyWait`, no `Thread.sleep`.
- Locators: `By.id` and `[data-test=...]` CSS selectors; no XPath needed.
- Each test starts a fresh browser (`@BeforeMethod`) and always quits it (`@AfterMethod(alwaysRun = true)`), so tests are independent and repeatable.
- Chrome password-manager popups are disabled in `BaseTest` so they can't cover the page.

## Git workflow
Branch: `feature/<Группа>_<Фамилия>_saucedemo`
```bash
git init
git add .
git commit -m "chore: init Maven + Selenium + TestNG project"
git checkout -b feature/<Группа>_<Фамилия>_saucedemo
# then atomic commits, e.g.
#   feat: add positive/negative login tests
#   feat: add cart and checkout page objects and tests
#   test: add sorting tests
git remote add origin https://github.com/<аккаунт>/ui-tests-saucedemo-<Фамилия>.git
git push -u origin feature/<Группа>_<Фамилия>_saucedemo
# open a Pull Request into main; do not merge before review
```
Put the branch URL and `git rev-parse HEAD` into the report.
