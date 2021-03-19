package app.pom.pages;

import app.pom.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MiCuentaPage extends BasePage {
    public MiCuentaPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    //Objetos
    @FindBy (xpath = "//h1 [contains(text(),'Mi Cuenta')]")
    public WebElement cartelMiCuenta;

    public boolean confirmarLogin(){
        esperarVisibilidad(cartelMiCuenta);
        return estaDesplegado(cartelMiCuenta);
    }
}
