package app.pom.pages;

import app.pom.base.BasePage;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    //Objetos elements
    @FindBy (xpath = "//a [@class='getAccess']")
    public WebElement iniciaSesion;
    @FindBy (xpath = "//div [@id='modal']")
    public WebElement ventanaIngresoSesion;
    @FindBy (xpath = "//input [@id='user']")
    public WebElement textFieldUsuario;
    @FindBy (xpath = "//input [@id='pass']")
    public WebElement textFieldContraseña;
    @FindBy (xpath = "//button[@class='btnLogin']")
    public WebElement btnLogin;
    @FindBy (xpath = "//div [contains(text(),'Datos incorrectos')]")
    public WebElement msjIngresoInvalido;
    @FindBy (xpath = "//div [contains(text(),'Usuario requerido')]")
    public WebElement msjIngresoCamposVacios;

    @FindBy (xpath = "//input [@id='sq']")
    public WebElement buscadorProductos;
    @FindBy (xpath = "//button [contains(text(),'Buscar')]")
    public WebElement btnBuscar;

    @FindBy (xpath = "(//a [contains(text(),'Portátiles')])[1]")
    public WebElement categoriaPortatiles;

    //Locators
    By productosRecomendados = By.xpath("//section [@class='recomendados'] //article");

    //Metodos
    public void login(String usuario, String pass){
        click(iniciaSesion);
        esperarVisibilidad(ventanaIngresoSesion);
        enviarDatos(textFieldUsuario,usuario);
        enviarDatos(textFieldContraseña,pass);
        click(btnLogin);
    }
    public boolean confirmarIngresoInvalido(){
        esperarVisibilidad(msjIngresoInvalido);
        return estaDesplegado(msjIngresoInvalido);
    }

    public boolean confirmarIngresoCamposVacios(){
        esperarVisibilidad(msjIngresoCamposVacios);
        return estaDesplegado(msjIngresoCamposVacios);
    }

    public void buscarProducto(String producto){
        enviarDatos(buscadorProductos,producto);
        click(btnBuscar);
    }

    public void ingresarSeccionPortatiles(){
        click(categoriaPortatiles);
    }

    public void seleccionarProductoRecomendado(){
        List<WebElement> listaProductosRecomendados = encontrarElementos(productosRecomendados);
        click(listaProductosRecomendados.get(0));
    }




}
