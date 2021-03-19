package app.testcases;

import app.pom.base.PropertiesApp;
import app.pom.pages.*;
import app.recursos.DataDriven;
import app.recursos.ReporteApp;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.remoting.support.DefaultRemoteInvocationExecutor;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.ArrayList;

public class Tests {

    private WebDriver driver;
    public ReporteApp reporte = new ReporteApp();
    private DataDriven data;
    private ArrayList<String> datosCP;

    @BeforeSuite
    public void setupClass() {
        reporte.setReport();
    }

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeTest
    public void preparacionTest() throws Exception {
        data = new DataDriven();
        PropertiesApp.loadPropertiesApp(PropertiesApp.getFileName());
        data.preparacionExcel(PropertiesApp.getAppUrlExcel(), PropertiesApp.getAppSheetnameExcel(), PropertiesApp.getAppTitulotcExcel());
        datosCP = new ArrayList<String>();
    }


    @Test
    public void atc01_InicioSesionExitoso() throws Exception {
        reporte.createReport("atc01_InicioSesionExitoso");
        String user = PropertiesApp.getAppUser();
        String pass = PropertiesApp.getAppPass();
        String homeURL = PropertiesApp.getAppUrl();
        HomePage hp = new HomePage(driver);
        reporte.logTestINFO("abrir: " + homeURL);
        hp.IrUrl(homeURL);
        reporte.addScreenshot(reporte.takeScreenshot(driver, "Selenium 2"), "Carga HomePage");
        hp.login(user, pass);
        reporte.addScreenshot(reporte.takeScreenshot(driver, "Selenium 2"), "Inicio Sesion");
        MiCuentaPage mcp = new MiCuentaPage(driver);
        Assert.assertTrue(mcp.confirmarLogin());
    }

    @Test
    public void atc02_InicioSesionCuentaInexistente() throws Exception {
        reporte.createReport("atc02_InicioSesionCuentaInexistente");
        datosCP = data.getData("atc02_InicioSesionCuentaInexistente");
        PropertiesApp.loadPropertiesApp(PropertiesApp.getFileName());
        String homeURL = PropertiesApp.getAppUrl();
        HomePage hp = new HomePage(driver);
        reporte.logTestINFO("abrir: " + homeURL);
        hp.IrUrl(homeURL);
        reporte.addScreenshot(reporte.takeScreenshot(driver, "Selenium 2"), "Carga HomePage");
        hp.login(datosCP.get(1), datosCP.get(2));
        reporte.addScreenshot(reporte.takeScreenshot(driver, "Selenium 2"), "Ingreso datos inexistentes");
        Assert.assertTrue(hp.confirmarIngresoInvalido());
    }

    @Test
    public void atc03_InicioSesionCamposVacios() throws Exception {
        reporte.createReport("atc03_InicioSesionCamposVacios");
        PropertiesApp.loadPropertiesApp(PropertiesApp.getFileName());
        String homeURL = PropertiesApp.getAppUrl();
        HomePage hp = new HomePage(driver);
        reporte.logTestINFO("abrir: " + homeURL);
        hp.IrUrl(homeURL);
        reporte.addScreenshot(reporte.takeScreenshot(driver, "Selenium 2"), "Carga HomePage");
        hp.login("", "");
        reporte.addScreenshot(reporte.takeScreenshot(driver, "Selenium 2"), "Ingreso datos vacios");
        Assert.assertTrue(hp.confirmarIngresoCamposVacios());
    }

    @Test
    public void atc04_AgregarProductoACarritoPorBuscador() throws Exception {
        reporte.createReport("atc04_AgregarProductoACarritoPorBuscador");
        datosCP = data.getData("atc04_AgregarProductoACarritoPorBuscador");
        PropertiesApp.loadPropertiesApp(PropertiesApp.getFileName());
        String homeURL = PropertiesApp.getAppUrl();
        HomePage hp = new HomePage(driver);
        reporte.logTestINFO("abrir: " + homeURL);
        hp.IrUrl(homeURL);
        hp.buscarProducto(datosCP.get(1));
        SearchPage sp = new SearchPage(driver);
        sp.seleccionarProducto(datosCP.get(2));
        VentaPage vp = new VentaPage(driver);
        vp.comprarProducto();
        CarroComprasPage ccp = new CarroComprasPage(driver);
        reporte.addScreenshot(reporte.takeScreenshot(driver, "Selenium 2"), "Carrito de compras");
        Assert.assertTrue(ccp.confirmacionCarrito());
    }

    @Test
    public void atc05_AgregarProductoACarritoPorFiltros() throws Exception {
        reporte.createReport("atc05_AgregarProductoACarritoPorFiltros");
        datosCP = data.getData("atc05_AgregarProductoACarritoPorFiltros");
        PropertiesApp.loadPropertiesApp(PropertiesApp.getFileName());
        String homeURL = PropertiesApp.getAppUrl();
        HomePage hp = new HomePage(driver);
        reporte.logTestINFO("abrir: " + homeURL);
        hp.IrUrl(homeURL);
        hp.ingresarSeccionPortatiles();
        SeccionesPage sp = new SeccionesPage(driver);
        sp.filtrar(datosCP.get(1));
        sp.seleccionarProducto();
        VentaPage vp = new VentaPage(driver);
        vp.comprarProducto();
        CarroComprasPage ccp = new CarroComprasPage(driver);
        reporte.addScreenshot(reporte.takeScreenshot(driver, "Selenium 2"), "Carrito de compras");
        Assert.assertTrue(ccp.confirmacionCarrito());
    }

    @Test
    public void atc06_AgregarProductoACotizacion() throws Exception {
        reporte.createReport("atc06_AgregarProductoACotizacion");
        PropertiesApp.loadPropertiesApp(PropertiesApp.getFileName());
        String homeURL = PropertiesApp.getAppUrl();
        HomePage hp = new HomePage(driver);
        reporte.logTestINFO("abrir: " + homeURL);
        hp.IrUrl(homeURL);
        hp.seleccionarProductoRecomendado();
        VentaPage vp = new VentaPage(driver);
        vp.cotizarProducto();
        CotizacionPage cp = new CotizacionPage(driver);
        Assert.assertTrue(cp.confirmacionCotizacion());
    }

    @Test
    public void atc07_AgregarProductoACarritoYEliminar() throws Exception {
        reporte.createReport("atc07_AgregarProductoACarritoYEliminar");
        PropertiesApp.loadPropertiesApp(PropertiesApp.getFileName());
        String homeURL = PropertiesApp.getAppUrl();
        HomePage hp = new HomePage(driver);
        reporte.logTestINFO("abrir: " + homeURL);
        hp.IrUrl(homeURL);
        hp.seleccionarProductoRecomendado();
        VentaPage vp = new VentaPage(driver);
        vp.comprarProducto();
        CarroComprasPage ccp = new CarroComprasPage(driver);
        ccp.eliminarItemCarrito();
        Assert.assertTrue(ccp.confirmacionCarritoVacio());
    }

    @Test
    public void atc08_ConsultaTiempoDespacho() throws Exception {
        reporte.createReport("atc08_ConsultaTiempoDespacho");
        datosCP = data.getData("atc08_ConsultaTiempoDespacho");
        PropertiesApp.loadPropertiesApp(PropertiesApp.getFileName());
        String homeURL = PropertiesApp.getAppUrl();
        HomePage hp = new HomePage(driver);
        reporte.logTestINFO("abrir: " + homeURL);
        hp.IrUrl(homeURL);
        hp.buscarProducto(datosCP.get(1));
        SearchPage sp = new SearchPage(driver);
        sp.seleccionarProducto(datosCP.get(2));
        VentaPage vp = new VentaPage(driver);
        vp.calcularDespacho(datosCP.get(3));
        Assert.assertTrue(vp.confirmacionDespacho());
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
