package com.litecart.selenide.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.open;

public class BasePage {
    
    @Step("Открыть URL: {url}")
    protected void openUrl(String url) {
        open(url);
    }
}
