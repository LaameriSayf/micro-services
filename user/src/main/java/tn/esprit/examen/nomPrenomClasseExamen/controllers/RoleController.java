package tn.esprit.examen.nomPrenomClasseExamen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Role;
import tn.esprit.examen.nomPrenomClasseExamen.services.RoleService;

@RestController
@RequestMapping("/api/auth/roles")

public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/ajouterRole")
    public Role ajouterRole(@RequestBody Role role){
        return roleService.ajouterRole(role);
    }

    }
