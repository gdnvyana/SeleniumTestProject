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

        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");

        Configuration.browser = browser;
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
        Configuration.baseUrl = config.getString("base.url");
        Configuration.holdBrowserOpen = false;

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--no-sandbox");
            Configuration.browserCapabilities.setCapability("goog:chromeOptions", options);
        }
    }

    public static void quitDriver() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            closeWebDriver();
        }
    }
}
