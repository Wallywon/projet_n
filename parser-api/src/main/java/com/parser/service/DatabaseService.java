package com.parser.service;

import com.parser.model.Student;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    
    private static final List<Student> inMemoryStudents = new ArrayList<>();
    
    public DatabaseService() {
    }
    
    public boolean saveStudents(List<Student> students) {
        try {
            inMemoryStudents.addAll(students);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Student> getAllStudents() {
        return new ArrayList<>(inMemoryStudents);
    }
    
    public void close() {
    }
}
