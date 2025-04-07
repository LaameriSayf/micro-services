import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';
import { UserProfile } from './user-profile';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {
  private keycloakAuth: any;
  private _profile: UserProfile | undefined;
  private initOptions = {
    onLoad: 'check-sso', // Change from 'login-required' to allow public access
    checkLoginIframe: false,
    silentCheckSsoRedirectUri: window.location.origin + '/assets/silent-check-sso.html'
  };

  constructor(private router: Router) {}

  async init(): Promise<boolean> {
    const config = {
      url: 'http://localhost:8080',
      realm: 'Authentication',
      clientId: 'bsn'
    };

    this.keycloakAuth = new (Keycloak as any)(config);

    try {
      const authenticated = await this.keycloakAuth.init(this.initOptions);
      
      if (authenticated) {
        console.log("User is authenticated");
        this._profile = await this.keycloakAuth.loadUserProfile();
        
        // Only redirect to dashboard if coming from login
        if (this.router.url === '/login') {
          this.router.navigate(['/dashboard']);
        }
      }
      
      return authenticated;
    } catch (error) {
      console.error('Keycloak initialization failed:', error);
      return false;
    }
  }

  get profile(): UserProfile | undefined {
    return this._profile;
  }

  getToken(): string | undefined {
    return this.keycloakAuth?.token;
  }

  isLoggedIn(): boolean {
    return this.keycloakAuth?.authenticated || false;
  }

  getUsername(): string {
    return this.keycloakAuth?.tokenParsed?.preferred_username || '';
  }

  isAuthenticated(): boolean {
    return this.keycloakAuth?.authenticated || false;
  }

  getUserRoles(): string[] {
    if (!this.keycloakAuth?.tokenParsed) return [];
    return this.keycloakAuth.tokenParsed.realm_access?.roles || [];
  }

  hasRole(role: string): boolean {
    return this.getUserRoles().includes(role);
  }

  async login(redirectUri?: string): Promise<void> {
    await this.keycloakAuth.login({
      redirectUri: redirectUri || window.location.origin
    });
  }

  async logout(redirectUri?: string): Promise<void> {
    await this.keycloakAuth.logout({
      redirectUri: redirectUri || window.location.origin
    });
  }

  async register(redirectUri?: string): Promise<void> {
    await this.keycloakAuth.register({
      redirectUri: redirectUri || window.location.origin
    });
  }

  getAccountManagementUrl(): string {
    return this.keycloakAuth.createAccountUrl();
  }

  updateToken(minValidity = 5): Promise<boolean> {
    return new Promise((resolve, reject) => {
      if (!this.keycloakAuth) {
        reject(false);
        return;
      }

      this.keycloakAuth.updateToken(minValidity)
        .then((refreshed: boolean) => {
          resolve(refreshed);
        })
        .catch(() => {
          reject(false);
        });
    });
  }
}