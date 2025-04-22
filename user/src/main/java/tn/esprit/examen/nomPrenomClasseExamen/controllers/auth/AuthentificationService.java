package tn.esprit.examen.nomPrenomClasseExamen.controllers.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Security.JwtService;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Role;
import tn.esprit.examen.nomPrenomClasseExamen.entities.TypeRole;
import tn.esprit.examen.nomPrenomClasseExamen.entities.User;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.RoleRepository;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.UserRepository;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
public class AuthentificationService {
   // @Autowired
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
   // @Autowired
    private RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        Optional<Role> clientRole = roleRepository.findRoleByTypeRole(TypeRole.CLIENT);
        if (clientRole.isEmpty()) {
            Role newClientRole = new Role();
            newClientRole.setTypeRole(TypeRole.CLIENT);
            roleRepository.save(newClientRole);
            clientRole = Optional.of(newClientRole);
        }

        var user = User.builder()
                .nom(request.getFirstname())
                .prenom(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(clientRole.get()))
                .build();

        repository.save(user);
        var roleNames = user.getRoles().stream()
                .map(Role::getTypeRole)
                .map(Enum::name)
                .toList();

        // Create claims
        var claims = Map.of(
                "idUser", user.getId(),
                "roles", roleNames
        );

        var jwtToken = jwtService.generateToken(claims, user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()


                )
        );
        var user=repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken=jwtService.generateToken(Collections.emptyMap(), user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
