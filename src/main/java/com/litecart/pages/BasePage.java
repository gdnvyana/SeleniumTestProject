package com.litecart.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    // Веб-драйвер для управления браузером
    protected WebDriver driver;

    // Объект для ожидания элементов
    protected WebDriverWait wait;

    // Исполнитель JavaScript кода в браузере
    protected JavascriptExecutor jsExecutor;


    public BasePage(WebDriver driver) {
        this.driver = driver; // Сохраняем драйвер
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Ждем до 10 сек
        this.jsExecutor = (JavascriptExecutor) driver; // Преобразуем драйвер в JavascriptExecutor
    }

    //Ждем, когда элемент станет видимым на странице
    protected void waitForElementVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    //Ждем, когда элемент станет кликабельным
    protected void waitForElementClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    //Кликает по элементу с ожиданием его кликабельности
    protected void click(By locator) {
        waitForElementClickable(locator); //  ждем
        driver.findElement(locator).click(); //  кликаем
    }

    //Вводит текст в поле ввода
    protected void sendKeys(By locator, String text) {
        waitForElementVisible(locator); // Ждем видимости поля
        driver.findElement(locator).clear(); // Очищаем поле
        driver.findElement(locator).sendKeys(text); // Вводим текст
    }

    //Получает текст элемента
    protected String getText(By locator) {
        waitForElementVisible(locator); // Ждем видимости
        return driver.findElement(locator).getText(); // Возвращаем текст
    }

    //Проверяет, отображается ли элемент на странице
    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed(); // Проверяем видимость
        } catch (NoSuchElementException e) {
            return false; // Если элемента нет - false
        }
    }

    //Прокручивает страницу до элемента (используя JavaScript)
    protected void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator); // Находим элемент
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element); // Скроллим к элементу
    }
}