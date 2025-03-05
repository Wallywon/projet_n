package academicYear.services;

import academicYear.dtos.AcademicYearDTO;
import academicYear.entities.AcademicYear;
import academicYear.mappers.DTOMapper;
import academicYear.repositories.AcademicYearRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AcademicYearService {
    private final AcademicYearRepository academicYearRepository;

    public AcademicYearService(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }

    // Récupérer toutes les formations
    public List<AcademicYearDTO> getAllAcademicYears() {
        return academicYearRepository.findAll().stream()
                .map(DTOMapper::toDto)
                .collect(Collectors.toList());
    }

    // Récupérer une formation par ID
    public Optional<AcademicYearDTO> getAcademicYearById(Long id) {
        return academicYearRepository.findById(id).map(DTOMapper::toDto);
    }

    // Créer une nouvelle formation
    public AcademicYearDTO createAcademicYear(AcademicYearDTO dto) {
        AcademicYear academicYear = DTOMapper.toEntity(dto);
        AcademicYear savedAcademicYear = academicYearRepository.save(academicYear);
        return DTOMapper.toDto(savedAcademicYear);
    }

    // Modifier une formation
    public Optional<AcademicYearDTO> updateAcademicYear(Long id, AcademicYearDTO dto) {
        return academicYearRepository.findById(id).map(existingAcademicYear -> {
            existingAcademicYear.setName(dto.getName());
            existingAcademicYear.setTpSize(dto.getTpSize());
            existingAcademicYear.setTdSize(dto.getTdSize());
            existingAcademicYear.setNbUesOpt(dto.getNbUesOpt());
            existingAcademicYear.setRespId(dto.getRespId());
            existingAcademicYear.setStudentsIds(dto.getStudentIds());
            return DTOMapper.toDto(academicYearRepository.save(existingAcademicYear));
        });
    }

    // Supprimer une formation
    public boolean deleteAcademicYear(Long id) {
        if (academicYearRepository.existsById(id)) {
            academicYearRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
