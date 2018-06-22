package ru.graduation.votingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.graduation.votingsystem.domain.Restaurant;

import java.util.Optional;

/**
 * Created by yriyMitsiuk on 12.06.2018.
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByName(String name);
}
