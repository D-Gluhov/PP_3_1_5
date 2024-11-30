package ru.kata.spring.boot_security.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.ServiceRegistrationImpl;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private final ServiceRegistrationImpl serviceRegistration;

    @GetMapping("/registration")
    public String registrationNewUser(Model model) {
        model.addAttribute("user", new User());
        return "registrationNewUser";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") User user) {
        serviceRegistration.registerUser(user);
        return "redirect:/login";
    }
}
