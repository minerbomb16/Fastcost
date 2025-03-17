package com.fastcost.operations;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ElisoftReader {

    public int rows = 0;
    public int cols = 0;
    public List<List<String>> data;
    public void readData() {
        String fileLocation = "data/Dane.xlsx";
        try {
            File dataElisoft = new File(fileLocation);
            dataElisoft.createNewFile();
            if(dataElisoft.length() > 0) {
                FileInputStream file = new FileInputStream(fileLocation);
                XSSFWorkbook workbook = new XSSFWorkbook(file);
                XSSFSheet sheet = workbook.getSheetAt(0);

                rows = sheet.getLastRowNum();
                cols = sheet.getRow(1).getLastCellNum();

                data = new ArrayList<>();

                for(int r = 1; r < rows; r++) {
                    XSSFRow row = sheet.getRow(r);
                    List<String> rowData = new ArrayList<>();
                    for(int c = 0; c < cols; c++) {
                        XSSFCell cell = row.getCell(c);
                        rowData.add(getCellValueAsString(cell));
                    }
                    data.add(rowData);
                }

                rows--;
                file.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getCellValueAsString(XSSFCell cell) {
        if (cell == null) {
            return "";
        } else {
            if (cell.getCellType() == CellType.NUMERIC) {
                return String.valueOf(cell.getNumericCellValue());
            } else if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            } else {
                return "";
            }
        }
    }

    public String getValue(int r, int c) {
        return data.get(r).get(c);
    }

}
