package com.parser.service;

import com.parser.model.Student;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    
    // Liste statique pour stocker les étudiants en mémoire
    private static final List<Student> inMemoryStudents = new ArrayList<>();
    
    public DatabaseService() {
        // Pas besoin d'initialisation spécifique
    }
    
    public boolean saveStudents(List<Student> students) {
        try {
            // Ajouter à notre liste en mémoire
            inMemoryStudents.addAll(students);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Student> getAllStudents() {
        // Retourner une copie de la liste
        return new ArrayList<>(inMemoryStudents);
    }
    
    public void close() {
        // Rien à fermer
    }
}