package config;

import ru.graduation.votingsystem.to.VoteTo;

import static config.RestaurantTestData.BELLA_ROSA;
import static config.RestaurantTestData.FALCONE;

/**
 * Created by yriyMitsiuk on 21.06.2018.
 */
public class VoteTestData {
    public static final VoteTo CREATED = new VoteTo(FALCONE);
    public static final VoteTo RETURNED_VOTETO_OTHERUSER = new VoteTo(BELLA_ROSA);
}
