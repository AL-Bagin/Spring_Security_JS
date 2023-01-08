package ru.kata.spring.boot_security.demo.model;


import jdk.jfr.Name;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @NotEmpty(message = "Фамилия не должна быть пустой")
    @Size(min = 2, max = 30, message = "Фамилия должна быть от 2 до 30 символов")
    @Column(name = "last_name")
    private String lastName;

    @Min(value = 1, message = "Возраст должен быть от 1 года до 150 лет")
    @Max(value = 150, message = "Возраст должен быть от 1 года до 150 лет")
    @Column(name = "age")
    private int age;

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

    public User(String name, String lastName, int age, String password, String email, Collection<Role> roles) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.password = password;
        this.email = email;
        this.roles = roles;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
