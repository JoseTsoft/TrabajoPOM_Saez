package app.pom.base;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesApp {

    private static String APP_URL;
    private static String APP_USER;
    private static String APP_PASS;
    private static String JDBC_FILE_NAME = "app";

    private static String APP_URL_EXCEL;
    private static String APP_SHEETNAME_EXCEL;
    private static String APP_TITULOTC_EXCEL;

    public static Properties loadPropertiesApp(String file) throws Exception {
        //Obtengo un objeto enumeracion con las llaves del archivo
        FileInputStream fis = new FileInputStream("src/test/java/app/recursos/app.properties");
        Properties propiedades = new Properties();
        propiedades.load(fis);
        APP_URL = propiedades.getProperty("url");
        APP_USER = propiedades.getProperty("nroCuenta");
        APP_PASS = propiedades.getProperty("contraseña");
        APP_URL_EXCEL = propiedades.getProperty("urlExcel");
        APP_SHEETNAME_EXCEL = propiedades.getProperty("sheetname");
        APP_TITULOTC_EXCEL = propiedades.getProperty("tituloTC");
        return propiedades;
    }

    public static String getAppUrl() {
        return APP_URL;
    }

    public static String getAppUser() {
        return APP_USER;
    }

    public static String getAppPass() {
        return APP_PASS;
    }

    public static String getFileName() {
        return JDBC_FILE_NAME;
    }

    public static String getAppUrlExcel() {
        return APP_URL_EXCEL;
    }

    public static String getAppSheetnameExcel() {
        return APP_SHEETNAME_EXCEL;
    }

    public static String getAppTitulotcExcel() {
        return APP_TITULOTC_EXCEL;
    }
}
