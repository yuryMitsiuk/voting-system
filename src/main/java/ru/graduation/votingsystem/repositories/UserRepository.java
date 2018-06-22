package ru.graduation.votingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.graduation.votingsystem.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
