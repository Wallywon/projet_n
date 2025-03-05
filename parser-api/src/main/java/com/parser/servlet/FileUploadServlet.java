package com.parser.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import com.parser.model.Student;
import com.parser.service.DatabaseService;
import com.parser.service.ExcelParser;

@WebServlet("/upload")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ExcelParser excelParser;
    private DatabaseService databaseService;
    
    @Override
    public void init() throws ServletException {
        excelParser = new ExcelParser();
        databaseService = new DatabaseService();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Get the uploaded file
            Part filePart = request.getPart("excelFile");
            
            if (filePart != null) {
                // Parse students from the Excel file
                List<Student> students = excelParser.parseStudentsFromExcel(filePart.getInputStream());
                
                // Save students to database
                boolean success = databaseService.saveStudents(students);
                
                if (success) {
                    request.setAttribute("message", "Successfully uploaded " + students.size() + " students.");
                    request.setAttribute("students", students);
                } else {
                    request.setAttribute("error", "Failed to save students to database.");
                }
            } else {
                request.setAttribute("error", "No file uploaded.");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error processing file: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Forward to result page
        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Display all students from the database
        List<Student> students = databaseService.getAllStudents();
        request.setAttribute("students", students);
        request.getRequestDispatcher("/viewStudents.jsp").forward(request, response);
    }
    
    @Override
    public void destroy() {
        if (databaseService != null) {
            databaseService.close();
        }
    }
}