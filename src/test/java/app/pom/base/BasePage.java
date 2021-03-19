package app.pom.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage {

    private WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    //Wrappers
    public void IrUrl(String url) {
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public void click(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al clickear elemento");
        }
    }

    public void esperarVisibilidad(WebElement element) {
        WebDriverWait w = new WebDriverWait(driver, 15);
        w.until(ExpectedConditions.visibilityOf(element));
    }
    public void esperarVisibilidadLocator(By locator){
        WebDriverWait w = new WebDriverWait(driver,15);
        w.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
    }


    public void enviarDatos(WebElement element, String texto) {
        element.sendKeys(texto);
    }

    public boolean estaDesplegado(WebElement element){
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<WebElement> encontrarElementos(By locator){
        return  driver.findElements(locator);
    }

    public void aceptarVentanaEmergente(){
        driver.switchTo().alert().accept();
        driver.switchTo().defaultContent();
    }


}
