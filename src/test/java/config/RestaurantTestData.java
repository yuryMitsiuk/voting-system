package config;

import org.assertj.core.api.Assertions;
import ru.graduation.votingsystem.domain.Restaurant;

import java.util.Arrays;

import static ru.graduation.votingsystem.domain.AbstractBaseEntity.START_SEQ;

/**
 * Created by yriyMitsiuk on 13.06.2018.
 */
public class RestaurantTestData {

    public static final long BELLA_ROSA_ID = START_SEQ+3;
    public static final long PERFETTO_ID = START_SEQ+4;
    public static final long VERANDA_ID = START_SEQ+5;
    public static final long IN_VINO_ID = START_SEQ+6;
    public static final long FALCONE_ID = START_SEQ+7;

    public static final Restaurant BELLA_ROSA = new Restaurant(BELLA_ROSA_ID, "Bella Rosa");
    public static final Restaurant PERFETTO = new Restaurant(PERFETTO_ID, "Perfetto");
    public static final Restaurant VERANDA = new Restaurant(VERANDA_ID, "Veranda");
    public static final Restaurant IN_VINO = new Restaurant(IN_VINO_ID, "In Vino");
    public static final Restaurant FALCONE = new Restaurant(FALCONE_ID, "Falcone");

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
