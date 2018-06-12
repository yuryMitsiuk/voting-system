package ru.graduation.votingsystem.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.graduation.votingsystem.domain.Restaurant;
import ru.graduation.votingsystem.domain.Role;
import ru.graduation.votingsystem.domain.User;
import ru.graduation.votingsystem.repositories.RestaurantRepository;
import ru.graduation.votingsystem.repositories.UserRepository;

@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public Bootstrap(UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadUsers();
        loadRestaurants();
    }

    private void loadUsers() {
        User user = new User(100000L, "user", "user@gmail.com", "password", Role.ROLE_USER);
        User otheruser = new User(100001L, "otheruser", "otheruser@yahoo.com", "otherpassword", Role.ROLE_USER);
        User admin = new User(100002L, "admin", "admin@yandex.ru", "password", Role.ROLE_USER, Role.ROLE_ADMIN);
        userRepository.save(user);
        userRepository.save(otheruser);
        userRepository.save(admin);
    }

    private void loadRestaurants() {
        Restaurant bellaRosa = new Restaurant(100003L, "Bella Rosa");
        Restaurant perfetto = new Restaurant(100004L, "Perfetto");
        Restaurant veranda = new Restaurant(100005L, "Veranda");
        Restaurant invino = new Restaurant(100006L, "In Vino");
        Restaurant falcone = new Restaurant(100007L, "Falcone");

        restaurantRepository.save(bellaRosa);
        restaurantRepository.save(perfetto);
        restaurantRepository.save(veranda);
        restaurantRepository.save(invino);
        restaurantRepository.save(falcone);
    }
}
