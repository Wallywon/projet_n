package academicYear.controllers;

import academicYear.dtos.GroupeDTO;
import academicYear.services.GroupeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupeController {

    private final GroupeService groupeService;

    public GroupeController(GroupeService groupeService) {
        this.groupeService = groupeService;
    }

    // Récupérer tous les groupes
    @GetMapping
    public List<GroupeDTO> getAllGroups() {
        return groupeService.getAllGroups();
    }

    // Récupérer un groupe par ID
    @GetMapping("/{id}")
    public ResponseEntity<GroupeDTO> getGroupById(@PathVariable Long id) {
        return groupeService.getGroupById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer les groupes d'une formation
    @GetMapping("/academicYear/{yearId}")
    public List<GroupeDTO> getGroupsByYearId(@PathVariable Long yearId) {
        return groupeService.getGroupsByYearId(yearId);
    }

    // Créer un nouveau groupe
    @PostMapping
    public ResponseEntity<GroupeDTO> createGroup(@RequestBody GroupeDTO dto) {
        return ResponseEntity.ok(groupeService.createGroup(dto));
    }

    // Modifier un groupe
    @PutMapping("/{id}")
    public ResponseEntity<GroupeDTO> updateGroup(@PathVariable Long id, @RequestBody GroupeDTO dto) {
        return groupeService.updateGroup(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Supprimer un groupe
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        return groupeService.deleteGroup(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
