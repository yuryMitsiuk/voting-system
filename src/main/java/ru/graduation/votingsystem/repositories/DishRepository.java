package ru.graduation.votingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.votingsystem.domain.Dish;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by yriyMitsiuk on 13.06.2018.
 */
public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findAllByRestaurantIdAndDate(Long id, @NotNull LocalDate date);

    Optional<Dish> findByIdAndRestaurantId(Long id, Long restaurant_id);

    @Transactional
    void removeByIdAndRestaurantId(Long id, Long restaurant_id);

     List<Dish> findAllByRestaurantId(Long id);

     List<Dish> findAllByRestaurantIdAndDateBetween(Long restaurant_id, @NotNull LocalDate date, @NotNull LocalDate date2);
}
