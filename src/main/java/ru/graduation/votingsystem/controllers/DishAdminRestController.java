package ru.graduation.votingsystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.votingsystem.domain.Dish;
import ru.graduation.votingsystem.repositories.DishRepository;
import ru.graduation.votingsystem.repositories.RestaurantRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by yriyMitsiuk on 13.06.2018.
 */
@RestController
@RequestMapping(RestaurantAdminRestController.REST_URL)
public class DishAdminRestController {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public DishAdminRestController(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping(value = "/{id}/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAllByRestaurant(@PathVariable Long id) {
        return dishRepository.findAllByRestaurantIdAndAndDate(id, LocalDate.now());
    }

    @GetMapping(value = "/{id}/dishes/{dish_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Dish> get(@PathVariable Long id, @PathVariable Long dish_id) {
        return dishRepository.findByIdAndRestaurantId(dish_id, id);
    }

    @PostMapping(value = "/{id}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Dish> create(@PathVariable Long id, @RequestBody Dish dish) {
        dish.setRestaurant(restaurantRepository.getOne(id));
        Dish created = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RestaurantAdminRestController.REST_URL+"/{id}/dishes/{dish_id}")
                .buildAndExpand(id, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Dish> update(@PathVariable Long id, @RequestBody Dish dish) {
        Dish updated = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RestaurantAdminRestController.REST_URL+"/{id}/dishes/{dish_id}")
                .buildAndExpand(id, updated.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(updated);
    }

    @DeleteMapping(value = "/{id}/dishes/{dish_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @PathVariable Long dish_id) {
        dishRepository.removeByIdAndRestaurantId(dish_id, id);
    }
}
