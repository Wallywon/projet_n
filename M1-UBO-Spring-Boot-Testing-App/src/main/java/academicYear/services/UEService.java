package academicYear.services;

import academicYear.dtos.UEDTO;
import academicYear.entities.AcademicYear;
import academicYear.entities.UE;
import academicYear.mappers.DTOMapper;
import academicYear.repositories.AcademicYearRepository;
import academicYear.repositories.UERepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UEService {
    private final UERepository ueRepository;
    private final AcademicYearRepository academicYearRepository;

    public UEService(AcademicYearRepository academicYearRepository, UERepository ueRepository) {
        this.ueRepository = ueRepository;
        this.academicYearRepository = academicYearRepository;
    }

    // Récupérer toutes les UEs
    public List<UEDTO> getAllUEs() {
        return ueRepository.findAll().stream()
                .map(DTOMapper::toDto)
                .collect(Collectors.toList());
    }

    // Récupérer une UE par ID
    public Optional<UEDTO> getUEById(Long id) {
        return ueRepository.findById(id).map(DTOMapper::toDto);
    }

    // Récupérer les UEs d'une formation
    public List<UEDTO> getUEsByYearId(Long yearId) {
        return ueRepository.findByYearId(yearId).stream()
                .map(DTOMapper::toDto)
                .collect(Collectors.toList());
    }

    // Créer une nouvelle UE
    public UEDTO createUE(UEDTO dto) {
        UE ue = DTOMapper.toEntity(dto);
        UE savedUE = ueRepository.save(ue);
        return DTOMapper.toDto(savedUE);
    }

    // Modifier une UE
    public Optional<UEDTO> updateUE(Long id, UEDTO dto) {
        return ueRepository.findById(id).map(existingUE -> {
            existingUE.setName(dto.getName());
            existingUE.setRequired(dto.isRequired());
            existingUE.setCapacity(dto.getCapacity());
            existingUE.setYearId(dto.getYearId());
            existingUE.setStudentIds(dto.getStudentIds());
            return DTOMapper.toDto(ueRepository.save(existingUE));
        });
    }

    // Supprimer une UE
    public boolean deleteUE(Long id) {
        if (ueRepository.existsById(id)) {
            ueRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean registerStudentToUE(Long ueId, Long studentId) {
        Optional<UE> ueOpt = ueRepository.findById(ueId);

        if (ueOpt.isPresent()) {
            UE ue = ueOpt.get();

            // Vérifier si la formation de l'UE existe et si l'étudiant est bien inscrit ET accepté
            Optional<AcademicYear> academicYearOpt = academicYearRepository.findById(ue.getYearId());
            if (academicYearOpt.isEmpty() || !academicYearOpt.get().getStudentsIds().contains(studentId)) {
                return false; // L'étudiant n'est pas inscrit dans la formation
            }

            // Vérifier si l'UE a une capacité et si elle est atteinte
            if (ue.getCapacity() != null && ue.getStudentIds().size() >= ue.getCapacity()) {
                return false; // L'UE est pleine
            }

            // Vérifier si l'étudiant est déjà inscrit à cette UE
            if (ue.getStudentIds().contains(studentId)) {
                return false; // L'étudiant est déjà inscrit
            }

            // Ajouter l'étudiant à la liste des inscrits
            ue.getStudentIds().add(studentId);
            ueRepository.save(ue);
            return true;
        }
        return false;
    }

    public boolean unregisterStudentFromUE(Long ueId, Long studentId) {
        Optional<UE> ueOpt = ueRepository.findById(ueId);

        if (ueOpt.isPresent()) {
            UE ue = ueOpt.get();

            // Vérifier si la formation de l'UE existe et si l'étudiant est bien inscrit ET accepté
            Optional<AcademicYear> academicYearOpt = academicYearRepository.findById(ue.getYearId());
            if (academicYearOpt.isEmpty() || !academicYearOpt.get().getStudentsIds().contains(studentId)) {
                return false; // L'étudiant n'est pas inscrit dans la formation
            }

            // Vérifier si l'étudiant est inscrit dans l'UE avant de le désinscrire
            if (!ue.getStudentIds().contains(studentId)) {
                return false; // L'étudiant n'est pas inscrit dans cette UE
            }

            // Supprimer l'étudiant de la liste des inscrits
            ue.getStudentIds().remove(studentId);
            ueRepository.save(ue);
            return true;
        }
        return false;
    }

}
