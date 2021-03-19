package app.recursos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class DataDriven {

    private FileInputStream file;
    private XSSFWorkbook excel;
    private XSSFSheet hojaExcel;
    private String tituloCampoCasosDePrueba;

    public void preparacionExcel(String url, String sheetName, String tituloCampoCasosDePrueba) throws IOException {
        file = new FileInputStream(url);
        excel = new XSSFWorkbook(file);

        this.tituloCampoCasosDePrueba = tituloCampoCasosDePrueba;
        int sheets = excel.getNumberOfSheets();
        for (int i = 0; i < sheets; i++) {
            if (excel.getSheetName(i).equals(sheetName)) {
                hojaExcel = excel.getSheetAt(i);
            }
        }

    }

    public ArrayList<String> getData(String atcName) {
        ArrayList<String> array = new ArrayList<String>();
        Iterator<Row> filas = hojaExcel.iterator();
        Row firstRow = filas.next();
        Iterator<Cell> celda = firstRow.cellIterator();

        int k = 0;
        int column = 0;
        while (celda.hasNext()) {
            Cell selectedCell = celda.next();
            if (selectedCell.getStringCellValue().equals(tituloCampoCasosDePrueba)) {
                column = k;
            }
            k++;
        }

        while (filas.hasNext()) {
            Row r = filas.next();
            if (r.getCell(column).getStringCellValue().equals(atcName)) {
                Iterator<Cell> cv = r.cellIterator();

                while (cv.hasNext()) {
                    Cell c = cv.next();
                    if (c.getCellType() == CellType.STRING) {
                        array.add(c.getStringCellValue());
                    } else if (c.getCellType() == CellType.NUMERIC) {
                        array.add(NumberToTextConverter.toText(c.getNumericCellValue()));
                    }
                }
            }

        }
        return array;
    }

}
