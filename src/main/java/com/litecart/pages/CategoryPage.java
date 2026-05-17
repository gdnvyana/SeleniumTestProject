package com.litecart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ResourceBundle;

public class CategoryPage extends BasePage {

    // Локатор для заголовка страницы
    private By title;

    // Локатор для списка товаров в категории
    private By products;

    public CategoryPage(WebDriver driver) {
        super(driver); // Вызываем конструктор класса BasePage

        // Загружаем локаторы из property-файла
        ResourceBundle locators = ResourceBundle.getBundle("locators/category");

        // Инициализируем локаторы из property-файла
        title = By.cssSelector(locators.getString("title.css"));    // Заголовок категории
        products = By.cssSelector(locators.getString("products.css")); // Список товаров
    }

    //Получает текст заголовка страницы категории
    public String getTitleText() {
        return getText(title); // Используем метод из BasePage
    }

    //Проверяет, содержит ли заголовок страницы указанный текст
    public boolean isTitleContains(String text) {
        return getTitleText().contains(text); // Проверяем вхождение текста
    }

    //Проверяет, есть ли товары на странице категории
    public boolean hasProducts() {
        // Если список товаров не пустой - true
        return !driver.findElements(products).isEmpty();
    }
}