declare module 'keycloak-js' {
    export interface KeycloakConfig {
      url: string;
      realm: string;
      clientId: string;
    }
    
    export interface KeycloakInstance {
      init(options?: any): Promise<boolean>;
      logout(options?: any): void;
      authenticated?: boolean;
      token?: string;
      tokenParsed?: any;
      createLoginUrl(options?: any): string;
      createLogoutUrl(options?: any): string;
      createRegisterUrl(options?: any): string;
      // Ajoute d'autres méthodes ou propriétés nécessaires pour ton projet
    }
    
    // Déclare que "Keycloak" est une fonction qui retourne une instance Keycloak
    export default function Keycloak(config?: string | KeycloakConfig): KeycloakInstance;
  }
  