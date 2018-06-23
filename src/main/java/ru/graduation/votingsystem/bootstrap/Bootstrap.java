package ru.graduation.votingsystem.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.graduation.votingsystem.domain.*;
import ru.graduation.votingsystem.repositories.DishRepository;
import ru.graduation.votingsystem.repositories.RestaurantRepository;
import ru.graduation.votingsystem.repositories.UserRepository;
import ru.graduation.votingsystem.repositories.VoteRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final VoteRepository voteRepository;
    private final PasswordEncoder encoder;

    public Bootstrap(UserRepository userRepository, RestaurantRepository restaurantRepository, DishRepository dishRepository, VoteRepository voteRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
        this.voteRepository = voteRepository;
        this.encoder = encoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadUsers();
        loadRestaurantsAndDishes();
        loadVotes();
    }

    private void loadUsers() {
        User user = new User(100000L, "user", "user@gmail.com", encoder.encode("password"), Role.ROLE_USER);
        User otheruser = new User(100001L, "otheruser", "otheruser@yahoo.com", encoder.encode("otherpassword"), Role.ROLE_USER);
        User admin = new User(100002L, "admin", "admin@yandex.ru", encoder.encode("password"), Role.ROLE_USER, Role.ROLE_ADMIN);
        Stream.of(user, otheruser, admin).forEachOrdered(userRepository::save);
    }

    private void loadRestaurantsAndDishes() {
        Restaurant bellaRosa = new Restaurant(100003L, "Bella Rosa");
        Restaurant perfetto = new Restaurant(100004L, "Perfetto");
        Restaurant veranda = new Restaurant(100005L, "Veranda");
        Restaurant invino = new Restaurant(100006L, "In Vino");
        Restaurant falcone = new Restaurant(100007L, "Falcone");
        Stream.of(bellaRosa, perfetto, veranda, invino, falcone).forEachOrdered(restaurantRepository::save);

        Dish dish1_bellaRosa = new Dish(100008L, "Potato Wedges", new BigDecimal("3.79"));
        Dish dish2_bellaRosa = new Dish(100009L, "Baked Beans", new BigDecimal("7.55"));
        Dish dish3_bellaRosa = new Dish(100010L, "Cole Slaw", new BigDecimal("1.88"), LocalDate.of(2018, 5, 10));
        Stream.of(dish1_bellaRosa, dish2_bellaRosa, dish3_bellaRosa).peek(dish -> dish.setRestaurant(bellaRosa))
                .forEachOrdered(dishRepository::save);

        Dish dish1_perfetto = new Dish(100011L, "Soft Drink", new BigDecimal("1.69"));
        Dish dish2_perfetto = new Dish(100012L, "Iced Tea", new BigDecimal("2.49"));
        Dish dish3_perfetto = new Dish(100013L, "Lemonade", new BigDecimal("3.49"));
        Dish dish4_perfetto = new Dish(100014L, "Strawberry Lemonade", new BigDecimal("4.15"), LocalDate.of(2018, 5, 10));
        Stream.of(dish1_perfetto, dish2_perfetto, dish3_perfetto, dish4_perfetto).peek(dish -> dish.setRestaurant(perfetto))
                .forEachOrdered(dishRepository::save);

        Dish dish1_veranda = new Dish(100015L, "Оnion soup", new BigDecimal("1.15"));
        Dish dish2_veranda = new Dish(100016L, "Tomato soup", new BigDecimal("2.05"));
        Dish dish3_veranda = new Dish(100017L, "Mushroom cream soup", new BigDecimal("2.75"));
        Dish dish4_veranda = new Dish(100018L, "Chicken broth with eggs and croutons", new BigDecimal("4.05"), LocalDate.of(2018, 5, 10));
        Dish dish5_veranda = new Dish(100019L, "Garlic bread", new BigDecimal("0.35"), LocalDate.of(2018, 5, 10));
        Stream.of(dish1_veranda, dish2_veranda, dish3_veranda, dish4_veranda, dish5_veranda).peek(dish -> dish.setRestaurant(veranda))
                .forEachOrdered(dishRepository::save);

        Dish dish1_invino = new Dish(100020L, "Grilled vegetables", new BigDecimal("2.87"));
        Dish dish2_invino = new Dish(100021L, "Omelette", new BigDecimal("1.17"));
        Dish dish3_invino = new Dish(100022L, "yesterday's Grilled vegetables", new BigDecimal("2.87"), LocalDate.of(2018, 5, 10));
        Dish dish4_invino = new Dish(100023L, "yesterday's Omelette", new BigDecimal("1.17"), LocalDate.of(2018, 5, 15));
        Stream.of(dish1_invino, dish2_invino, dish3_invino, dish4_invino).peek(dish -> dish.setRestaurant(invino)).forEachOrdered(dishRepository::save);

        Dish dish1_falcone = new Dish(100024L, "Pasta Carbonara", new BigDecimal("5.71"));
        Dish dish2_falcone = new Dish(100025L, "Risotto", new BigDecimal("6.02"));
        Dish dish3_falcone = new Dish(100026L, "yesterday's Pasta Carbonara", new BigDecimal("5.71"), LocalDate.of(2018, 5, 10));
        Dish dish4_falcone = new Dish(100027L, "yesterday's Risotto", new BigDecimal("6.02"), LocalDate.of(2018, 5, 10));
        Stream.of(dish1_falcone, dish2_falcone, dish3_falcone, dish4_falcone).peek(dish -> dish.setRestaurant(falcone)).forEachOrdered(dishRepository::save);
    }

    private void loadVotes() {
        Vote vote1 = new Vote(new VoteIdentity(userRepository.getOne(100000L)), restaurantRepository.getOne(100003L));
        Vote vote2 = new Vote(new VoteIdentity(userRepository.getOne(100001L)), restaurantRepository.getOne(100003L));
        Vote vote3 = new Vote(new VoteIdentity(userRepository.getOne(100002L)), restaurantRepository.getOne(100003L));
        Vote vote4 = new Vote(new VoteIdentity(userRepository.getOne(100000L), LocalDate.of(2018, 5, 10)), restaurantRepository.getOne(100003L));
        Stream.of(vote1, vote2, vote3, vote4).forEach(voteRepository::save);
    }
}
