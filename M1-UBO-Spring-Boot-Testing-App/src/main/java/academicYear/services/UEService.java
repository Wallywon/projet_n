package academicYear.services;

import academicYear.dtos.UEDTO;
import academicYear.entities.UE;
import academicYear.mappers.DTOMapper;
import academicYear.repositories.UERepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UEService {
    private final UERepository ueRepository;

    public UEService(UERepository ueRepository) {
        this.ueRepository = ueRepository;
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
}
