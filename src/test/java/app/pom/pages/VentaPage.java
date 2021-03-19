package app.pom.pages;

import app.pom.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class VentaPage extends BasePage {

    public VentaPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//a [@class='btnComprar'])[1]")
    public WebElement btnComprar;
    @FindBy(xpath = "//a [contains(text(),'Cotizar')]")
    public WebElement btnCotizar;
    @FindBy(xpath = "(//a [@class='calculaDespacho'])[1]")
    public WebElement btnCalcularDespacho;
    @FindBy(xpath = "//div [@class='modalDespacho']")
    public WebElement modalDespacho;
    @FindBy(xpath = "//select [@id='comuna']")
    public WebElement listaDesplegableComuna;
    @FindBy(xpath = "//table [@class='tbl-carro']")
    public WebElement tablaDespacho;

    By opcionesRegion = By.xpath("//select [@name='region'] //option");
    By opcionesComuna = By.xpath("//select [@name='comuna'] //option");



    public void comprarProducto() {
        click(btnComprar);
    }

    public void cotizarProducto() {
        click(btnCotizar);
    }

    public void calcularDespacho(String region) throws InterruptedException {
        click(btnCalcularDespacho);
        esperarVisibilidad(modalDespacho);
        List<WebElement> listaRegiones = encontrarElementos(opcionesRegion);
        int cantidadRegiones = listaRegiones.size();
        for (int i = 0; i < cantidadRegiones; i++) {
            if(listaRegiones.get(i).getText().equals(region)){
                click(listaRegiones.get(i));
            }
        }
        Thread.sleep(2000);
        click(listaDesplegableComuna);
        List<WebElement> listaComunas = encontrarElementos(opcionesComuna);
        click(listaComunas.get(1));
    }

    public boolean confirmacionDespacho(){
        esperarVisibilidad(tablaDespacho);
        return estaDesplegado(tablaDespacho);
    }
}
