package com.litecart.selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

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

    public String getTitleText() {
        return title.getText();
    }

    public boolean isTitleContains(String text) {
        return getTitleText().contains(text);
    }

    public boolean hasProducts() {
        return !products.isEmpty();
    }
}