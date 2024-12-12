package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Transactional
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }
}
