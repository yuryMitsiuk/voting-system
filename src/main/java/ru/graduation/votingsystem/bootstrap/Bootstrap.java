package ru.graduation.votingsystem.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.graduation.votingsystem.domain.Dish;
import ru.graduation.votingsystem.domain.Restaurant;
import ru.graduation.votingsystem.domain.Role;
import ru.graduation.votingsystem.domain.User;
import ru.graduation.votingsystem.repositories.DishRepository;
import ru.graduation.votingsystem.repositories.RestaurantRepository;
import ru.graduation.votingsystem.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.stream.Stream;

@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    public Bootstrap(UserRepository userRepository, RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadUsers();
        loadRestaurantsAndDishes();
    }

    private void loadUsers() {
        User user = new User(100000L, "user", "user@gmail.com", "password", Role.ROLE_USER);
        User otheruser = new User(100001L, "otheruser", "otheruser@yahoo.com", "otherpassword", Role.ROLE_USER);
        User admin = new User(100002L, "admin", "admin@yandex.ru", "password", Role.ROLE_USER, Role.ROLE_ADMIN);
        userRepository.save(user);
        userRepository.save(otheruser);
        userRepository.save(admin);
    }

    private void loadRestaurantsAndDishes() {
        Restaurant bellaRosa = new Restaurant(100003L, "Bella Rosa");
        Restaurant perfetto = new Restaurant(100004L, "Perfetto");
        Restaurant veranda = new Restaurant(100005L, "Veranda");
        Restaurant invino = new Restaurant(100006L, "In Vino");
        Restaurant falcone = new Restaurant(100007L, "Falcone");

        Stream.of(bellaRosa, perfetto, veranda, invino, falcone).forEach(restaurantRepository::save);

        Dish dish1_bellaRosa = new Dish(100008L, "Potato Wedges", new BigDecimal("3.79"));
        Dish dish2_bellaRosa = new Dish(100009L, "Baked Beans", new BigDecimal("7.55"));
        Dish dish3_bellaRosa = new Dish(100010L, "Cole Slaw", new BigDecimal("1.88"));
        Stream.of(dish1_bellaRosa, dish2_bellaRosa, dish3_bellaRosa).forEach(dish -> {
            dish.setRestaurant(bellaRosa);
            dishRepository.save(dish);
        });

        Dish dish1_perfetto = new Dish(100011L, "Soft Drink", new BigDecimal("1.69"));
        Dish dish2_perfetto = new Dish(100012L, "Iced Tea", new BigDecimal("2.49"));
        Dish dish3_perfetto = new Dish(100013L, "Lemonade", new BigDecimal("3.49"));
        Dish dish4_perfetto = new Dish(100014L, "Strawberry Lemonade", new BigDecimal("4.15"));
        Stream.of(dish1_perfetto, dish2_perfetto, dish3_perfetto, dish4_perfetto).forEach(dish -> {
            dish.setRestaurant(perfetto);
            dishRepository.save(dish);
        });
    }
}
