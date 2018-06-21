package ru.graduation.votingsystem.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "votes")
public class Vote {

    @EmbeddedId
    private VoteIdentity identity;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "restaurantId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(VoteIdentity identity, @NotNull Restaurant restaurant) {
        this.identity = identity;
        this.restaurant = restaurant;
    }

    public VoteIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(VoteIdentity identity) {
        this.identity = identity;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "identity=" + identity +
                ", restaurant=" + restaurant +
                '}';
    }
}
