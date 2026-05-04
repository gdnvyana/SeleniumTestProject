import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HerokuappTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Указываем путь к ChromeDriver
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        // Открываем браузер
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    public void click10thLink() throws InterruptedException {
        // Переходим на сайт
        driver.get("https://the-internet.herokuapp.com");

        // Находим все ссылки на странице
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        System.out.println("Всего ссылок: " + allLinks.size());

        // Кликаем на 10-ю ссылку (индекс 9, так как начинается с 0)
        if (allLinks.size() >= 10) {
            System.out.println("Кликаю на: " + allLinks.get(9).getText());
            allLinks.get(9).click();
            Thread.sleep(3000); // Пауза, чтобы увидеть результат
        }
    }

    @AfterEach
    public void tearDown() {
        // Закрываем браузер
        if (driver != null) driver.quit();
    }
}