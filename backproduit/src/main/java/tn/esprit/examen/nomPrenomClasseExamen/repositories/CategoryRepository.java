package tn.esprit.examen.nomPrenomClasseExamen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.examen.nomPrenomClasseExamen.entities.CategorieProduit;
@Repository
public interface CategoryRepository extends JpaRepository<CategorieProduit,Long> {
}
