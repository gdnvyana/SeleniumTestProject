package com.litecart.selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.ResourceBundle;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CategoryPage extends BasePage {

    private final SelenideElement title;
    private final ElementsCollection products;

    public CategoryPage() {
        ResourceBundle locators = ResourceBundle.getBundle("locators/category");

        title = $(locators.getString("title.css"));
        products = $$(locators.getString("products.css"));
    }

    @Step("Получить текст заголовка")
    public String getTitleText() {
        return title.getText();
    }

    @Step("Проверить, содержит ли заголовок текст: {text}")
    public boolean isTitleContains(String text) {
        return getTitleText().contains(text);
    }

    @Step("Проверить, есть ли товары на странице")
    public boolean hasProducts() {
        return !products.isEmpty();
    }
}
