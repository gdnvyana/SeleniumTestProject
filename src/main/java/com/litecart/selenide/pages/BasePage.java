package com.litecart.selenide.pages;

import static com.codeborne.selenide.Selenide.open;

public class BasePage {

    // Только открытие URL, остальное Selenide делает сам
    protected void openUrl(String url) {
        open(url);
    }
}