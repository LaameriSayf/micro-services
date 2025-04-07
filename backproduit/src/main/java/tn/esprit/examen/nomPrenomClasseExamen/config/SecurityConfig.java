package tn.esprit.examen.nomPrenomClasseExamen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)  // Active @PreAuthorize
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Définir les règles d'accès
                .authorizeRequests()
                .requestMatchers("/api/produits/**").hasRole("admin")  // Exemple : accès réservé aux utilisateurs avec le rôle 'admin'
                .anyRequest().authenticated()
                .and()
                // Configurer le serveur de ressources OAuth2 pour utiliser JWT
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());

        // Active CORS et désactive CSRF (pour une API REST sécurisée par JWT)
        http.cors().and().csrf().disable();

        return http.build();
    }

    // Convertisseur personnalisé pour extraire les rôles depuis le JWT
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this::extractAuthorities);
        return converter;
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // Récupère le claim "realm_access"
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            List<String> roles = (List<String>) realmAccess.get("roles");
            // Pour chaque rôle, ajoute une autorité avec le préfixe "ROLE_"
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        }
        return authorities;
    }

    // Bean pour décoder le JWT en utilisant le JWK Set de Keycloak
    @Bean
    public JwtDecoder jwtDecoder() {
        // Assure-toi que l'URL ci-dessous correspond exactement à ton serveur Keycloak et ton realm.
        return NimbusJwtDecoder.withJwkSetUri("http://localhost:8080/realms/Authentication/protocol/openid-connect/certs")
                .build();
    }

    // Configuration CORS pour autoriser les requêtes depuis ton frontend (par exemple, Angular)
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.ORIGIN,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT,
                HttpHeaders.AUTHORIZATION
        ));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    // Exemple d'Bean pour l'audit (si nécessaire)
    @Bean
    public AuditorAware<String> auditorAware() {
        return new ApplicationAuditAware();
    }
}
