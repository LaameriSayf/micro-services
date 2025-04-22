package tn.esprit.examen.nomPrenomClasseExamen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.entities.CategorieProduit;
import tn.esprit.examen.nomPrenomClasseExamen.services.CategorieService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:4200") // Remplacez par l'URL de votre frontend

public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    // Ajouter une nouvelle catégorie
    @PostMapping("/addCategorie")
    public CategorieProduit addCategorie(@RequestBody CategorieProduit categorie) {
        return categorieService.addCategorie(categorie);
    }

    // Récupérer toutes les catégories
    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategorieProduit>> getAllCategories() {
        List<CategorieProduit> categories = categorieService.getAllCategories();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(categories);
    }

    // Récupérer une catégorie par ID
    @GetMapping("/getCategorieById/{id}")
    public Optional<CategorieProduit> getCategorieById(@PathVariable Long id) {
        return categorieService.getCategorieById(id);
    }

    // Mettre à jour une catégorie
    @PutMapping("/updateCategorie/{id}")
    public CategorieProduit updateCategorie(@PathVariable Long id, @RequestBody CategorieProduit categorieDetails) {
        return categorieService.updateCategorie(id, categorieDetails);
    }

    // Supprimer une catégorie par ID
    @DeleteMapping("/deleteCategorie/{id}")
    public void deleteCategorie(@PathVariable Long id) {
        categorieService.deleteCategorie(id);
    }
}
