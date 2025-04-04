package tn.esprit.examen.nomPrenomClasseExamen.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Role;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.RoleRepository;

@Service

public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public Role ajouterRole(Role role){
        return roleRepository.save(role);
    }

}
