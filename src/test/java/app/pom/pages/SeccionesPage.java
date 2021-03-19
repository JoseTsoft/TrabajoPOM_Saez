package app.pom.pages;

import app.pom.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SeccionesPage extends BasePage {

    public SeccionesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    //Locators
    By filtros = By.xpath("//section [@id='filtros'] //ul //a");
    By productos = By.xpath("//section [@id='productos'] //article");

    public void filtrar(String filtro) {
        List<WebElement> listadoFiltros = encontrarElementos(filtros);
        int tamañoLista = listadoFiltros.size();
        for (int i = 0; i < tamañoLista; i++) {
            if (listadoFiltros.get(i).getText().equals(filtro)) {
                click(listadoFiltros.get(i));
                break;
            }
        }
    }

    public void seleccionarProducto() {
        List<WebElement> listadoProductos = encontrarElementos(productos);
        int tamañoLista = listadoProductos.size();
        if (tamañoLista > 1) {
            click(listadoProductos.get(tamañoLista - 1));
        }else{
            click(listadoProductos.get(0));
        }
    }
}
