package academicYear.repositories;

import academicYear.entities.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long> {
    // récuperer les groupes d'une formation à partir de l'id de la formation
    List<Groupe> findByYearId(Long yearId);

}
