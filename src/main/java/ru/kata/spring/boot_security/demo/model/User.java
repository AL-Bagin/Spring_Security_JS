package ru.kata.spring.boot_security.demo.model;


import jdk.jfr.Name;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 30, message = "Имя должно быть от 2 до 30 символов")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Пароль не должен быть пустым")
    @Size(min = 5, message = "Минимальная длина пароля - 5 символов")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Email не должен быть пустым")
    @Email(message = "Введите корректный email")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "У пользователя должна быть роль")
    @ManyToMany
    private Collection<Role> roles;
    public User() {
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Role> getRoles() { return roles; }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

}
