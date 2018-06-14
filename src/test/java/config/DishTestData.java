package config;

import ru.graduation.votingsystem.domain.Dish;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.graduation.votingsystem.domain.AbstractBaseEntity.START_SEQ;

public class DishTestData {

    public static final long DISH1_INVINO_ID = START_SEQ+20;
    public static final long DISH2_INVINO_ID = START_SEQ+21;
    public static final long DISH3_INVINO_ID = START_SEQ+22;
    public static final long DISH4_INVINO_ID = START_SEQ+23;

    public static final long DISH1_FALCONE_ID = START_SEQ+24;
    public static final long DISH2_FALCONE_ID = START_SEQ+25;
    public static final long DISH3_FALCONE_ID = START_SEQ+26;
    public static final long DISH4_FALCONE_ID = START_SEQ+27;

    public static final Dish DISH1_INVINO = new Dish(DISH1_INVINO_ID, "Grilled vegetables", new BigDecimal("2.87"));
    public static final Dish DISH2_INVINO = new Dish(DISH2_INVINO_ID, "Omelette", new BigDecimal("1.17"));
    public static final Dish DISH3_INVINO = new Dish(DISH3_INVINO_ID, "yesterday's Grilled vegetables", new BigDecimal("2.87"), LocalDate.of(2018, 5, 10));
    public static final Dish DISH4_INVINO = new Dish(DISH4_INVINO_ID, "yesterday's Omelette", new BigDecimal("1.17"), LocalDate.of(2018, 5, 15));

    public static final Dish DISH1_FALCONE = new Dish(DISH1_FALCONE_ID, "Pasta Carbonara", new BigDecimal("5.71"));
    public static final Dish DISH2_FALCONE = new Dish(DISH2_FALCONE_ID, "Risotto", new BigDecimal("6.02"));
    public static final Dish DISH3_FALCONE = new Dish(DISH3_FALCONE_ID, "yesterday's Pasta Carbonara", new BigDecimal("5.71"), LocalDate.of(2018, 5, 10));
    public static final Dish DISH4_FALCONE = new Dish(DISH4_FALCONE_ID, "yesterday's Risotto", new BigDecimal("6.02"), LocalDate.of(2018, 5, 10));

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }
}
