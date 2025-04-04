package tn.esprit.examen.nomPrenomClasseExamen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.CategorieProduit;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {

    @Autowired
    private CategoryRepository categoryRepository;

    // CREATE
    public CategorieProduit addCategorie(CategorieProduit categorie) {
        return categoryRepository.save(categorie);
    }

    // READ
    public List<CategorieProduit> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<CategorieProduit> getCategorieById(Long id) {
        return categoryRepository.findById(id);
    }

    // UPDATE
    public CategorieProduit updateCategorie(Long id, CategorieProduit categorieDetails) {
        CategorieProduit categorie = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categorie not found"));
        categorie.setNom(categorieDetails.getNom());
        categorie.setDescription(categorieDetails.getDescription());
        return categoryRepository.save(categorie);
    }

    // DELETE
    public void deleteCategorie(Long id) {
        CategorieProduit categorie = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categorie not found"));
        categoryRepository.delete(categorie);
    }
}
