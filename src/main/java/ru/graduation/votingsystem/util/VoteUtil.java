package ru.graduation.votingsystem.util;

import ru.graduation.votingsystem.domain.Vote;
import ru.graduation.votingsystem.domain.VoteIdentity;
import ru.graduation.votingsystem.to.VoteTo;

import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {
    public static Vote createVoteFromVoteTo(VoteTo voteTo) {
        return new Vote(new VoteIdentity(), voteTo.getRestaurant());
    }

    public static VoteTo asTo(Vote vote){
        return new VoteTo(vote.getRestaurant(), vote.getIdentity().getVoteDate());
    }

    public static List<VoteTo> asListTo(List<Vote> votes) {
        return votes.stream().map(VoteUtil::asTo).collect(Collectors.toList());
    }
}
