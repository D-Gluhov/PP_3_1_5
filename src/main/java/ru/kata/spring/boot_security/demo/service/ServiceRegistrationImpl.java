package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@Service
@AllArgsConstructor
public class ServiceRegistrationImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Transactional
    public void registerUser(User user) {
        userRepository.save(settingFieldsOfUser(user));
    }

    private User settingFieldsOfUser(User user) {
        user.addRole(roleRepository.findByRoleName("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}
