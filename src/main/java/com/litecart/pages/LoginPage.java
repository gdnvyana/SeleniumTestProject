package com.litecart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ResourceBundle;

public class LoginPage extends BasePage {

    // Поле для ввода email/логина
    private By emailField;

    // Поле для ввода пароля
    private By passwordField;

    // Кнопка входа в систему (Login)
    private By loginButton;

    // Ссылка выхода из системы (Logout)
    private By logoutLink;


    public LoginPage(WebDriver driver) {
        super(driver); // Вызываем конструктор BasePage

        // Загружаем локаторы из property-файла
        ResourceBundle locators = ResourceBundle.getBundle("locators/login");

        // Инициализируем локаторы из property-файла
        emailField = By.name(locators.getString("email.name"));        // Поле email
        passwordField = By.name(locators.getString("password.name"));  // Поле пароля
        loginButton = By.name(locators.getString("login.button.name")); // Кнопка входа
        logoutLink = By.cssSelector(locators.getString("logout.link.css")); // Ссылка выхода
    }

    //Открывает страницу авторизации
    public void open() {
        ResourceBundle config = ResourceBundle.getBundle("config/config");
        driver.get(config.getString("base.url") + "login"); // Открываем страницу логина
    }

    //Выполняет вход в систему с указанными учетными данными
    public void login(String email, String password) {
        sendKeys(emailField, email);     // Вводим email
        sendKeys(passwordField, password); // Вводим пароль
        click(loginButton);               // Нажимаем кнопку входа
    }

    //Проверяет, отображается ли ссылка "Logout" (Выход)
    public boolean isLogoutLinkDisplayed() {
        waitForElementVisible(logoutLink); // Ждем появления ссылки Logout
        return isElementDisplayed(logoutLink); // Проверяем, видна ли ссылка
    }
}