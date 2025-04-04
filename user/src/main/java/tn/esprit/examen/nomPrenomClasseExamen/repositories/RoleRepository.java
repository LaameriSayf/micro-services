package tn.esprit.examen.nomPrenomClasseExamen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Role;
import tn.esprit.examen.nomPrenomClasseExamen.entities.TypeRole;

import java.util.Optional;

@Repository

public interface RoleRepository extends JpaRepository<Role, Long> {

Role findByTypeRole(TypeRole typeRole);
Optional<Role> findRoleByTypeRole(TypeRole typeRole);
}
