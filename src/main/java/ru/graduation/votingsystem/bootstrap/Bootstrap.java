package ru.graduation.votingsystem.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.graduation.votingsystem.domain.Role;
import ru.graduation.votingsystem.domain.User;
import ru.graduation.votingsystem.repositories.UserRepository;

@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;

    public Bootstrap(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadUsers();
    }

    public void loadUsers() {
        User user = new User(100000L, "user", "user@gmail.com", "password", Role.ROLE_USER);
        User otheruser = new User(100001L, "otheruser", "otheruser@yahoo.com", "otherpassword", Role.ROLE_USER);
        User admin = new User(100002L, "admin", "admin@yandex.ru", "password", Role.ROLE_USER, Role.ROLE_ADMIN);
        userRepository.save(user);
        userRepository.save(otheruser);
        userRepository.save(admin);
    }
}
