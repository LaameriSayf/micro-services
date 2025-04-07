export class CategorieProduit {
    id?: number;
    nom: string;
    description: string;
  
    constructor(nom: string, description: string, id?: number) {
      this.nom = nom;
      this.description = description;
      this.id = id;
    }
    
  }
  