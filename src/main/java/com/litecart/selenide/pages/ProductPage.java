package com.litecart.selenide.pages;

import com.codeborne.selenide.SelenideElement;

import java.util.ResourceBundle;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.back;

public class ProductPage extends BasePage {

    private final SelenideElement addToCartButton;
    private final SelenideElement price;

    public ProductPage() {
        ResourceBundle locators = ResourceBundle.getBundle("locators/product");

        addToCartButton = $(locators.getString("add.to.cart.name"));
        price = $(locators.getString("price.css"));
    }

    public ProductPage addToCart() {
        addToCartButton.shouldBe(visible).click();
        return this;
    }

    public boolean isAddToCartButtonDisplayed() {
        return addToCartButton.isDisplayed();
    }

    public boolean isPriceDisplayed() {
        return price.isDisplayed();
    }

    public HomePage navigateBack() {
        back();
        return new HomePage();
    }
}