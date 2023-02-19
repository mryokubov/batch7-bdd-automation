Feature: Etsy place order functionality

  Scenario: Place order on Etsy.com as a guest
    Given user launches "chrome" browser and navites to "https://etcy.com"
    When user enters "Iphone 13 case" in the search input box and presses Enter
    Then user should be navigated to the search results page
    When user clicks on the first search item
    Then user should be navigate to the product details page
    And user should be see the price and product description
    And user selects "Charcoal" for the color and "13 x 5.75 inches" for the size
    And user clicks on Add to cart button
    Then user should see items added on the right side
    When user clicks on view cart and checkout button
    Then user should be navigated to the cart page
    When user enters "some dummy info" in the note placeholder box
    When user should see proceed to checkout button and click on it
    Then user should see go to checkout as guest or register user option pop up
    When use clicks on continue as guest option
    Then user should be navigated to shipping address page
    And user enters shipping information
    |Email                 |Confirm               |Country      |Fullname |Street         |Apt|Zip  |City  |State   |
    |kevin.lee114@gmail.com|kevin.lee114@gmail.com|United States|Kevin Lee|123 main street|111|15234|Mclean|Virginia|
    And user clicks on continue to payment button
    And user should see create account option pop up
    Then user should be given 4 payment options
    Then user should choose "Card" option
    And user should be able to enter card details
    |NameOnCard        |CardNumber      |ExpDate|Security|Billing|
    |Kevin Thompson Lee|1234987654362345|2/2024 |111     |true|
    Then user should be able to click review order button


