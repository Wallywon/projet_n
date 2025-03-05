package academicYear.repositories;

import academicYear.entities.UE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UERepository extends JpaRepository<UE, Long> {
    // récuperer les ues d'une formation à partir de l'id de la formation
    List<UE> findByYearId(Long yearId);
}
