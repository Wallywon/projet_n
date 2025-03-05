package academicYear.services;

import academicYear.dtos.AcademicYearDTO;
import academicYear.dtos.UEDTO;
import academicYear.dtos.GroupeDTO;
import academicYear.entities.AcademicYear;
import academicYear.mappers.DTOMapper;
import academicYear.repositories.AcademicYearRepository;
import academicYear.repositories.UERepository;
import academicYear.repositories.GroupeRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AcademicYearService {
    private final AcademicYearRepository academicYearRepository;
    private final UERepository ueRepository;
    private final GroupeRepository groupeRepository;

    public AcademicYearService(AcademicYearRepository academicYearRepository, UERepository ueRepository, GroupeRepository groupeRepository) {
        this.academicYearRepository = academicYearRepository;
        this.ueRepository = ueRepository;
        this.groupeRepository = groupeRepository;
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

    // Enregistrer un étudiant
    public boolean registerStudent(Long academicYearId, Long studentId) {
        Optional<AcademicYear> academicYearOpt = academicYearRepository.findById(academicYearId);

        if (academicYearOpt.isPresent()) {
            AcademicYear academicYear = academicYearOpt.get();
            academicYear.getStudentsIds().add(studentId);
            academicYearRepository.save(academicYear);
            return true;
        }
        return false;
    }

    // Récupérer la liste des étudiants d’une formation
    public Set<Long> getStudentsByAcademicYear(Long academicYearId) {
        return academicYearRepository.findById(academicYearId)
                .map(AcademicYear::getStudentsIds)
                .orElse(Collections.emptySet());
    }

    // Récupérer la liste des UEs d’une formation
    public List<UEDTO> getUEsByAcademicYear(Long academicYearId) {
        return ueRepository.findByYearId(academicYearId).stream()
                .map(DTOMapper::toDto)
                .collect(Collectors.toList());
    }

    // Récupérer la liste des Groupes d’une formation
    public List<GroupeDTO> getGroupsByAcademicYear(Long academicYearId) {
        return groupeRepository.findByYearId(academicYearId).stream()
                .map(DTOMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean acceptStudent(Long academicYearId, Long studentId) {
        Optional<AcademicYear> academicYearOpt = academicYearRepository.findById(academicYearId);

        if (academicYearOpt.isPresent()) {
            AcademicYear academicYear = academicYearOpt.get();

            // Vérifier que l'étudiant est bien inscrit AVANT de pouvoir l'accepter
            if (!academicYear.getStudentsIds().contains(studentId)) {
                return false; // Étudiant non inscrit, donc refusé
            }

            // Ici, l'étudiant est déjà inscrit donc on valide son acceptation (optionnel si un autre champ de validation existait)
            academicYearRepository.save(academicYear);
            return true;
        }
        return false;
    }



    public boolean rejectStudent(Long academicYearId, Long studentId) {
        Optional<AcademicYear> academicYearOpt = academicYearRepository.findById(academicYearId);

        if (academicYearOpt.isPresent()) {
            AcademicYear academicYear = academicYearOpt.get();

            // Vérifier que l'étudiant est bien inscrit avant de pouvoir le rejeter
            if (!academicYear.getStudentsIds().contains(studentId)) {
                return false; // Étudiant non inscrit, donc rejet impossible
            }

            // Supprimer l'étudiant de la liste des inscrits
            academicYear.getStudentsIds().remove(studentId);
            academicYearRepository.save(academicYear);
            return true;
        }
        return false;
    }


}
