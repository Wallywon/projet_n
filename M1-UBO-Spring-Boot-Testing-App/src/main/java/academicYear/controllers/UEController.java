package academicYear.controllers;

import academicYear.dtos.UEDTO;
import academicYear.services.UEService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ue")
public class UEController {

    private final UEService ueService;

    public UEController(UEService ueService) {
        this.ueService = ueService;
    }

    // Récupérer toutes les UEs
    @GetMapping
    public List<UEDTO> getAllUEs() {
        return ueService.getAllUEs();
    }

    // Récupérer une UE par ID
    @GetMapping("/{id}")
    public ResponseEntity<UEDTO> getUEById(@PathVariable Long id) {
        return ueService.getUEById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer les UEs d'une formation
    @GetMapping("/academicYear/{yearId}")
    public List<UEDTO> getUEsByYearId(@PathVariable Long yearId) {
        return ueService.getUEsByYearId(yearId);
    }

    // Créer une nouvelle UE
    @PostMapping
    public ResponseEntity<UEDTO> createUE(@RequestBody UEDTO dto) {
        return ResponseEntity.ok(ueService.createUE(dto));
    }

    // Modifier une UE
    @PutMapping("/{id}")
    public ResponseEntity<UEDTO> updateUE(@PathVariable Long id, @RequestBody UEDTO dto) {
        return ueService.updateUE(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une UE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUE(@PathVariable Long id) {
        return ueService.deleteUE(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/{idUe}/register/{numEtudiant}")
    public ResponseEntity<String> registerStudentToUE(@PathVariable Long idUe, @PathVariable Long numEtudiant) {
        boolean isRegistered = ueService.registerStudentToUE(idUe, numEtudiant);

        if (!isRegistered) {
            return ResponseEntity.badRequest().body(
                    "Impossible d'inscrire l'étudiant : " +
                            "il doit être inscrit et accepté dans la formation, " +
                            "ne pas être déjà inscrit à l'UE, et il doit rester de la place."
            );
        }
        return ResponseEntity.ok("Étudiant inscrit à l'UE avec succès !");
    }

    @DeleteMapping("/{idUe}/unregister/{numEtudiant}")
    public ResponseEntity<String> unregisterStudentFromUE(@PathVariable Long idUe, @PathVariable Long numEtudiant) {
        boolean isUnregistered = ueService.unregisterStudentFromUE(idUe, numEtudiant);

        if (!isUnregistered) {
            return ResponseEntity.badRequest().body(
                    "Impossible de désinscrire l'étudiant : " +
                            "il doit être inscrit et accepté dans la formation et être actuellement inscrit à l'UE."
            );
        }
        return ResponseEntity.ok("Étudiant désinscrit de l'UE avec succès !");
    }

}
