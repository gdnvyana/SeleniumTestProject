package com.litecart.tests;

import com.litecart.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

public class LitecartTest extends BaseTest {

    //Получает нужный email из config.properties
    private String getValidEmail() {
        return ResourceBundle.getBundle("config/config").getString("valid.email");
    }

    //Получает нужный пароль из config.properties
    private String getValidPassword() {
        return ResourceBundle.getBundle("config/config").getString("valid.password");
    }

    //Тест 1: Успешный вход учетными данными
    @Test(priority = 1)
    public void testLoginWithValidCredentials() {
        // Создаем объект страницы логина
        LoginPage loginPage = new LoginPage(driver);

        // Открываем страницу логина
        loginPage.open();

        // Выполняем вход с валидными данными
        loginPage.login(getValidEmail(), getValidPassword());

        // Проверяем, что появилась ссылка Logout (признак успешного входа)
        Assert.assertTrue(loginPage.isLogoutLinkDisplayed(), "Кнопка Logout не отображается");
    }

    //Тест 2: Добавление одного товара в корзину
    @Test(priority = 2)
    public void testAddOneProductToCart() {
        // Создаем объект главной страницы
        HomePage homePage = new HomePage(driver);

        // Открываем главную страницу
        homePage.open();

        // Кликаем по первому товару и переходим на страницу товара
        ProductPage productPage = homePage.clickFirstProduct();

        // Добавляем товар в корзину
        productPage.addToCart();

        // Проверяем, что счетчик корзины обновился до "1"
        Assert.assertTrue(homePage.isCartQuantityUpdated("1"), "В корзине не 1 товар");
    }

    //Тест 3: Добавление нескольких товаров в корзину
    @Test(priority = 3)
    public void testAddMultipleProductsToCart() {
        // Создаем объект главной страницы
        HomePage homePage = new HomePage(driver);

        // Открываем главную страницу
        homePage.open();

        // Добавляем 2 товара в корзину
        for (int i = 0; i < 2; i++) {
            // Кликаем по товару с индексом i
            ProductPage productPage = homePage.clickProductByIndex(i);

            // Добавляем товар в корзину
            productPage.addToCart();

            // Проверяем обновление счетчика
            Assert.assertTrue(homePage.isCartQuantityUpdated(String.valueOf(i + 1)),
                    "Счетчик корзины не обновился");

            // Возвращаемся на главную страницу для следующего товара
            homePage = productPage.navigateBack();
        }

        // Проверяем, что в корзине 2 товара
        Assert.assertEquals(homePage.getCartQuantity(), "2", "В корзине не 2 товара");
    }

    //Тест 4: Открытие категории Rubber Ducks
    @Test(priority = 4)
    public void testOpenCategoryRubberDucks() {
        // Создаем объект главной страницы
        HomePage homePage = new HomePage(driver);

        // Открываем главную страницу
        homePage.open();

        // Кликаем по категории "Rubber Ducks" в меню
        CategoryPage categoryPage = homePage.clickCategory("Rubber Ducks");

        // Проверяем, что заголовок страницы содержит слово "Duck"
        Assert.assertTrue(categoryPage.isTitleContains("Duck"), "Заголовок не содержит 'Duck'");

        // Проверяем, что на странице есть товары
        Assert.assertTrue(categoryPage.hasProducts(), "На странице нет товаров");
    }

    //Тест 5: Проверка страницы товара
    @Test(priority = 5)
    public void testProductDetailsPage() {
        // Создаем объект главной страницы
        HomePage homePage = new HomePage(driver);

        // Открываем главную страницу
        homePage.open();

        // Запоминаем название первого товара
        String productName = homePage.getProducts().get(0).getText();

        // Кликаем по первому товару
        ProductPage productPage = homePage.clickFirstProduct();

        // Проверяем, что кнопка "Add to Cart" отображается
        Assert.assertTrue(productPage.isAddToCartButtonDisplayed(), "Кнопка Add to Cart не отображается");

        // Проверяем, что цена отображается
        Assert.assertTrue(productPage.isPriceDisplayed(), "Цена не отображается");

        // Выводим название проверенного товара в консоль
        System.out.println("Проверен товар: " + productName);
    }
}