package academicYear.dtos;

import java.util.Set;

public class GroupeDTO {
    private Long id;
    private String name;
    private String type; // TD ou TP
    private Long yearId;
    private Set<Long> studentIds;

    public GroupeDTO() {}

    public GroupeDTO(Long id, String name, String type, Long yearId, Set<Long> studentIds) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.yearId = yearId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
