package ru.kata.spring.boot_security.demo.configs;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;
    private final UserService userService;
    private final RoleService roleService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner dataLoader(){
        return args -> {
            Role role1 = new Role(1L, "ROLE_USER");
            Role role2 = new Role(2L, "ROLE_ADMIN");

            roleService.saveRole(role1);
            roleService.saveRole(role2);

            Set<Role> roleAdmin = new HashSet<>();
            Set<Role> roleUser = new HashSet<>();
            roleUser.add(role1);
            roleAdmin.add(role2);
            User user = new User("user", "user", 14, "user", "user");
            User admin = new User("admin", "admin", 12, "admin", "admin");
            admin.setRoles(roleAdmin);
            user.setRoles(roleUser);
            userService.saveOrUpdate(admin);
            userService.saveOrUpdate(user);
        };
    }
}