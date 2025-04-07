package tn.esprit.examen.nomPrenomClasseExamen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Produit;
import tn.esprit.examen.nomPrenomClasseExamen.services.ProduitService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produits")
@CrossOrigin(origins = "http://localhost:4200") // Remplacez par l'URL de votre frontend

public class ProduitController {

    @Autowired
    private ProduitService produitService;

    // Ajouter un nouveau produit
    @PostMapping("/addProduit")
    public Produit addProduit(@RequestBody Produit produit) {
        return produitService.addProduit(produit);
    }

    // Récupérer tous les produits
    @PreAuthorize("hasAuthority('ROLE_admin')")
    @GetMapping("/getAllProduits")
    public List<Produit> getAllProduits() {
        return produitService.getAllProduits();
    }
    // Récupérer un produit par ID
    @GetMapping("/getProduitById/{id}")
    public Optional<Produit> getProduitById(@PathVariable Long id) {
        return produitService.getProduitById(id);
    }

    // Mettre à jour un produit
    @PutMapping("/updateProduit/{id}")
    public Produit updateProduit(@PathVariable Long id, @RequestBody Produit produitDetails) {
        return produitService.updateProduit(id, produitDetails);
    }

    // Supprimer un produit par ID
    @DeleteMapping("/deleteProduit/{id}")
    public void deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
    }

    @GetMapping("/byCategorie/{categorieId}")
    public ResponseEntity<List<Produit>> getProduitsByCategorie(@PathVariable Long categorieId) {
        List<Produit> produits = produitService.getProduitsByCategorie(categorieId);
        if (produits.isEmpty()) {
            return ResponseEntity.noContent().build(); // Si aucune donnée n'est trouvée
        }
        return ResponseEntity.ok(produits);
    }
}
