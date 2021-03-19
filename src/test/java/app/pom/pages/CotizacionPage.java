package app.pom.pages;

import app.pom.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CotizacionPage extends BasePage {

    public CotizacionPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy (xpath = "//h1 [contains(text(),'Cotizaci')]")
    public WebElement msjCotizacionOnline;

    public boolean confirmacionCotizacion(){
        esperarVisibilidad(msjCotizacionOnline);
        return estaDesplegado(msjCotizacionOnline);
    }
}
