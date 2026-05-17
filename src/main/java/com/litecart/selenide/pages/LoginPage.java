package com.litecart.selenide.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.ResourceBundle;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage extends BasePage {

    private final String baseUrl;
    private final SelenideElement emailInput;
    private final SelenideElement passwordInput;
    private final SelenideElement loginButton;
    private final SelenideElement logoutLink;

    public LoginPage() {
        ResourceBundle config = ResourceBundle.getBundle("config/config");
        ResourceBundle locators = ResourceBundle.getBundle("locators/login");

        baseUrl = config.getString("base.url");
        emailInput = $(locators.getString("email.name"));
        passwordInput = $(locators.getString("password.name"));
        loginButton = $(locators.getString("login.button.name"));
        logoutLink = $(locators.getString("logout.link.css"));
    }

    @Step("Открыть страницу логина")
    public LoginPage open() {
        Selenide.open(baseUrl + "login");
        return this;
    }

    @Step("Выполнить вход с email: {email}")
    public LoginPage login(String email, String password) {
        emailInput.setValue(email);
        passwordInput.setValue(password);
        loginButton.click();
        return this;
    }

    @Step("Проверить отображение кнопки Logout")
    public boolean isLogoutLinkDisplayed() {
        return logoutLink.isDisplayed();
    }
}
