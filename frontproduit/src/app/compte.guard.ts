import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { KeycloakService } from './keycloak/keycloak.service';

export const compteGuard: CanActivateFn = (route, state) => {
  const keycloakService = inject(KeycloakService);
  const router = inject(Router);

  // Vérifie si l'utilisateur est authentifié via Keycloak
  if (!keycloakService.isAuthenticated()) {
    console.warn('⛔ Non authentifié ! Redirection vers /login...');
    router.navigate(['/login']);
    return false;
  }

  // Récupère les rôles de l'utilisateur (méthode à implémenter dans KeycloakService)
  const userRoles = keycloakService.getUserRoles();
  console.log('🔍 Rôles de l’utilisateur :', userRoles);

  // Exemple de contrôle : accès autorisé si l'utilisateur possède le rôle "ADMIN"
  if (userRoles.includes('ADMIN')) {
    console.log('✅ Accès autorisé à ADMIN');
    return true;
  } else {
    console.warn('🚫 Accès refusé ! Redirection vers /bancaireuser...');
    router.navigate(['/bancaireuser']);
    return false;
  }
};
