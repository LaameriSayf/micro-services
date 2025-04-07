import { Component, OnInit } from '@angular/core';
import { ProduitService } from '../services/produit.service';
import { Produit } from '../models/produit';
import { KeycloakService } from '../keycloak/keycloak.service';

@Component({
  selector: 'app-frontproduit',
  templateUrl: './frontproduit.component.html',
  styleUrls: ['./frontproduit.component.css']
})
export class FrontproduitComponent implements OnInit {
  produits: Produit[] = [];
  selectedProduit: Produit | null = null;
  showModal = false;

  constructor(private produitService: ProduitService,private keycloakService: KeycloakService) { }

  ngOnInit(): void {
    this.loadProduits();
  }

  loadProduits(): void {
    this.produitService.getAllProduits().subscribe({
      next: (data) => this.produits = data,
      error: (err) => console.error('Error:', err)
    });
  }

  generateQRCode(produit: Produit): string {
    const productData = {
      name: produit.nom,
      desc: produit.description,
      price: produit.prix,
      category: produit.categorie?.nom
    };
    return `https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=${encodeURIComponent(JSON.stringify(productData))}`;
  }

  openModal(produit: Produit): void {
    this.selectedProduit = produit;
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
  }

  login() {
    this.keycloakService.login();
  }
}