package com.litecart.selenide.tests;

import com.codeborne.selenide.Selenide;
import com.litecart.selenide.pages.HomePage;
import com.litecart.selenide.utils.SauceLabsManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;

@Epic("SauceLabs Integration")
@Feature("Cloud Testing")
public class SauceLabsTest {

    @BeforeMethod
    public void setUp() {
        SauceLabsManager.setup();
    }

    @Test
    @Story("SauceLabs Execution")
    @Description("Проверка работы тестов на SauceLabs")
    @Severity(SeverityLevel.CRITICAL)
    public void testOnSauceLabs() {
        open("http://litecart.stqa.ru/en/");
        Selenide.sleep(2000);

        System.out.println("Page title: " + Selenide.title());
        System.out.println("Running on SauceLabs cloud!");

        Assert.assertTrue(Selenide.title().contains("My Store"),
                "Страница должна содержать 'My Store'");
    }
}
