package academicYear.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean isRequired;
    private Integer capacity; // Nombre max d'étudiants si optionnelle
    private Long yearId; // ID de la formation

    @ElementCollection
    private Set<Long> studentIds = new HashSet<>(); // Liste des IDs des étudiants inscrits

    public UE() {}

    public UE(String name, boolean isRequired, Integer capacity, Long yearId) {
        this.name = name;
        this.isRequired = isRequired;
        this.capacity = capacity;
        this.yearId = yearId;
    }

    public void registerStudent(Long studentId) {
        if (capacity == null || studentIds.size() < capacity) {
            studentIds.add(studentId);
        }
    }

    public void unregisterStudent(Long studentId) {
        studentIds.remove(studentId);
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long yearId) {
        this.yearId = yearId;
    }

    public Set<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(Set<Long> studentIds) {
        this.studentIds = studentIds;
    }
}
