package ru.graduation.votingsystem.to;

import ru.graduation.votingsystem.domain.Restaurant;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public class VoteTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Restaurant restaurant;

    @NotNull
    private LocalDate voteDate = LocalDate.now();

    public VoteTo() {
    }

    public VoteTo(@NotNull Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public VoteTo(@NotNull Restaurant restaurant, @NotNull LocalDate voteDate) {
        this.restaurant = restaurant;
        this.voteDate = voteDate;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(LocalDate voteDate) {
        this.voteDate = voteDate;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "restaurant=" + restaurant +
                '}';
    }
}
