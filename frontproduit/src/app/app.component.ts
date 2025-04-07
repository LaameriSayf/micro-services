import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from './keycloak/keycloak.service';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontproduit';
  isLoggedIn: boolean = false;
  username: string = '';
  userRoles: string[] = [];

  constructor(private authService: AuthService) {}

  ngOnInit() {
    // Vérifier si l'utilisateur est connecté
    this.isLoggedIn = this.authService.isLoggedIn();
    if (this.isLoggedIn) {
      this.username = this.authService.getUsername();
      this.userRoles = this.authService.getUserRoles();
    }
  }

  // Méthode pour vérifier l'accès en fonction du rôle
  hasAdminRole(): boolean {
    return this.authService.hasRole('admin');
  }

  hasUserRole(): boolean {
    return this.authService.hasRole('client');
  }

  // Méthode de déconnexion
  logout() {
    this.authService.logout();
  }
}
