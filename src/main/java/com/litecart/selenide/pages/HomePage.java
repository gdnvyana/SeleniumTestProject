package com.litecart.selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

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

    @Step("Открыть главную страницу")
    public HomePage open() {
        openUrl(baseUrl);
        return this;
    }

    @Step("Получить список товаров")
    public ElementsCollection getProducts() {
        return products;
    }

    @Step("Кликнуть на первый товар")
    public ProductPage clickFirstProduct() {
        products.first().click();
        return new ProductPage();
    }

    @Step("Кликнуть на товар по индексу: {index}")
    public ProductPage clickProductByIndex(int index) {
        products.get(index).click();
        return new ProductPage();
    }

    @Step("Получить количество товаров в корзине")
    public String getCartQuantity() {
        return cartQuantity.getText();
    }

    @Step("Кликнуть на категорию: {categoryName}")
    public CategoryPage clickCategory(String categoryName) {
        menuLinks.findBy(text(categoryName)).click();
        return new CategoryPage();
    }

    @Step("Проверить обновление счетчика корзины до {expectedQuantity}")
    public boolean isCartQuantityUpdated(String expectedQuantity) {
        cartQuantity.shouldHave(text(expectedQuantity));
        return getCartQuantity().equals(expectedQuantity);
    }
}
