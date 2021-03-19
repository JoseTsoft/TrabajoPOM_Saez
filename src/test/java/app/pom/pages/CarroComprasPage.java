package app.pom.pages;

import app.pom.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CarroComprasPage extends BasePage {

    public CarroComprasPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }
    @FindBy(xpath = "//h1 [contains(text(),'Carro Compras')]")
    public WebElement msjCarroCompras;
    @FindBy (xpath = "//section [@id='carro'] //a [contains(text(),'X')]")
    public WebElement btnEliminarDelCarrito;
    @FindBy (xpath = "//p [contains(text(),'Aun no agregas productos a tu carro de compras')]")
    public WebElement msjCarroComprasVacio;

    public boolean confirmacionCarrito(){
        esperarVisibilidad(msjCarroCompras);
        return estaDesplegado(msjCarroCompras);
    }
    public void eliminarItemCarrito(){
        click(btnEliminarDelCarrito);
        aceptarVentanaEmergente();
    }

    public boolean confirmacionCarritoVacio(){
        esperarVisibilidad(msjCarroComprasVacio);
        return estaDesplegado(msjCarroComprasVacio);
    }

}
