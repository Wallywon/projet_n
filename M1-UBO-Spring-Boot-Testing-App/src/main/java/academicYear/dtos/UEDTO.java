package academicYear.dtos;

import java.util.Set;

public class UEDTO {
    private Long id;
    private String name;
    private boolean isRequired;
    private Integer capacity;
    private Long yearId;
    private Set<Long> studentIds;

    public UEDTO() {}

    public UEDTO(Long id, String name, boolean isRequired, Integer capacity, Long yearId, Set<Long> studentIds) {
        this.id = id;
        this.name = name;
        this.isRequired = isRequired;
        this.capacity = capacity;
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
