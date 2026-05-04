import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AcmeStoreTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    // Выполняем перед каждым тестом - открывает браузер
    @BeforeEach
    public void setUp() {
        // Указываем путь к ChromeDriver
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        // Настройки Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        // Запускаем браузер
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Явное ожидание
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Actions для сложных действий (наведение, перетаскивание)
        actions = new Actions(driver);
    }

    // Тест 1: Проверяем переход по ссылкам через заголовок страницы
    @Test
    public void testHeaderMenuLinksByTitle() {
        // Открываем магазин
        driver.get("http://litecart.stqa.ru/en/");

        // Находим все ссылки в меню шапки
        List<WebElement> menuLinks = driver.findElements(By.cssSelector("#site-menu li a"));
        assertTrue(menuLinks.size() > 0, "Меню в шапке не найдено");

        // Проходим по каждой ссылке
        for (int i = 0; i < menuLinks.size(); i++) {

            // Обновляем список ссылок (важно после возврата на главную)
            List<WebElement> currentLinks = driver.findElements(By.cssSelector("#site-menu li a"));
            WebElement link = currentLinks.get(i);

            String linkText = link.getText().trim();

            // Пропускаем пустые или скрытые элементы
            if (linkText.isEmpty() || !link.isDisplayed()) {
                continue;
            }

            System.out.println("=== Проверяем ссылку: " + linkText + " ===");

            // Ждем, пока ссылка станет кликабельной
            wait.until(ExpectedConditions.elementToBeClickable(link));

            // Actions - наводим курсор на ссылку (обязательное требование)
            actions.moveToElement(link).perform();
            // Кликаем по ссылке
            link.click();

            // Ждем загрузки страницы (по наличию заголовка h1)
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1")));

            // Получаем заголовок страницы
            String actualTitle = driver.getTitle();
            System.out.println("Title: " + actualTitle);

            // Проверки
            assertNotNull(actualTitle);
            assertFalse(actualTitle.isEmpty(), "Заголовок пустой");
            assertTrue(actualTitle.contains(linkText),
                    "Title не содержит текст ссылки: " + linkText);

            // Проверяем, что заголовок h1 отображается
            assertTrue(driver.findElement(By.cssSelector("h1")).isDisplayed());

            // Возвращаемся на главную страницу
            driver.navigate().back();

            // Ждем, пока меню снова станет видимым
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#site-menu")));
        }
    }

    // Тест 2: Проверка перехода по ссылкам через наличие элемента
    @Test
    public void testHeaderMenuLinksByElementPresence() {
        // Открываем магазин
        driver.get("http://litecart.stqa.ru/en/");

        // Находим все ссылки в меню шапки
        List<WebElement> menuLinks = driver.findElements(By.cssSelector("#site-menu li a"));
        assertTrue(menuLinks.size() > 0, "Меню в шапке не найдено");

        // Проходим по каждой ссылке
        for (int i = 0; i < menuLinks.size(); i++) {

            // Обновляем список ссылок
            List<WebElement> currentLinks = driver.findElements(By.cssSelector("#site-menu li a"));
            WebElement link = currentLinks.get(i);

            String linkText = link.getText().trim();

            // Пропускаем пустые или скрытые элементы
            if (linkText.isEmpty() || !link.isDisplayed()) {
                continue;
            }

            System.out.println("=== Проверяем ссылку: " + linkText + " ===");

            // Ждем кликабельности
            wait.until(ExpectedConditions.elementToBeClickable(link));

            // Actions - наводим курсор
            actions.moveToElement(link).perform();
            // Кликаем
            link.click();

            // Ждем появления заголовка h1 на новой странице
            WebElement header = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1"))
            );

            // Проверяем, что заголовок отображается
            assertTrue(header.isDisplayed(),
                    "Заголовок не отображается: " + linkText);

            System.out.println("Найден заголовок: " + header.getText());

            // Возвращаемся на главную
            driver.navigate().back();

            // Ждем возврата меню
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#site-menu")));
        }
    }

    // Выполняется после каждого теста - закрывает браузер
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}