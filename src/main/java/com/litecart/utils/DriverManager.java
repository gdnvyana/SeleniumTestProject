package com.litecart.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ResourceBundle;

public class DriverManager {

    private static WebDriver driver;

    //Возвращает экземпляр драйвера. Создает новый, если еще не создан
    public static WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    //Выбор драйвер в зависимости от выбранного браузера
    private static void initDriver() {
        // Загружаем конфигурацию
        ResourceBundle config = ResourceBundle.getBundle("config/config");
        String browser = config.getString("browser");

        // Выбираем браузер
        switch (browser.toLowerCase()) {
            case "chrome":
                // Путь к ChromeDriver
                System.setProperty("webdriver.chrome.driver",
                        config.getString("chrome.driver.path"));
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*"); // Разрешаем удаленные запросы
                driver = new ChromeDriver(options);
                break;

            case "firefox":
                // Путь к GeckoDriver
                System.setProperty("webdriver.gecko.driver",
                        config.getString("firefox.driver.path"));
                driver = new FirefoxDriver();
                break;
        }
        // Разворачиваем окно на весь экран
        driver.manage().window().maximize();
    }

    //Закрывает драйвер и освобождает ресурсы
    public static void quitDriver() {
        if (driver != null) {
            driver.quit(); // Закрываем браузер
            driver = null; // Обнуляем ссылку
        }
    }
}