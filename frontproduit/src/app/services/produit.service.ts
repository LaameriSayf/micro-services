import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produit } from '../models/produit';
import { Chart } from 'chart.js/auto';

@Injectable({
  providedIn: 'root'
})
export class ProduitService {

  private apiUrl = 'http://localhost:8089/api/produits'; // L'URL de votre API Spring Boot

  constructor(private http: HttpClient) {}

  // Récupérer tous les produits
  getAllProduits(): Observable<Produit[]> {
    return this.http.get<Produit[]>(`${this.apiUrl}/getAllProduits`);
  }

  // Ajouter un produit
  addProduit(produit: Produit): Observable<Produit> {
    return this.http.post<Produit>(`${this.apiUrl}/addProduit`, produit);
  }

  // Mettre à jour un produit
  updateProduit(id: number, produit: Produit): Observable<Produit> {
    return this.http.put<Produit>(`${this.apiUrl}/updateProduit/${id}`, produit);
  }

  // Supprimer un produit par son id
  deleteProduit(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/deleteProduit/${id}`);
  }

  // Récupérer un produit par son ID (optionnel selon le besoin)
  getProduitById(id: number): Observable<Produit> {
    return this.http.get<Produit>(`${this.apiUrl}/getProduitById/${id}`);
  }


  getProduitsByCategorie(categorieId: number): Observable<Produit[]> {
    return this.http.get<Produit[]>(`${this.apiUrl}/byCategorie/${categorieId}`);
  }
   // Get product statistics (product count per category)
   getProductStats(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/stats`);
  }
}
