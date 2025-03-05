package com.parser.model;

import java.util.Date;

public class Student {
    private String firstName;
    private String lastName;
    private String studentNumber;
    private Date birthDate;
    
    // Constructors
    public Student() {
    }
    
    public Student(String firstName, String lastName, String studentNumber, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentNumber = studentNumber;
        this.birthDate = birthDate;
    }
    
    // Getters and setters
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getStudentNumber() {
        return studentNumber;
    }
    
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
    
    public Date getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    @Override
    public String toString() {
        return "Student [firstName=" + firstName + ", lastName=" + lastName + ", studentNumber=" + studentNumber
                + ", birthDate=" + birthDate + "]";
    }
}