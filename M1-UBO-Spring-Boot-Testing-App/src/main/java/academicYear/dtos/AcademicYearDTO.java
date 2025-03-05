package academicYear.dtos;

import java.util.Set;

public class AcademicYearDTO {
    private Long id;
    private String name;
    private int tpSize;
    private int tdSize;
    private int nbUesOpt;
    private Long respId;
    private Set<Long> studentIds;

    public AcademicYearDTO() {}

    public AcademicYearDTO(Long id, String name, int tpSize, int tdSize, int nbUesOpt, Long respId, Set<Long> studentIds) {
        this.id = id;
        this.name = name;
        this.tpSize = tpSize;
        this.tdSize = tdSize;
        this.nbUesOpt = nbUesOpt;
        this.respId = respId;
        this.studentIds = studentIds;
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

    public Set<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(Set<Long> studentIds) {
        this.studentIds = studentIds;
    }
}
