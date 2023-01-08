package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
@Transactional
public class CreateAdmin {

    @PersistenceContext
    EntityManager entityManager;

    @EventListener(ApplicationReadyEvent.class)
    public void createAdmin() {
        if (entityManager.find(User.class, (long)1) == null) {
            entityManager.createNativeQuery("DELETE FROM roles WHERE id = 1").executeUpdate();
            entityManager.createNativeQuery("DELETE FROM roles WHERE id = 2").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO users (id, name, last_name, age, password, email) values (1, 'admin', 'admin', 24, '$2y$10$SL2.FVkvG2PQcbPTY8BOrebt7Ukgf2p0nw53gYl9UmHa1FxS24i0m', 'admin@mail.ru')").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO roles (id, name) value (1, 'ROLE_ADMIN')").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO roles (id, name) value (2, 'ROLE_USER')").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO users_roles (user_id, roles_id) values (1,1)").executeUpdate();
            entityManager.createNativeQuery("INSERT INTO users_roles (user_id, roles_id) values (1,2)").executeUpdate();
        }
    }
}
