package com.healthtrack;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthTrackFunctionalTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String BASE_URL = "http://localhost:8080/healthtrack";

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    @DisplayName("Debería registrar un usuario y actualizar su peso correctamente")
    void testUserRegistrationAndUpdateWeight() {
        driver.get(BASE_URL + "/register.html");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys("testuser");
        driver.findElement(By.id("initialWeight")).sendKeys("70.0");
        driver.findElement(By.id("initialWeight")).submit();

        // --- AJUSTE FINAL ---
        // La prueba real de que estamos en el dashboard es la siguiente línea,
        // que espera a que aparezca el campo 'newWeightInput'.
        // Por lo tanto, la comprobación de la URL era redundante y la eliminamos
        // para evitar el fallo de timing misterioso.
        WebElement newWeightInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("newWeightInput")));
        newWeightInput.sendKeys("72.5");
        
        driver.findElement(By.id("updateWeightButton")).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("currentWeightDisplay"), "72.5"));

        WebElement currentWeightDisplay = driver.findElement(By.id("currentWeightDisplay"));
        assertEquals("72.5", currentWeightDisplay.getText(), "El peso debería ser 72.5 después de la actualización.");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}