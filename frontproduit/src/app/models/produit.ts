import { CategorieProduit } from "./categorie";

export class Produit {
    id?: number;
    nom: string;
    description: string;
    prix: number;
    stock: number;
    imageUrl: string;
    categorie: CategorieProduit;
  
    constructor(
      nom: string,
      description: string,
      prix: number,
      stock: number,
      imageUrl: string,
      categorie: CategorieProduit,
      id?: number
    ) {
      this.nom = nom;
      this.description = description;
      this.prix = prix;
      this.stock = stock;
      this.imageUrl = imageUrl;
      this.categorie = categorie;
      this.id = id;
    }
  }
  