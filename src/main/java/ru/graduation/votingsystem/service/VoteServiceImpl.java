package ru.graduation.votingsystem.service;

import org.springframework.stereotype.Service;
import ru.graduation.votingsystem.AuthorizedUser;
import ru.graduation.votingsystem.domain.Vote;
import ru.graduation.votingsystem.repositories.UserRepository;
import ru.graduation.votingsystem.repositories.VoteRepository;
import ru.graduation.votingsystem.to.VoteTo;
import ru.graduation.votingsystem.util.VoteUtil;
import ru.graduation.votingsystem.util.exception.VotingTimeExpiredException;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;


@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    public VoteServiceImpl(VoteRepository voteRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public VoteTo save(VoteTo voteTo) {
        Objects.requireNonNull(voteTo);
        Vote vote = VoteUtil.createVoteFromVoteTo(voteTo);
        vote.getIdentity().setUser(userRepository.getOne(AuthorizedUser.id()));
        if (voteRepository.existsById(vote.getIdentity()) && LocalTime.now().isAfter(LocalTime.of(11, 0)))
            throw new VotingTimeExpiredException("Today the voting time has expired.");
        return VoteUtil.asTo(voteRepository.save(vote));
    }

    @Override
    public List<VoteTo> getAllForUser(long userId) {
        return VoteUtil.asListTo(voteRepository.findAllOfUser(userId));
    }

    @Override
    public List<Vote> getAllForRestaurant(long restaurantId) {
        return voteRepository.findAllOfRestaurants(restaurantId);
    }
}
