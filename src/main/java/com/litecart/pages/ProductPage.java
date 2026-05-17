package com.litecart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ResourceBundle;

public class ProductPage extends BasePage {

    // Кнопка добавления товара в корзину
    private By addToCartButton;

    // Локатор для цены товара
    private By price;

    public ProductPage(WebDriver driver) {
        super(driver); // Вызываем конструктор BasePage

        // Загружаем локаторы из property-файла
        ResourceBundle locators = ResourceBundle.getBundle("locators/product");

        // Инициализируем локаторы из property-файла
        addToCartButton = By.name(locators.getString("add.to.cart.name")); // Кнопка "Add to Cart"
        price = By.cssSelector(locators.getString("price.css"));           // Цена товара
    }

    //Добавляет текущий товар в корзину
    public void addToCart() {
        click(addToCartButton); // Кликаем по кнопке добавления в корзину
    }

    //Проверяет, отображается ли кнопка "Add to Cart" на странице товара
    public boolean isAddToCartButtonDisplayed() {
        return isElementDisplayed(addToCartButton); // Проверяем видимость кнопки
    }

    //Проверяет, отображается ли цена на странице товара
    public boolean isPriceDisplayed() {
        return isElementDisplayed(price); // Проверяем видимость цены
    }

    //Возвращается на предыдущую страницу (главную)
    public HomePage navigateBack() {
        driver.navigate().back(); // Нажимаем кнопку "Назад" в браузере
        return new HomePage(driver); // Возвращаем объект главной страницы
    }
}