package academicYear.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long yearId; // ID de la formation

    private String type; // Type du groupe (TD ou TP)

    @ElementCollection
    private Set<Long> studentIds = new HashSet<>(); // Liste des IDs des étudiants

    public Groupe() {}

    public Groupe(String name, Long yearId, String type) {
        this.name = name;
        this.yearId = yearId;
        this.type = type;
    }

    public void addStudent(Long studentId) {
        studentIds.add(studentId);
    }

    public void removeStudent(Long studentId) {
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

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long yearId) {
        this.yearId = yearId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(Set<Long> studentIds) {
        this.studentIds = studentIds;
    }
}
