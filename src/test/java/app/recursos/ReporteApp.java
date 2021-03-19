package app.recursos;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ReporteApp {

    private static ExtentReports extent;
    private static ExtentTest test;

    public void setReport(){
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("src/test/java/app/reportes/reporte.html");
        htmlReporter.config().setEncoding("uft-8");
        htmlReporter.config().setDocumentTitle("Reportes de Ejecucion Selenium 2 - TSOFT");
        htmlReporter.config().setReportName("Resultados de Casos Automatizados");
        htmlReporter.config().setTheme(Theme.STANDARD);

        extent=new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Selenium 2", "Jose Saez 2021");
    }

    public void closeReport(){
        extent.flush();
    }

    public void createReport(String nombreTest){
        test = extent.createTest(nombreTest);
    }

    public void logTestINFO(String LogTest){
        test.log(Status.INFO,LogTest);
    }
    public void logTestPASS(String LogTest){
        test.log(Status.PASS,LogTest);
    }
    public void logTestFAIL(String LogTest){
        test.log(Status.FAIL,LogTest);
    }

    public String takeScreenshot(WebDriver driver, String nombreScreenshot) throws IOException {

        String dateTime = new SimpleDateFormat("ddMMyyyy_hhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinoScreenshot = "src/test/java/app/reportes/images/"+nombreScreenshot+dateTime+".png";
        File destinoFinalScreenshot = new File(destinoScreenshot);
        FileUtils.copyFile(source,destinoFinalScreenshot);
        return destinoScreenshot;
    }
    public void addScreenshot(String screenshotPath, String details) throws IOException {
        test.addScreenCaptureFromPath(screenshotPath,details);
    }
    public void reportLogFail(ITestResult result, WebDriver driver) throws IOException {

        String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
        test.log(Status.FAIL, "<details><summary><b><font color=red>Exception Occured, click to see details:" +
                "</font></b></summary>" + exceptionMessage.replaceAll(",", "<br>") + "</details> \n");
        addScreenshot(takeScreenshot(driver,"Selenium 2"),"screenshot FAIL");
    }

    public void reportLogSKIP(ITestResult result) throws IOException {
        test.log(Status.SKIP, "No se ejecuto el Caso: "+result.getName());
    }

    public void reportLogSuccess(ITestResult result) throws IOException {
        test.log(Status.PASS, "el Caso: "+result.getName()+" se ejecuto correctamente");
    }

}
