package academicYear.services;

import academicYear.dtos.GroupeDTO;
import academicYear.entities.Groupe;
import academicYear.mappers.DTOMapper;
import academicYear.repositories.GroupeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupeService {
    private final GroupeRepository groupeRepository;

    public GroupeService(GroupeRepository groupeRepository) {
        this.groupeRepository = groupeRepository;
    }

    // Récupérer tous les groupes
    public List<GroupeDTO> getAllGroups() {
        return groupeRepository.findAll().stream()
                .map(DTOMapper::toDto)
                .collect(Collectors.toList());
    }

    // Récupérer un groupe par ID
    public Optional<GroupeDTO> getGroupById(Long id) {
        return groupeRepository.findById(id).map(DTOMapper::toDto);
    }

    // Récupérer les groupes d'une formation
    public List<GroupeDTO> getGroupsByYearId(Long yearId) {
        return groupeRepository.findByYearId(yearId).stream()
                .map(DTOMapper::toDto)
                .collect(Collectors.toList());
    }

    // Créer un nouveau groupe
    public GroupeDTO createGroup(GroupeDTO dto) {
        Groupe groupe = DTOMapper.toEntity(dto);
        Groupe savedGroupe = groupeRepository.save(groupe);
        return DTOMapper.toDto(savedGroupe);
    }

    // Modifier un groupe
    public Optional<GroupeDTO> updateGroup(Long id, GroupeDTO dto) {
        return groupeRepository.findById(id).map(existingGroupe -> {
            existingGroupe.setName(dto.getName());
            existingGroupe.setType(dto.getType());
            existingGroupe.setYearId(dto.getYearId());
            existingGroupe.setStudentIds(dto.getStudentIds());
            return DTOMapper.toDto(groupeRepository.save(existingGroupe));
        });
    }

    // Supprimer un groupe
    public boolean deleteGroup(Long id) {
        if (groupeRepository.existsById(id)) {
            groupeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
