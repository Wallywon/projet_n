package academicYear.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AcademicYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int tpSize;  // Taille TP
    private int tdSize;  // Taille TD
    private int nbUesOpt; // Nombre d'UE optionnelles
    private Long respId;  // ID du responsable

    @ElementCollection
    private Set<Long> studentsIds = new HashSet<>(); // Liste des IDs des étudiants inscrits

    public AcademicYear() {}

    public AcademicYear(String name, int tpSize, int tdSize, int nbUesOpt, Long respId) {
        this.name = name;
        this.tpSize = tpSize;
        this.tdSize = tdSize;
        this.nbUesOpt = nbUesOpt;
        this.respId = respId;
    }

    public void registerStudent(Long studentId) {
        studentsIds.add(studentId);
    }

    public void unregisterStudent(Long studentId) {
        studentsIds.remove(studentId);
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTpSize() {
        return tpSize;
    }

    public void setTpSize(int tpSize) {
        this.tpSize = tpSize;
    }

    public int getTdSize() {
        return tdSize;
    }

    public void setTdSize(int tdSize) {
        this.tdSize = tdSize;
    }

    public int getNbUesOpt() {
        return nbUesOpt;
    }

    public void setNbUesOpt(int nbUesOpt) {
        this.nbUesOpt = nbUesOpt;
    }

    public Long getRespId() {
        return respId;
    }

    public void setRespId(Long respId) {
        this.respId = respId;
    }

    public Set<Long> getStudentsIds() {
        return studentsIds;
    }

    public void setStudentsIds(Set<Long> studentsIds) {
        this.studentsIds = studentsIds;
    }
}
