package com.parser.service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.parser.model.Student;

public class ExcelParser {
    
    public List<Student> parseStudentsFromExcel(InputStream excelFile) {
        List<Student> students = new ArrayList<>();
        
        try (Workbook workbook = new XSSFWorkbook(excelFile)) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
            
            // Skip header row if present
            int startRow = sheet.getFirstRowNum() + 1;
            int endRow = sheet.getLastRowNum();
            
            for (int i = startRow; i <= endRow; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                
                String firstName = getCellValueAsString(row.getCell(0));
                String lastName = getCellValueAsString(row.getCell(1));
                String studentNumber = getCellValueAsString(row.getCell(2));
                Date birthDate = getCellValueAsDate(row.getCell(3));
                
                if (firstName != null && !firstName.trim().isEmpty()) {
                    Student student = new Student(firstName, lastName, studentNumber, birthDate);
                    students.add(student);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return students;
    }
    
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    return dateFormat.format(cell.getDateCellValue());
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                switch (cell.getCachedFormulaResultType()) {
                    case STRING:
                        return cell.getStringCellValue();
                    case NUMERIC:
                        return String.valueOf((long) cell.getNumericCellValue());
                    default:
                        return "";
                }
            default:
                return "";
        }
    }
    
    private Date getCellValueAsDate(Cell cell) {
        if (cell == null) {
            return null;
        }
        
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getDateCellValue();
        }
        
        return null;
    }
}