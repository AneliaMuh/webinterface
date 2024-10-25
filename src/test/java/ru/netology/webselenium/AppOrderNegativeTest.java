package ru.netology.webselenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppOrderNegativeTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;
    }

    //невалидное имя
    @Test
    public void shouldSNegativeTestNotValidName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Anelia Mukhambetova");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79271234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual.trim());
    }

    //невалидный номер телефона
    @Test
    public void shouldSNegativeTestNotValidPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Анелия Мухамбетова");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("88888888888");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual.trim());
    }

    //пустое поле имени
    @Test
    public void shouldSNegativeTestEmptyName() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("88888888888");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual.trim());
    }

    //пустое поле телефон
    @Test
    public void shouldSNegativeTestEmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Анелия Мухамбетова");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    // не отмечен чекбокс
    @Test
    public void shouldNegativeTestUnchecked() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Мухамбетова Анелия");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79271234567");
        driver.findElement(By.className("button")).click();

        assertTrue(driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).isDisplayed());
    }
}
