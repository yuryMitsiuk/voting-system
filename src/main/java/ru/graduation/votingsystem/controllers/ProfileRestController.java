package ru.graduation.votingsystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.graduation.votingsystem.AuthorizedUser;
import ru.graduation.votingsystem.domain.Dish;
import ru.graduation.votingsystem.domain.Restaurant;
import ru.graduation.votingsystem.domain.User;
import ru.graduation.votingsystem.repositories.DishRepository;
import ru.graduation.votingsystem.repositories.RestaurantRepository;
import ru.graduation.votingsystem.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController {

    static final String REST_URL = "/rest/profile";

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    public ProfileRestController(UserRepository userRepository, RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return userRepository.findById(AuthorizedUser.id()).orElse(null);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user) {
        if (user.getId() == AuthorizedUser.id())
            userRepository.save(user);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete() {
        userRepository.deleteById(AuthorizedUser.id());
    }

    @GetMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @GetMapping(value = "/restaurants/{id}/menu")
    public List<Dish> getMenu(@PathVariable Long id) {
        return dishRepository.findAllByRestaurantIdAndDate(id, LocalDate.now());
    }
}
