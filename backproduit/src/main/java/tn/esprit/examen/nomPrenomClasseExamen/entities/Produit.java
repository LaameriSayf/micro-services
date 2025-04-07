package tn.esprit.examen.nomPrenomClasseExamen.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private double prix;
    private int stock;
    private String imageUrl; // Stocke l'URL de l'image

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference  // Empêche la sérialisation du côté Catégorie


    private CategorieProduit categorie;
}
