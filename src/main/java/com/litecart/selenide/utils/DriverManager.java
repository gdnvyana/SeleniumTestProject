package com.litecart.selenide.utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ResourceBundle;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class DriverManager {

    private static boolean useGrid = true;

    public static void setup() {
        ResourceBundle config = ResourceBundle.getBundle("config/config");
        String browser = config.getString("browser");

        if (useGrid) {
            setupGrid(browser);
        } else {
            setupLocal(browser);
        }

        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
        Configuration.baseUrl = config.getString("base.url");
        Configuration.holdBrowserOpen = false;

        System.out.println("Browser: " + browser);
        System.out.println("Base URL: " + Configuration.baseUrl);
        if (useGrid) {
            System.out.println("Running on Selenium Grid: http://localhost:4444");
        }
    }

    private static void setupLocal(String browser) {
        Configuration.browser = browser;
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--no-sandbox");
            Configuration.browserCapabilities.setCapability("goog:chromeOptions", options);
        }
    }

    private static void setupGrid(String browser) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");

        Configuration.browser = browser;
        Configuration.remote = "http://localhost:4444/wd/hub";
        Configuration.browserCapabilities.setCapability("goog:chromeOptions", options);

        System.out.println("Connected to Selenium Grid!");
    }

    public static void quitDriver() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            closeWebDriver();
            System.out.println("WebDriver closed");
        }
    }
}
