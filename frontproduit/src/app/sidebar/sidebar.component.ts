import { Component } from '@angular/core';
import { KeycloakService } from '../keycloak/keycloak.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {

  isCollapsed = false;
  userName: string = '';
  userRole: any;

  constructor(private keycloak: KeycloakService,public authService: AuthService) {

    this.userName = this.authService.getUsername(); // Récupère le nom de l'utilisateur
    this.userRole = this.authService.getUserRoles(); // Récupère le rôle de l'utilisateur

  }

  
  toggleSidebar() {
    this.isCollapsed = !this.isCollapsed;
  }

  logout() {
    this.keycloak.logout();
  }
  
}
