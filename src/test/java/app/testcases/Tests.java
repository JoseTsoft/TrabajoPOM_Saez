package app.testcases;

import app.pom.base.PropertiesApp;
import app.pom.pages.*;
import app.recursos.DataDriven;
import app.recursos.ReporteApp;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import test.v6.C;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Tests {

    //Varios
    private WebDriver driver;
    public ReporteApp reporte;
    private DataDriven data;
    private ArrayList<String> datosCP;

    //Pages
    private HomePage home;
    private MiCuentaPage miCuenta;
    private SearchPage search;
    private VentaPage ventas;
    private CarroComprasPage carritoCompras;
    private SeccionesPage secciones;
    private CotizacionPage cotizacion;

    //Recursos de app.properties
    private String user;
    private String pass;
    private String homeURL;
    private Properties nombreArchivoExcel;
    private String excelURL;
    private String nombreHoja;
    private String nombreTestCase;


    @BeforeSuite
    public void setupClass() {
        reporte = new ReporteApp();
        reporte.setReport();

    }

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        //Inicializacion pages
        home = new HomePage(driver);
        miCuenta = new MiCuentaPage(driver);
        search = new SearchPage(driver);
        ventas = new VentaPage(driver);
        carritoCompras = new CarroComprasPage(driver);
        secciones = new SeccionesPage(driver);
        cotizacion = new CotizacionPage(driver);

        //get Recursos
        user = PropertiesApp.getAppUser();
        pass = PropertiesApp.getAppPass();
        homeURL = PropertiesApp.getAppUrl();

    }

    @BeforeTest
    public void preparacionTest() throws Exception {
        //Conexión con excel e inicialización del almacenador
        data = new DataDriven();
        nombreArchivoExcel = PropertiesApp.loadPropertiesApp(PropertiesApp.getFileName());
        excelURL = PropertiesApp.getAppUrlExcel();
        nombreHoja = PropertiesApp.getAppSheetnameExcel();
        nombreTestCase = PropertiesApp.getAppTitulotcExcel();
        data.preparacionExcel(excelURL, nombreHoja, nombreTestCase);

        datosCP = new ArrayList<String>();

    }


    @Test(priority = 1)
    public void atc01_InicioSesionExitoso() throws Exception {
        reporte.createReport("atc01_InicioSesionExitoso");
        reporte.logTestINFO("abrir: " + homeURL);
        home.IrUrl(homeURL);
        reporte.addScreenshot(reporte.takeScreenshot(driver, "Selenium 2"), "Carga HomePage");
        home.login(user, pass);
        reporte.addScreenshot(reporte.takeScreenshot(driver, "Selenium 2"), "Inicio Sesion");
        Assert.assertTrue(miCuenta.confirmarLogin());
    }

    @Test(priority = 2)
    public void atc02_InicioSesionCuentaInexistente() throws Exception {
        reporte.createReport("atc02_InicioSesionCuentaInexistente");
        datosCP = data.getData("atc02_InicioSesionCuentaInexistente");
        reporte.logTestINFO("abrir: " + homeURL);
        home.IrUrl(homeURL);
        reporte.addScreenshot(reporte.takeScreenshot(driver, "Selenium 2"), "Carga HomePage");
        home.login(datosCP.get(1), datosCP.get(2));
        reporte.addScreenshot(reporte.takeScreenshot(driver, "Selenium 2"), "Ingreso datos inexistentes");
        Assert.assertTrue(home.confirmarIngresoInvalido());
    }

    @Test(priority = 3)
    public void atc03_InicioSesionCamposVacios() throws Exception {
        reporte.createReport("atc03_InicioSesionCamposVacios");
        reporte.logTestINFO("abrir: " + homeURL);
        home.IrUrl(homeURL);
        reporte.addScreenshot(reporte.takeScreenshot(driver, "Selenium 2"), "Carga HomePage");
        home.login("", "");
        reporte.addScreenshot(reporte.takeScreenshot(driver, "Selenium 2"), "Ingreso datos vacios");
        Assert.assertTrue(home.confirmarIngresoCamposVacios());
    }

    @Test(priority = 4)
    public void atc04_AgregarProductoACarritoPorBuscador() throws Exception {
        reporte.createReport("atc04_AgregarProductoACarritoPorBuscador");
        datosCP = data.getData("atc04_AgregarProductoACarritoPorBuscador");
        reporte.logTestINFO("abrir: " + homeURL);
        home.IrUrl(homeURL);
        home.buscarProducto(datosCP.get(1));
        search.seleccionarProducto(datosCP.get(2));
        ventas.comprarProducto();
        reporte.addScreenshot(reporte.takeScreenshot(driver, "Selenium 2"), "Carrito de compras");
        Assert.assertTrue(carritoCompras.confirmacionCarrito());
    }

    @Test(priority = 5)
    public void atc05_AgregarProductoACarritoPorFiltros() throws Exception {
        reporte.createReport("atc05_AgregarProductoACarritoPorFiltros");
        datosCP = data.getData("atc05_AgregarProductoACarritoPorFiltros");
        reporte.logTestINFO("abrir: " + homeURL);
        home.IrUrl(homeURL);
        home.ingresarSeccionPortatiles();
        secciones.filtrar(datosCP.get(1));
        secciones.seleccionarProducto();
        ventas.comprarProducto();
        reporte.addScreenshot(reporte.takeScreenshot(driver, "Selenium 2"), "Carrito de compras");
        Assert.assertTrue(carritoCompras.confirmacionCarrito());
    }

    @Test(priority = 6)
    public void atc06_AgregarProductoACotizacion() throws Exception {
        reporte.createReport("atc06_AgregarProductoACotizacion");
        reporte.logTestINFO("abrir: " + homeURL);
        home.IrUrl(homeURL);
        home.seleccionarProductoRecomendado();
        ventas.cotizarProducto();
        Assert.assertTrue(cotizacion.confirmacionCotizacion());
    }

    @Test(priority = 7)
    public void atc07_AgregarProductoACarritoYEliminar() throws Exception {
        reporte.createReport("atc07_AgregarProductoACarritoYEliminar");
        reporte.logTestINFO("abrir: " + homeURL);
        home.IrUrl(homeURL);
        home.seleccionarProductoRecomendado();
        ventas.comprarProducto();
        carritoCompras.eliminarItemCarrito();
        Assert.assertTrue(carritoCompras.confirmacionCarritoVacio());
    }

    @Test(priority = 8)
    public void atc08_ConsultaTiempoDespacho() throws Exception {
        reporte.createReport("atc08_ConsultaTiempoDespacho");
        datosCP = data.getData("atc08_ConsultaTiempoDespacho");
        reporte.logTestINFO("abrir: " + homeURL);
        home.IrUrl(homeURL);
        home.buscarProducto(datosCP.get(1));
        search.seleccionarProducto(datosCP.get(2));
        ventas.calcularDespacho(datosCP.get(3));
        Assert.assertTrue(ventas.confirmacionDespacho());
    }


    @AfterTest
    public void Teardown() throws IOException {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterMethod
    public void recoleccionReporte(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            reporte.reportLogFail(result, driver);

        } else if (result.getStatus() == ITestResult.SKIP) {
            reporte.reportLogSKIP(result);

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            reporte.reportLogSuccess(result);
        }
        reporte.closeReport();
        if (driver != null) {
            driver.quit();
        }
    }
}
