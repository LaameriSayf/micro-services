import { Injectable } from '@angular/core';
import { KeycloakService } from '../keycloak/keycloak.service'; // Importer KeycloakService

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private keycloakService: KeycloakService) { }

  // Méthode pour récupérer le token JWT de Keycloak
  getToken(): string | null {
    return this.keycloakService.getToken() || null;  // Retourne le token ou null si l'utilisateur n'est pas authentifié
  }

  // Méthode pour vérifier si l'utilisateur possède un rôle spécifique
  hasRole(role: string): boolean {
    const token = this.getToken();  // Récupérer le token JWT de Keycloak
    if (token) {
      const decodedToken = this.decodeToken(token);  // Décoder le token
      const roles = decodedToken?.realm_access?.roles || [];  // Extraire les rôles depuis `realm_access`
      return roles.includes(role);  // Vérifier si le rôle existe dans le token
    }
    return false; // Retourner false si aucun token n'est trouvé
  }

  // Méthode pour décoder le token JWT
  private decodeToken(token: string): any {
    try {
      const payload = token.split('.')[1]; // Extraire le payload du token
      const decoded = atob(payload); // Décoder le payload en Base64
      return JSON.parse(decoded); // Parser le JSON et retourner l'objet
    } catch (error) {
      console.error('Erreur lors du décodage du token JWT', error);
      return null;
    }
  }

  // Vérifier si l'utilisateur est authentifié
  isLoggedIn(): boolean {
    return this.keycloakService.isAuthenticated();  // Vérifier si l'utilisateur est authentifié via KeycloakService
  }

  // Récupérer le nom d'utilisateur à partir du token
  getUsername(): string {
    return this.keycloakService.getUsername();  // Utiliser la méthode de KeycloakService pour obtenir le nom d'utilisateur
  }

  // Obtenir les rôles de l'utilisateur
  getUserRoles(): string[] {
    const token = this.getToken();  // Récupérer le token JWT de Keycloak
    if (token) {
      const decodedToken = this.decodeToken(token);  // Décoder le token
      return decodedToken?.realm_access?.roles || [];  // Retourner les rôles depuis `realm_access`
    }
    return [];  // Si pas de token, retourner un tableau vide
  }

  // Se déconnecter de Keycloak
  logout() {
    this.keycloakService.logout();  // Appeler la méthode logout de KeycloakService
  }

  // Se connecter via Keycloak
  login() {
    this.keycloakService.login();  // Appeler la méthode login de KeycloakService
  }
  // Méthode pour obtenir le nom de l'utilisateur à partir du token
  
}
