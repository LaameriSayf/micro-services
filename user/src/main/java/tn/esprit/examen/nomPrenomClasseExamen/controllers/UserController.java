package tn.esprit.examen.nomPrenomClasseExamen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.entities.TypeRole;
import tn.esprit.examen.nomPrenomClasseExamen.services.UserService;

@RestController
@RequestMapping("/api/auth/user")

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/{id}/assign-role")
    public ResponseEntity<String> assignRoleToUser(@PathVariable Long id, @RequestParam TypeRole typeRole) {
        try {
            userService.assignRoleToUser(id, typeRole);
            return ResponseEntity.ok("Role '" + typeRole + "' assigned to user successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
