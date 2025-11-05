package com.automatizacion.Controller;

import com.automatizacion.Model.ExcelManager;
import com.automatizacion.View.ConsolaView;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import org.openqa.selenium.support.ui.WebDriverWait;


public class BusquedaContactos {
    private ExcelManager excelManager;
    private ConsolaView vista;
    private String rutaExcel;

    public BusquedaContactos(ExcelManager excelManager, ConsolaView vista, String rutaExcel) {
        this.excelManager = excelManager;
        this.vista = vista;
        this.rutaExcel = rutaExcel;
    }

    public void ejecutar() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        try {
            List<String> contactos = excelManager.leerContactos(rutaExcel);

            for (int i = 0; i < contactos.size(); i++) {
                String numero = contactos.get(i);
                vista.mostrarMensaje("🔍 Buscando número: " + numero);

                driver.get("https://www.doctorsim.com/ec-es/recargar-celular/");
                WebElement input = driver.findElement(By.id("phone"));
                input.clear();
                input.sendKeys(numero);
                driver.findElement(By.id("form-submit")).click();
                Thread.sleep(4000);

                // Leer operador
                String operadorResult;
                try {

                    WebElement operadorElemento =  driver.findElement(By.xpath("//*[@id=\"showSelec\"]/div[2]/div/div/div/div/div/p"));
                    operadorResult = operadorElemento.getText().trim().toUpperCase();
                    System.out.println("Operador: " + operadorResult);
                } catch (NoSuchElementException e) {
                    operadorResult = "NO DETECTADO";
                    System.out.println("⚠ No se encontró operador.");
                }

                excelManager.escribirOperador(rutaExcel, i + 1, operadorResult);
                vista.mostrarMensaje("✔ " + numero + " → " + operadorResult + "\n");
            }

            vista.mostrarMensaje("✅ Proceso completado correctamente.");
            driver.quit();

        } catch (Exception e) {
            vista.mostrarError("Error durante la ejecución: " + e.getMessage());
        }
    }
}
