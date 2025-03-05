package academicYear.controllers;

import academicYear.dtos.AcademicYearDTO;
import academicYear.dtos.GroupeDTO;
import academicYear.dtos.UEDTO;
import academicYear.services.AcademicYearService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/academicyear")
public class AcademicYearController {

    private final AcademicYearService academicYearService;

    public AcademicYearController(AcademicYearService academicYearService) {
        this.academicYearService = academicYearService;
    }

    // Récupérer toutes les formations
    @GetMapping
    public List<AcademicYearDTO> getAllAcademicYears() {
        return academicYearService.getAllAcademicYears();
    }

    // Récupérer une formation par ID
    @GetMapping("/{id}")
    public ResponseEntity<AcademicYearDTO> getAcademicYearById(@PathVariable Long id) {
        return academicYearService.getAcademicYearById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer une nouvelle formation
    @PostMapping
    public ResponseEntity<AcademicYearDTO> createAcademicYear(@RequestBody AcademicYearDTO dto) {
        return ResponseEntity.ok(academicYearService.createAcademicYear(dto));
    }

    // Modifier une formation
    @PutMapping("/{id}")
    public ResponseEntity<AcademicYearDTO> updateAcademicYear(@PathVariable Long id, @RequestBody AcademicYearDTO dto) {
        return academicYearService.updateAcademicYear(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une formation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcademicYear(@PathVariable Long id) {
        return academicYearService.deleteAcademicYear(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/register/{numEtudiant}")
    public ResponseEntity<String> registerStudent(@PathVariable Long id, @PathVariable Long numEtudiant) {
        boolean isRegistered = academicYearService.registerStudent(id, numEtudiant);
        return isRegistered
                ? ResponseEntity.ok("Étudiant inscrit avec succès !")
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Object> getStudentsByAcademicYear(@PathVariable Long id) {
        Set<Long> students = academicYearService.getStudentsByAcademicYear(id);

        if (students.isEmpty()) {
            return ResponseEntity.notFound().build(); // Retourne un 404 si aucun étudiant trouvé
        }

        return ResponseEntity.ok().body(students); // Spécification explicite du type dans ResponseEntity
    }

    @GetMapping("/{id}/groups")
    public ResponseEntity<List<GroupeDTO>> getGroupsByAcademicYear(@PathVariable Long id) {
        List<GroupeDTO> groups = academicYearService.getGroupsByAcademicYear(id);

        if (groups.isEmpty()) {
            return ResponseEntity.notFound().build(); // Retourne 404 si aucun groupe trouvé
        }

        return ResponseEntity.ok(groups); // Retourne la liste des Groupes avec un 200 OK
    }

    @GetMapping("/{id}/ues")
    public ResponseEntity<List<UEDTO>> getUEsByAcademicYear(@PathVariable Long id) {
        List<UEDTO> ues = academicYearService.getUEsByAcademicYear(id);

        if (ues.isEmpty()) {
            return ResponseEntity.notFound().build(); // Retourne 404 si aucune UE trouvée
        }

        return ResponseEntity.ok(ues); // Retourne la liste des UEs avec un 200 OK
    }

    @PostMapping("/{id}/accept/{numEtudiant}")
    public ResponseEntity<String> acceptStudent(@PathVariable Long id, @PathVariable Long numEtudiant) {
        boolean isAccepted = academicYearService.acceptStudent(id, numEtudiant);
        return isAccepted
                ? ResponseEntity.ok("Étudiant accepté avec succès !")
                : ResponseEntity.badRequest().body("L'étudiant doit être inscrit avant d'être accepté.");
    }


    @PostMapping("/{id}/reject/{numEtudiant}")
    public ResponseEntity<String> rejectStudent(@PathVariable Long id, @PathVariable Long numEtudiant) {
        boolean isRejected = academicYearService.rejectStudent(id, numEtudiant);
        return isRejected
                ? ResponseEntity.ok("Étudiant rejeté avec succès !")
                : ResponseEntity.badRequest().body("Impossible de rejeter un étudiant non inscrit.");
    }


}
