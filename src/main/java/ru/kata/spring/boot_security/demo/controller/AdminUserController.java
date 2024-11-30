package ru.kata.spring.boot_security.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;


@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminUserController {

    private final UserServiceImpl userService;

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "allUsers";
    }

    @GetMapping("/users/new")
    public String newUser(@RequestParam(name = "id", required = false) Integer id, Model model) {
        validateUserId(id, model);
        return "newUser";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/admin/users";
    }

    @PostMapping("users/delete")
    public String deleteUser(@RequestParam(name = "id") Integer id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    private void validateUserId(Integer id, Model model) {
        if (id != null) {
            model.addAttribute("user", userService.findById(id));
        } else {
            model.addAttribute("user", new User());
        }
    }
}
