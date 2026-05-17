package com.litecart.selenide.tests;

import com.litecart.selenide.utils.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    @BeforeMethod
    public void setUp() {
        DriverManager.setup();
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
