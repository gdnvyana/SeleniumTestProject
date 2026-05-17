package com.litecart.selenide.utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ResourceBundle;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class DriverManager {

    public static void setup() {
        ResourceBundle config = ResourceBundle.getBundle("config/config");
        String browser = config.getString("browser");

        Configuration.browser = browser;
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
        Configuration.baseUrl = config.getString("base.url");
        Configuration.holdBrowserOpen = false;
        Configuration.reportsFolder = "build/reports/tests";

        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", config.getString("chrome.driver.path"));

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            Configuration.browserCapabilities.setCapability("goog:chromeOptions", options);
        }

        System.out.println("Browser: " + browser);
        System.out.println("Base URL: " + Configuration.baseUrl);
    }

    public static void quitDriver() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            closeWebDriver();
            System.out.println("WebDriver closed");
        }
    }
}