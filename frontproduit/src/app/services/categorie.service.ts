import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategorieProduit } from '../models/categorie';

@Injectable({
  providedIn: 'root'
})
export class CategorieService {

  private apiUrl = 'http://localhost:8089/api/categories'; // L'URL de votre API Spring Boot

  constructor(private http: HttpClient) {}

  // Récupérer toutes les catégories
  getAllCategories(): Observable<CategorieProduit[]> {
    return this.http.get<CategorieProduit[]>(`${this.apiUrl}/getAllCategories`);
  }

  // Ajouter une catégorie
  addCategorie(categorie: CategorieProduit): Observable<CategorieProduit> {
    return this.http.post<CategorieProduit>(`${this.apiUrl}/addCategorie`, categorie);
  }

  // Récupérer une catégorie par ID
  getCategorieById(id: number): Observable<CategorieProduit> {
    return this.http.get<CategorieProduit>(`${this.apiUrl}/getCategorieById/${id}`);
  }

  // Mettre à jour une catégorie
  updateCategorie(id: number, categorie: CategorieProduit): Observable<CategorieProduit> {
    return this.http.put<CategorieProduit>(`${this.apiUrl}/updateCategorie/${id}`, categorie);
  }

  // Supprimer une catégorie par son id
  deleteCategorie(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/deleteCategorie/${id}`);
  }
}
