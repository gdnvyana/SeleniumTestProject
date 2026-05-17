package com.litecart.selenide.tests;

import com.litecart.selenide.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

public class LitecartSelenideTest extends BaseTest {

    private String getValidEmail() {
        return ResourceBundle.getBundle("config/config").getString("valid.email");
    }

    private String getValidPassword() {
        return ResourceBundle.getBundle("config/config").getString("valid.password");
    }

    @Test(priority = 1)
    public void testLoginWithValidCredentials() {
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage.login(getValidEmail(), getValidPassword());

        Assert.assertTrue(loginPage.isLogoutLinkDisplayed(), "Кнопка Logout не отображается");
    }

    @Test(priority = 2)
    public void testAddOneProductToCart() {
        HomePage homePage = new HomePage();
        homePage.open();

        ProductPage productPage = homePage.clickFirstProduct();
        productPage.addToCart();

        homePage = productPage.navigateBack();

        Assert.assertTrue(homePage.isCartQuantityUpdated("1"), "В корзине не 1 товар");
    }

    @Test(priority = 3)
    public void testAddMultipleProductsToCart() {
        HomePage homePage = new HomePage();
        homePage.open();

        for (int i = 0; i < 2; i++) {
            ProductPage productPage = homePage.clickProductByIndex(i);
            productPage.addToCart();

            homePage = productPage.navigateBack();

            Assert.assertTrue(homePage.isCartQuantityUpdated(String.valueOf(i + 1)),
                    "Счетчик корзины не обновился для " + (i + 1) + " товара");
        }

        Assert.assertEquals(homePage.getCartQuantity(), "2", "В корзине не 2 товара");
    }

    @Test(priority = 4)
    public void testOpenCategoryRubberDucks() {
        HomePage homePage = new HomePage();
        homePage.open();

        CategoryPage categoryPage = homePage.clickCategory("Rubber Ducks");

        Assert.assertTrue(categoryPage.isTitleContains("Duck"), "Заголовок не содержит 'Duck'");
        Assert.assertTrue(categoryPage.hasProducts(), "На странице нет товаров");
    }

    @Test(priority = 5)
    public void testProductDetailsPage() {
        HomePage homePage = new HomePage();
        homePage.open();

        String productName = homePage.getProducts().first().getText();
        ProductPage productPage = homePage.clickFirstProduct();

        Assert.assertTrue(productPage.isAddToCartButtonDisplayed(), "Кнопка Add to Cart не отображается");
        Assert.assertTrue(productPage.isPriceDisplayed(), "Цена не отображается");

        System.out.println("Проверен товар: " + productName);
    }
}