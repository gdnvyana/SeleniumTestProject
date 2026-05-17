package com.litecart.selenide.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

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

    @Step("Добавить товар в корзину")
    public ProductPage addToCart() {
        addToCartButton.shouldBe(visible).click();
        return this;
    }

    @Step("Проверить отображение кнопки Add to Cart")
    public boolean isAddToCartButtonDisplayed() {
        return addToCartButton.isDisplayed();
    }

    @Step("Проверить отображение цены")
    public boolean isPriceDisplayed() {
        return price.isDisplayed();
    }

    @Step("Вернуться на главную страницу")
    public HomePage navigateBack() {
        back();
        return new HomePage();
    }
}
