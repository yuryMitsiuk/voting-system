package ru.graduation.votingsystem.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.votingsystem.domain.Dish;
import ru.graduation.votingsystem.repositories.DishRepository;
import ru.graduation.votingsystem.repositories.RestaurantRepository;
import ru.graduation.votingsystem.util.exception.NotFoundException;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.graduation.votingsystem.util.Util.orElse;
import static ru.graduation.votingsystem.util.ValidationUtil.checkNotFound;

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

    @GetMapping(value = "/{id}/menu", produces = APPLICATION_JSON_VALUE)
    public List<Dish> getAllByRestaurant(@PathVariable Long id,
                                         @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        checkNotFound(restaurantRepository.existsById(id), " id = "+ id);
        return dishRepository.findAllByRestaurantIdAndDate(id, orElse(date, LocalDate.now()));
    }

    @GetMapping(value = "/{id}/menu/{dish_id}", produces = APPLICATION_JSON_VALUE)
    public Dish getByRestaurant(@PathVariable Long id, @PathVariable Long dish_id) {
        checkNotFound(restaurantRepository.existsById(id), " id = "+ id);
        return dishRepository.findByIdAndRestaurantId(dish_id, id).orElseThrow(() -> new NotFoundException("Not found entity with id = " + id));
    }

    @PostMapping(value = "/{id}/menu", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Dish> create(@PathVariable Long id, @Valid @RequestBody Dish dish) {
        checkNotFound(restaurantRepository.existsById(id), " id = "+ id);
        dish.setRestaurant(restaurantRepository.getOne(id));
        Dish created = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RestaurantAdminRestController.REST_URL+"/{id}/menu/{dish_id}")
                .buildAndExpand(id, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}/menu", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> update(@PathVariable Long id, @Valid @RequestBody Dish dish) {
        dish.setRestaurant(restaurantRepository.getOne(id));
        Dish updated = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RestaurantAdminRestController.REST_URL+"/{id}/menu/{dish_id}")
                .buildAndExpand(id, updated.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(updated);
    }

    @DeleteMapping(value = "/{id}/menu/{dish_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @PathVariable Long dish_id) {
        checkNotFound(restaurantRepository.existsById(id), " id = "+ id);
        checkNotFound(dishRepository.existsById(dish_id), " dish_id = "+ dish_id);
        dishRepository.removeByIdAndRestaurantId(dish_id, id);
    }

    @GetMapping(value = "/{id}/menu/history", produces = APPLICATION_JSON_VALUE)
    public List<Dish> getHistory(@PathVariable Long id) {
        checkNotFound(restaurantRepository.existsById(id), " id = "+ id);
        return dishRepository.findAllByRestaurantId(id);
    }


    @GetMapping(value = "/{id}/menu/filter", produces = APPLICATION_JSON_VALUE)
    public List<Dish> getBetween(@PathVariable Long id,
                                 @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                 @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        checkNotFound(restaurantRepository.existsById(id), " id = "+ id);
        return dishRepository.findAllByRestaurantIdAndDateBetween(id, orElse(from, LocalDate.MIN), orElse(to, LocalDate.MAX));
    }


}
