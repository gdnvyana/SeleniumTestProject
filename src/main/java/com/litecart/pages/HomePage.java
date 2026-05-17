package com.litecart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.ResourceBundle;


public class HomePage extends BasePage {

    // Локатор для списка товаров на главной странице
    private By products;

    // Локатор для счетчика товаров в корзине
    private By cartQuantity;

    // Локатор для ссылок главного меню
    private By menuLinks;


    public HomePage(WebDriver driver) {
        super(driver); // Вызываем конструктор BasePage

        // Загружаем локаторы из property-файла
        ResourceBundle locators = ResourceBundle.getBundle("locators/home");

        // Инициализируем локаторы из property-файла
        products = By.cssSelector(locators.getString("products.css"));        // Товары
        cartQuantity = By.cssSelector(locators.getString("cart.quantity.css")); // Счетчик корзины
        menuLinks = By.cssSelector(locators.getString("menu.links.css"));     // Ссылки меню
    }

    //Открывает главную страницу в браузере
    public void open() {
        ResourceBundle config = ResourceBundle.getBundle("config/config");
        driver.get(config.getString("base.url")); // Загружаем главную страницу
    }

    //Получает список всех товаров на главной странице
    public List<WebElement> getProducts() {
        waitForElementVisible(products); // Ждем появления товаров
        return driver.findElements(products); // Возвращаем список товаров
    }

    //Кликает по первому товару на главной странице
    public ProductPage clickFirstProduct() {
        getProducts().get(0).click(); // Кликаем по первому товару
        return new ProductPage(driver); // Возвращаем страницу товара
    }

    //Кликает по товару по его индексу в списке
    public ProductPage clickProductByIndex(int index) {
        getProducts().get(index).click(); // Кликаем по товару с указанным индексом
        return new ProductPage(driver); // Возвращаем страницу товара
    }

    //Получает количество товаров в корзине
    public String getCartQuantity() {
        return getText(cartQuantity); // Используем метод из BasePage
    }

    //Кликает по категории в главном меню
    public CategoryPage clickCategory(String categoryName) {
        List<WebElement> links = driver.findElements(menuLinks); // Получаем все ссылки меню

        for (WebElement link : links) { // Смотрим все ссылки
            if (link.getText().contains(categoryName)) { // Если текст ссылки содержит нужное название
                link.click(); // Кликаем по ссылке
                break; // Выходим из цикла (ссылка найдена)
            }
        }
        return new CategoryPage(driver); // Возвращаем страницу категории
    }
    //Проверяет, обновился ли счетчик корзины до ожидаемого значения

    public boolean isCartQuantityUpdated(String expectedQuantity) {
        // Ждем, когда текст счетчика станет равен ожидаемому значению
        wait.until(ExpectedConditions.textToBePresentInElementLocated(cartQuantity, expectedQuantity));

        // Проверяем, совпадает ли текущее значение с ожидаемым
        return getCartQuantity().equals(expectedQuantity);
    }
}