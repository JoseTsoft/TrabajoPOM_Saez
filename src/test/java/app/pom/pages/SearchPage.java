package app.pom.pages;

import app.pom.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    By marcaProductosBuscados = By.xpath("//div [@class='detalle'] //p //a");

    public void seleccionarProducto(String marca){
        List<WebElement> listadoProductos = encontrarElementos(marcaProductosBuscados);
        int tamañoLista = listadoProductos.size();
        for (int i = 0; i < tamañoLista; i++) {
            if(listadoProductos.get(i).getText().equals(marca)){
                click(listadoProductos.get(i));
                break;
            }
        }

    }
}
