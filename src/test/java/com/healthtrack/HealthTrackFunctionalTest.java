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
        
        // --- ¡¡¡CORRECCIÓN PARA GITHUB ACTIONS AQUÍ!!! ---
        // Habilitamos el modo "headless" y añadimos argumentos para estabilidad en CI/CD.
        options.addArguments("--headless"); // ¡LA LÍNEA MÁS IMPORTANTE!
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox"); // Requerido para correr como root en contenedores Docker.
        options.addArguments("--disable-dev-shm-usage"); // Previene errores por falta de memoria en /dev/shm.
        options.addArguments("--window-size=1920,1080"); // Especificar un tamaño de ventana es buena práctica.
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // driver.manage().window().maximize(); // No es necesario en modo headless si ya definimos el tamaño.
    }

    @Test
    @DisplayName("Debería registrar un usuario y actualizar su peso correctamente")
    void testUserRegistrationAndUpdateWeight() {
        driver.get(BASE_URL + "/register.html");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys("testuser");
        driver.findElement(By.id("initialWeight")).sendKeys("70.0");
        driver.findElement(By.id("initialWeight")).submit();

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