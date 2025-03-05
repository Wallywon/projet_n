package academicYear.mappers;

import academicYear.dtos.AcademicYearDTO;
import academicYear.dtos.GroupeDTO;
import academicYear.dtos.UEDTO;
import academicYear.entities.AcademicYear;
import academicYear.entities.Groupe;
import academicYear.entities.UE;

public class DTOMapper {

    // Convertir une entité AcademicYear en DTO
    public static AcademicYearDTO toDto(AcademicYear academicYear) {
        return new AcademicYearDTO(
                academicYear.getId(),
                academicYear.getName(),
                academicYear.getTpSize(),
                academicYear.getTdSize(),
                academicYear.getNbUesOpt(),
                academicYear.getRespId(),
                academicYear.getStudentsIds()
        );
    }

    // Convertir un DTO en entité AcademicYear
    public static AcademicYear toEntity(AcademicYearDTO dto) {
        AcademicYear academicYear = new AcademicYear(
                dto.getName(),
                dto.getTpSize(),
                dto.getTdSize(),
                dto.getNbUesOpt(),
                dto.getRespId()
        );
        academicYear.setStudentsIds(dto.getStudentIds());
        return academicYear;
    }

    // Convertir une entité Group en DTO
    public static GroupeDTO toDto(Groupe groupe) {
        return new GroupeDTO(
                groupe.getId(),
                groupe.getName(),
                groupe.getType(),
                groupe.getYearId(),
                groupe.getStudentIds()
        );
    }

    // Convertir un DTO en entité Group
    public static Groupe toEntity(GroupeDTO dto) {
        Groupe groupe = new Groupe(
                dto.getName(),
                dto.getYearId(),
                dto.getType()
        );
        groupe.setStudentIds(dto.getStudentIds());
        return groupe;
    }

    // Convertir une entité UE en DTO
    public static UEDTO toDto(UE ue) {
        return new UEDTO(
                ue.getId(),
                ue.getName(),
                ue.isRequired(),
                ue.getCapacity(),
                ue.getYearId(),
                ue.getStudentIds()
        );
    }

    // Convertir un DTO en entité UE
    public static UE toEntity(UEDTO dto) {
        UE ue = new UE(
                dto.getName(),
                dto.isRequired(),
                dto.getCapacity(),
                dto.getYearId()
        );
        ue.setStudentIds(dto.getStudentIds());
        return ue;
    }
}
