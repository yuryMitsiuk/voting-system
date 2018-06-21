package ru.graduation.votingsystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.votingsystem.domain.Restaurant;
import ru.graduation.votingsystem.domain.Vote;
import ru.graduation.votingsystem.repositories.RestaurantRepository;
import ru.graduation.votingsystem.service.VoteService;

import java.net.URI;
import java.util.List;

/**
 * Created by yriyMitsiuk on 12.06.2018.
 */
@RestController
@RequestMapping(RestaurantAdminRestController.REST_URL)
public class RestaurantAdminRestController {

    static final String REST_URL = "/rest/admin/restaurants";

    private final RestaurantRepository restaurantRepository;
    private final VoteService voteService;

    public RestaurantAdminRestController(RestaurantRepository restaurantRepository, VoteService voteService) {
        this.restaurantRepository = restaurantRepository;
        this.voteService = voteService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> update(@RequestBody Restaurant restaurant) {
        Restaurant updated = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        restaurantRepository.deleteById(id);
    }

    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getByName(@RequestParam("name") String name) {
        return restaurantRepository.findByName(name);
    }

    @GetMapping("/vote")
    public List<Vote> getAllVoteForRestaurants(@RequestParam("restaurantId") long restaurantId) {
        return voteService.getAllForRestaurant(restaurantId);
    }
}
