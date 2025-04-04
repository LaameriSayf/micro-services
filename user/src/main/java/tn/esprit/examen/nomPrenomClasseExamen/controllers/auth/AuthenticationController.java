package tn.esprit.examen.nomPrenomClasseExamen.controllers.auth;




import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.Security.JwtService;
import tn.esprit.examen.nomPrenomClasseExamen.entities.User;
import tn.esprit.examen.nomPrenomClasseExamen.services.UserService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api/auth/user")

@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    UserService us;

    private final AuthentificationService service;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }
    @GetMapping("/getall")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = us.getall();
        System.out.println("Réponse JSON : " + users);
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){us.delete(id);}
    @PostMapping("/add")
    public void add(@RequestBody User u){us.add(u);}
    @GetMapping("/count")
    public Long count(){return us.countUser();}
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Invalid email or password");
        }
    }
    @GetMapping("/getnom")
    public ResponseEntity<String> getNom(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            String nom = jwtService.extractUsername(token); // Assuming this method extracts the name
            return ResponseEntity.ok(nom); // Ensure the response is a plain text string or JSON
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error extracting nom from token");
        }
    }
    @GetMapping("/getimage")
    public ResponseEntity<?> getImage(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            String image = jwtService.extractUsername(token);

            return ResponseEntity.ok(image);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

}
