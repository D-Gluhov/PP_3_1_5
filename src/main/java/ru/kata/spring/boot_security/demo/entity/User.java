package ru.kata.spring.boot_security.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;


@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "surName")
    private String surName;
    @Column(name = "age")
    private Integer age;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    public User(String firstName, String surName, Integer age, String username, String password) {
        this.firstName = firstName;
        this.surName = surName;
        this.age = age;
        this.username = username;
        this.password = password;
    }

    public void addRole(Role role) {
        roles = isNull(roles) ? new HashSet<>() : roles;
        role.addUser(this);
        roles.add(role);
    }

    public void removeRole(Role role) {
        if (roles != null) {
            roles.remove(role);
        }
    }

    public boolean isAdmin() {
        return roles.stream()
                .anyMatch(role -> "ROLE_ADMIN".equals(role.getRoleName()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
