package ru.geekbrains.vklimovich.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.vklimovich.springmvc.auth.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
