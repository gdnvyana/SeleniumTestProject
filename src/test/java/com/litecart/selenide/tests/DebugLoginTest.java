package com.litecart.selenide.tests;

import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DebugLoginTest extends BaseTest {

    @Test
    public void testLoginManually() {
        // Открываем страницу логина
        open("http://litecart.stqa.ru/en/login");
        Selenide.sleep(3000);
        
        // Выводим информацию для отладки
        System.out.println("=== DEBUG INFO ===");
        System.out.println("Page title: " + Selenide.title());
        System.out.println("Current URL: " + Selenide.webdriver().driver().url());
        
        // Пытаемся найти поле email
        boolean emailByName = $("[name='email']").isDisplayed();
        System.out.println("Email field by name 'email': " + emailByName);
        
        boolean emailByCss = $("input[name='email']").isDisplayed();
        System.out.println("Email field by css 'input[name=email]': " + emailByCss);
        
        // Если поле найдено, вводим данные
        if (emailByName) {
            System.out.println("=== Entering credentials ===");
            $("[name='email']").setValue("lili.black@test.com");
            $("[name='password']").setValue("LiliBlack123");
            $("[name='login']").click();
            
            Selenide.sleep(3000);
            System.out.println("After login URL: " + Selenide.webdriver().driver().url());
            
            // Проверяем наличие кнопки Logout
            boolean logoutExists = $("a[href*='logout']").isDisplayed();
            System.out.println("Logout button exists: " + logoutExists);
            
            // Проверяем, есть ли сообщение об ошибке
            boolean errorMessage = $(".alert-danger").isDisplayed();
            if (errorMessage) {
                String errorText = $(".alert-danger").getText();
                System.out.println("Error message: " + errorText);
            }
        } else {
            System.out.println("=== Email field NOT found! ===");
            // Выводим все input поля на странице для анализа
            System.out.println("All input fields on page:");
            Selenide.$$("input").forEach(input -> {
                System.out.println("  - name: " + input.getAttribute("name") + 
                                 ", id: " + input.getAttribute("id") +
                                 ", type: " + input.getAttribute("type"));
            });
        }
        
        System.out.println("=== DEBUG FINISHED ===");
    }
}
