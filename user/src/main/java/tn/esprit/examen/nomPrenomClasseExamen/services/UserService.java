package tn.esprit.examen.nomPrenomClasseExamen.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Role;
import tn.esprit.examen.nomPrenomClasseExamen.entities.TypeRole;
import tn.esprit.examen.nomPrenomClasseExamen.entities.User;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.RoleRepository;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;
@Service
public class UserService{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository ur;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> getall() {
        List<User> lu=ur.findAll();
        return lu;
    }


    public void add(User user) {
        ur.save(user);
    }


    public void delete(Long id) {
        ur.deleteById(id);
    }


    public void update(User user) {
        ur.save(user);
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public Long countUser(){
        return ur.count();
    }

    public void assignRoleToUser(Long id, TypeRole typeRole) {
        User user = ur.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findByTypeRole(typeRole);


        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            ur.save(user);
        } else {
            throw new IllegalArgumentException("User already has this role!");
        }
    }


}
