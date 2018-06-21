package ru.graduation.votingsystem.service;

import ru.graduation.votingsystem.domain.Vote;
import ru.graduation.votingsystem.domain.VoteIdentity;
import ru.graduation.votingsystem.to.VoteTo;

import java.util.List;

public interface VoteService {
    VoteTo save(VoteTo voteTo);
    List<VoteTo> getAllForUser(long userId);
    List<Vote> getAllForRestaurant(long restaurantId);
}
