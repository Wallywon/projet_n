package academicYear.controllers;

import academicYear.dtos.AcademicYearDTO;
import academicYear.services.AcademicYearService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
}
