package com.academy.techcenture.stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EtsyPlaceOrderStepDefs {

    private WebDriver driver;

    @Given("user launches {string} browser and navites to {string}")
    public void user_launches_browser_and_navites_to(String browser, String url) {
        if (browser.equals("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }else if (browser.equals("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().window().maximize();
        driver.get(url);
    }

    @When("user enters {string} in the search input box and presses Enter")
    public void user_enters_in_the_search_input_box_and_presses_Enter(String searchKey) {
        driver.findElement(By.id("global-enhancements-search-query"))
                .sendKeys(searchKey + Keys.ENTER);
    }

    @Then("user should be navigated to the search results page")
    public void user_should_be_navigated_to_the_search_results_page() {
        WebElement result = driver.findElement(By.xpath("//span[@class='wt-display-inline-flex-sm']/span"));
        String resultNumber = result.getText();
        long numberOfItems = Long.parseLong(resultNumber.replaceAll("[a-zA-Z,]", "").trim());
        Assert.assertTrue(numberOfItems > 0);
    }

    @When("user clicks on the first search item")
    public void user_clicks_on_the_first_search_item() {
        driver.findElement( By.xpath("//ol[contains(@class,'tab-reorder-container')]/li[1]"))
                .click();
    }

    @Then("user should be navigate to the product details page")
    public void user_should_be_navigate_to_the_product_details_page() {

        String current = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handle: windowHandles) {
            if (!current.equals(handle)){
                driver.switchTo().window(handle);
                break;
            }
        }
    }


    @Then("user should be see the price and product description")
    public void user_should_be_see_the_price_and_product_description() {
        WebElement priceComponent = driver.findElement(By.xpath("//div[@data-buy-box-region='price']"));
        Assert.assertTrue(priceComponent.isDisplayed());
        WebElement productDescription = driver.findElement(By.xpath("//div[@data-buy-box-region='price']/following-sibling::div[2]"));
        Assert.assertTrue(productDescription.isDisplayed());
    }

    @Then("user selects {string} for the color and {string} for the size")
    public void user_selects_for_the_color_and_for_the_size(String color, String size) {
        WebElement colorElement = driver.findElement(By.id("variation-selector-0"));
        Select select = new Select(colorElement);
        select.selectByIndex(1);
        WebElement sizeElement = driver.findElement(By.id("variation-selector-1"));
        select = new Select(sizeElement);
        select.selectByIndex(1);
    }


    @Then("user clicks on Add to cart button")
    public void user_clicks_on_Add_to_cart_button() {
        driver.findElement(By.xpath("//button[contains(text(),'Add to cart')]"))
                .click();
    }

    @Then("user should see items added on the right side")
    public void user_should_see_items_added_on_the_right_side() {
        WebElement rightSideOverlay = driver.findElement(By.id("atc-overlay-content"));
        Assert.assertTrue(rightSideOverlay.isDisplayed());
    }


    @When("user clicks on view cart and checkout button")
    public void user_clicks_on_view_cart_and_checkout_button() {
       driver.findElement(By.xpath("//a[contains(text(),'View cart & check out')]")).click();
    }


    @Then("user should be navigated to the cart page")
    public void user_should_be_navigated_to_the_cart_page() {
        String actualTitle = driver.getTitle();
        Assert.assertEquals("Etsy - Shopping Cart", actualTitle);
    }

    @When("user enters {string} in the note placeholder box")
    public void user_enters_in_the_note_placeholder_box(String message) {
        driver.findElement(By.xpath("(//textarea[@name='message_to_seller'])[1]"))
                .sendKeys(message);
    }


    @When("user should see proceed to checkout button and click on it")
    public void user_should_see_proceed_to_checkout_button_and_click_on_it() {
        WebElement proceedToChckButton = driver.findElement(By.xpath("//button[starts-with(@class,'proceed-to-checkout')]"));
        proceedToChckButton.click();
    }

    @Then("user should see go to checkout as guest or register user option pop up")
    public void user_should_see_go_to_checkout_as_guest_or_register_user_option_pop_up() {
        WebElement popUp = driver.findElement(By.id("join-neu-overlay"));
        Assert.assertEquals(true, popUp.isDisplayed());
    }

    @When("use clicks on continue as guest option")
    public void use_clicks_on_continue_as_guest_option() {
        driver.findElement(By.xpath("//button[contains(text(),'Continue as a guest')]"))
                .click();
    }

    @Then("user should be navigated to shipping address page")
    public void user_should_be_navigated_to_shipping_address_page() {
        String actualTitle = driver.getTitle();
        Assert.assertEquals("Etsy - Checkout - Shipping", actualTitle);
    }

    @And("user enters shipping information")
    public void user_enters_shipping_information(List<Map<String,String>> data) {

        Map<String, String> shippingInfo = data.get(0);

        String email = shippingInfo.get("Email");
        String confirm = shippingInfo.get("Confirm");
        String country = shippingInfo.get("Country");
        String fullname = shippingInfo.get("Fullname");
        String street = shippingInfo.get("Street");
        String apt = shippingInfo.get("Apt");
        String zip = shippingInfo.get("Zip");
        String city = shippingInfo.get("City");
        String state = shippingInfo.get("State");

        driver.findElement( By.id("shipping-form-email-input")).sendKeys(email);
        driver.findElement( By.id("shipping-form-email-confirmation")).sendKeys(confirm);
        WebElement countryDropDown = driver.findElement(By.id("country_id"));
        Select select = new Select(countryDropDown);
        List<WebElement> options = select.getOptions();
        for (int i = 0; i < options.size(); i++) {
            String eachOption = options.get(i).getText().trim();
            if (eachOption.equals(country)){
                options.get(i).click();
                break;
            }
        }
        driver.findElement(By.id("name11-input")).sendKeys(fullname);
        driver.findElement(By.id("first_line12-input")).sendKeys(street);
        driver.findElement(By.id("second_line13-input")).sendKeys(apt);
        driver.findElement(By.id("zip14-input")).sendKeys(zip);
        driver.findElement(By.id("city15-input")).sendKeys(city);

        WebElement stateDropDown = driver.findElement(By.id("state16-select"));
        Select stateSelect = new Select(stateDropDown);
        stateSelect.getOptions().stream().
        filter(each ->{
            if (each.getText().trim().equals(state)) {
               return true;
            }
            return false;
        }).findFirst().get().click();
    }

    @Then("user clicks on continue to payment button")
    public void user_clicks_on_continue_to_payment_button() {
        driver.findElement( By.xpath("(//button/span[text()='Continue to payment'])[1]/..")).click();
    }


    @Then("user should be given {int} payment options")
    public void user_should_be_given_payment_options(Integer numberofpaymentOpton) {


    }


    @Then("user should choose {string} option")
    public void user_should_choose_option(String string) {

    }


    @Then("user should be able to enter card details")
    public void user_should_be_able_to_enter_card_details(io.cucumber.datatable.DataTable dataTable) {

    }

    @Then("user should be able to click review order button")
    public void user_should_be_able_to_click_review_order_button() {

    }

    @And("user should see create account option pop up")
    public void userShouldSeeCreateAccountOptionPopUp() {
        Assert.assertTrue(driver.findElement(By.id("join-neu-overlay")).isDisplayed());
        driver.findElement(By.xpath("//button[contains(text(),'Continue as guest')]"))
                .click();
    }
}
