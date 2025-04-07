package tn.esprit.examen.nomPrenomClasseExamen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Produit;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.ProduitRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    // CREATE
    public Produit addProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    // READ
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    public Optional<Produit> getProduitById(Long id) {
        return produitRepository.findById(id);
    }

    // UPDATE
    public Produit updateProduit(Long id, Produit produitDetails) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit not found"));
        produit.setNom(produitDetails.getNom());
        produit.setDescription(produitDetails.getDescription());
        produit.setPrix(produitDetails.getPrix());
        produit.setStock(produitDetails.getStock());
        produit.setImageUrl(produitDetails.getImageUrl());
        return produitRepository.save(produit);
    }

    // DELETE
    public void deleteProduit(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit not found"));
        produitRepository.delete(produit);
    }


    public List<Produit> getProduitsByCategorie(Long categorieId) {
        return produitRepository.findByCategorieId(categorieId);
    }
}
