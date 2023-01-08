package ru.kolesnik.springsecuritydemo.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolesnik.springsecuritydemo.auth.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
