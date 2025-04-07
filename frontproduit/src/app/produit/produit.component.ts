import { Component } from '@angular/core';
import { CategorieProduit } from '../models/categorie';
import { Produit } from '../models/produit';
import { CategorieService } from '../services/categorie.service';
import { ProduitService } from '../services/produit.service';
import * as bootstrap from 'bootstrap';

@Component({
  selector: 'app-produit',
  templateUrl: './produit.component.html',
  styleUrls: ['./produit.component.css']
})
export class ProduitComponent {
  produits: Produit[] = [];
  produit: Produit = new Produit('', '', 0, 0, '', new CategorieProduit('', ''));
  categories: CategorieProduit[] = [];
  editProduit: Produit | null = null;
  selectedProduit: Produit | null = null;
  selectedCategorieId: number | null = null;

  constructor(
    private produitService: ProduitService,
    private categorieService: CategorieService
  ) {}

  ngOnInit(): void {
    this.getAllProduits();
    this.getAllCategories();
  }

  getAllProduits() {
    this.produitService.getAllProduits().subscribe(
      (data) => {
        this.produits = data;
      },
      (error) => {
        console.error('Erreur lors de la récupération des produits:', error);
      }
    );
  }

  getAllCategories() {
    this.categorieService.getAllCategories().subscribe(
      (data) => {
        console.log('Data récupérée:', data);  // Voir ce qui est renvoyé par l'API
        this.categories = data;
      },
      (error) => {
        console.error('Erreur lors de la récupération des catégories:', error);
      }
    );
  }
  
  showDetails(produit: Produit) {
    this.selectedProduit = produit;

    // Open the modal programmatically using Bootstrap 5
    const modalElement = document.getElementById('productDetailsModal');
    const modal = new bootstrap.Modal(modalElement!);
    modal.show();
  }

  generateQRCodeUrl(productId: number | undefined): string {
    // Replace this with actual QR code generation logic or an API
    return `https://api.qrserver.com/v1/create-qr-code/?data=Product-${productId}&size=150x150`;
  }

  addProduit() {
    if (this.produit.nom && this.produit.description) {
      this.produitService.addProduit(this.produit).subscribe(
        (response) => {
          alert('Produit ajouté avec succès!');
          this.produit = new Produit('', '', 0, 0, '', new CategorieProduit('', '')); // Réinitialiser le formulaire
          this.getAllProduits(); // Mettre à jour la liste
          window.location.reload();
        },
        (error) => {
          console.error('Erreur lors de l\'ajout du produit:', error);
        }
      );
    }
  }

  deleteProduit(id: number) {
    this.produitService.deleteProduit(id).subscribe(
      () => {
        alert('Produit supprimé!');
        this.getAllProduits(); // Mettre à jour la liste après suppression
      },
      (error) => {
        console.error('Erreur lors de la suppression du produit:', error);
      }
    );
  }


  updateProduit() {
    if (this.editProduit && this.editProduit.id) {
      this.produitService.updateProduit(this.editProduit.id, this.editProduit).subscribe(
        (response) => {
          alert('Produit mis à jour!');
          this.getAllProduits(); // Mettre à jour la liste après mise à jour
          this.editProduit = null; // Réinitialiser l'édition
          window.location.reload();

        },
        (error) => {
          console.error('Erreur lors de la mise à jour du produit:', error);
        }
      );
    } else {
      console.error('ID du produit manquant ou produit non défini pour la mise à jour');
    }
  }
  

  setEditProduit(produit: Produit) {
    this.editProduit = { ...produit }; // Clone l'objet pour l'édition
  }

  cancelEdit() {
    this.editProduit = null; // Annuler l'édition
  }

  filterProduitsByCategorie() {
    if (this.selectedCategorieId) {
      this.produitService.getProduitsByCategorie(this.selectedCategorieId).subscribe(
        (data) => {
          this.produits = data;
        },
        (error) => {
          console.error('Erreur lors du filtrage des produits:', error);
        }
      );
    }
  }

}
