package ru.graduation.votingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.graduation.votingsystem.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
