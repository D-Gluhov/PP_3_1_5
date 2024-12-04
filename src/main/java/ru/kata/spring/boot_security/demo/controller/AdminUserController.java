package ru.kata.spring.boot_security.demo.controller;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AdminUserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/admin")
    public ResponseEntity<List<User>> startPageForAdmin() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<User> showUser(@RequestParam Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/admin/saveUser")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/admin/deleteUser")
    public ResponseEntity<HttpStatus> deleteUser(@RequestParam Long id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/admin/updateUser")
    public ResponseEntity<HttpStatus> updateUserInfo(@RequestBody @NotNull User user, @RequestParam Long id) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(userService.getUserById(id).getRoles());
        }
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
