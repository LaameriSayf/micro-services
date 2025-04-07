package tn.esprit.examen.nomPrenomClasseExamen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Produit;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit,Long> {
    List<Produit> findByCategorieId(Long categorieId);
}
