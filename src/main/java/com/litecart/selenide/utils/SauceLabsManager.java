package com.litecart.selenide.utils;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceLabsManager {

    private static final String USERNAME = "oauth-gdnvyanaa-c059d";
    private static final String ACCESS_KEY = "69b07ee3-c1c3-4586-ac7e-35912e846a6c";
    private static final String SAUCE_URL = "https://ondemand.saucelabs.com/wd/hub";

    public static void setup() {
        ChromeOptions options = new ChromeOptions();
        options.setPlatformName("macOS 13");
        options.setBrowserVersion("latest");

        // Настройки для SauceLabs
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", USERNAME);
        sauceOptions.setCapability("accessKey", ACCESS_KEY);
        sauceOptions.setCapability("name", "Litecart Tests");
        sauceOptions.setCapability("build", "Selenide-Tests");

        options.setCapability("sauce:options", sauceOptions);

        try {
            Configuration.remote = String.valueOf(new URL(SAUCE_URL));
            Configuration.browserCapabilities = options;
            Configuration.browser = "chrome";
            Configuration.browserSize = "1920x1080";
            Configuration.timeout = 30000;
            Configuration.baseUrl = "http://litecart.stqa.ru/en/";

            System.out.println("Running tests on SauceLabs!");
            System.out.println("Watch test execution: https://app.saucelabs.com/dashboard/tests");

        } catch (MalformedURLException e) {
            System.err.println("Error connecting to SauceLabs: " + e.getMessage());
        }
    }
}