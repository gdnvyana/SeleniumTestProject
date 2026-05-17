package com.litecart.selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.ResourceBundle;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class HomePage extends BasePage {

    private final String baseUrl;
    private final ElementsCollection products;
    private final SelenideElement cartQuantity;
    private final ElementsCollection menuLinks;

    public HomePage() {
        ResourceBundle config = ResourceBundle.getBundle("config/config");
        ResourceBundle locators = ResourceBundle.getBundle("locators/home");

        baseUrl = config.getString("base.url");
        products = $$(locators.getString("products.css"));
        cartQuantity = $(locators.getString("cart.quantity.css"));
        menuLinks = $$(locators.getString("menu.links.css"));
    }

    public HomePage open() {
        openUrl(baseUrl);
        return this;
    }

    public ElementsCollection getProducts() {
        return products;
    }

    public ProductPage clickFirstProduct() {
        products.first().click();
        return new ProductPage();
    }

    public ProductPage clickProductByIndex(int index) {
        products.get(index).click();
        return new ProductPage();
    }

    public String getCartQuantity() {
        return cartQuantity.getText();
    }

    public CategoryPage clickCategory(String categoryName) {
        menuLinks.findBy(text(categoryName)).click();
        return new CategoryPage();
    }

    public boolean isCartQuantityUpdated(String expectedQuantity) {
        cartQuantity.shouldHave(text(expectedQuantity));
        return getCartQuantity().equals(expectedQuantity);
    }
}