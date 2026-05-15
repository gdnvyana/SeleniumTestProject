import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Указываем порядок выполнения тестов
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LitecartTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String BASE_URL = "http://litecart.stqa.ru/en/";
    private final String VALID_EMAIL = "lili.black@test.com";
    private final String VALID_PASSWORD = "LiliBlack123";

    // Выполняется перед каждым тестом - открывает браузер
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

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ====================== ЛОГИН ======================

    // Тест 1: Успешный вход с валидными учетными данными
    @Test
    @Order(1)
    public void testLoginWithValidCredentials() {
        // Открываем страницу логина
        driver.get(BASE_URL + "login");

        // Вводим email и пароль
        driver.findElement(By.name("email")).sendKeys(VALID_EMAIL);
        driver.findElement(By.name("password")).sendKeys(VALID_PASSWORD);
        // Нажимаем кнопку входа
        driver.findElement(By.name("login")).click();

        // Ждем появления ссылки "Logout" (признак успешного входа)
        By logout = By.cssSelector("a[href*='logout']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(logout));

        // Проверяем, что кнопка выхода отображается
        assertTrue(driver.findElement(logout).isDisplayed());
    }

    // ====================== КОРЗИНА ======================

    // Тест 2: Добавление одного товара в корзину
    @Test
    @Order(2)
    public void testAddOneProductToCart() {
        // Открываем главную страницу
        driver.get(BASE_URL);

        // Ждем появления товаров на странице
        List<WebElement> products = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".product"))
        );
        // Кликаем на первый товар
        products.get(0).click();

        driver.findElement(By.name("add_cart_product")).click();

        // Ждем, пока счетчик корзины покажет "1"
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector(".quantity"), "1"
        ));

        // Проверяем, что в корзине 1 товар
        assertEquals("1", driver.findElement(By.cssSelector(".quantity")).getText());
    }

    // Тест 3: Добавление нескольких товаров в корзину
    @Test
    @Order(3)
    public void testAddMultipleProductsToCart() {

        driver.get(BASE_URL);

        // Добавляем 2 товара
        for (int i = 0; i < 2; i++) {
            // Ждем появления товаров
            List<WebElement> products = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".product"))
            );
            products.get(i).click();

            // Добавляем в корзину
            driver.findElement(By.name("add_cart_product")).click();

            // Ждем обновления счетчика
            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    By.cssSelector(".quantity"), String.valueOf(i + 1)
            ));

            // Возвращаемся на главную страницу
            driver.navigate().back();
        }

        // Проверяем, что в корзине 2 товара
        assertEquals("2", driver.findElement(By.cssSelector(".quantity")).getText());
    }

    // ====================== КАТАЛОГ ======================

    // Тест 4: Открытие категории Rubber Ducks
    @Test
    @Order(4)
    public void testOpenCategoryRubberDucks() {
        driver.get(BASE_URL);

        // Находим в меню ссылку "Rubber Ducks"
        WebElement ducks = driver.findElements(By.cssSelector("#site-menu li a"))
                .stream()
                .filter(e -> e.getText().contains("Rubber Ducks"))
                .findFirst()
                .orElseThrow();

        // Кликаем по категории
        ducks.click();

        // Ждем появления заголовка h1 на странице категории
        WebElement title = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1"))
        );

        // Проверяем, что заголовок содержит слово "Duck"
        assertTrue(title.getText().contains("Duck"));
        // Проверяем, что на странице есть товары
        assertFalse(driver.findElements(By.cssSelector(".product")).isEmpty());
    }

    // Тест 5: Проверка страницы товара
    @Test
    @Order(5)
    public void testProductDetailsPage() {
        driver.get(BASE_URL);

        // Ждем появления товаров
        List<WebElement> products = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".product"))
        );

        // Запоминаем название первого товара
        String productName = products.get(0).getText();
        // Кликаем на первый товар
        products.get(0).click();

        // Проверяем, что кнопка "Add to Cart" отображается
        assertTrue(driver.findElement(By.name("add_cart_product")).isDisplayed());
        // Проверяем, что цена отображается
        assertFalse(driver.findElements(By.cssSelector(".price")).isEmpty());

        // Выводим название проверенного товара в консоль
        System.out.println("Проверен товар: " + productName);
    }

    // Выполняется после каждого теста - закрывает браузер
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Закрываем браузер
        }
    }
}