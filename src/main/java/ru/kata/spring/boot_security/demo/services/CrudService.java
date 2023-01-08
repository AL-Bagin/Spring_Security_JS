package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrudService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public CrudService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> index() {
        return userRepository.findAll();
    }

    public List<Role> indexRoles() {
        return roleRepository.findAll();
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public User show(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void update(long id, User user) {
        User updateUser = show(id);
        updateUser.setName(user.getName());
        updateUser.setLastName(user.getLastName());
        updateUser.setAge(user.getAge());
        if (!updateUser.getPassword().equals(user.getPassword())) {
            updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        updateUser.setEmail(user.getEmail());
        if (user.getId() != 1) {
            updateUser.setRoles(user.getRoles());
        }
        userRepository.save(updateUser);
    }
}
