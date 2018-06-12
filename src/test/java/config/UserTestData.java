package config;

import org.assertj.core.api.Assertions;
import ru.graduation.votingsystem.domain.Role;
import ru.graduation.votingsystem.domain.User;

import java.util.Arrays;

import static ru.graduation.votingsystem.domain.AbstractBaseEntity.START_SEQ;

/**
 * Created by yriyMitsiuk on 12.06.2018.
 */
public class UserTestData {
    public static final long USER_ID = START_SEQ;
    public static final long OTHERUSER_ID = START_SEQ+1;
    public static final long ADMIN_ID = START_SEQ+2;

    public static final User USER = new User(USER_ID, "user", "user@gmail.com", "password", Role.ROLE_USER);
    public static final User OTHERUSER = new User(OTHERUSER_ID, "otheruser", "otheruser@yahoo.com", "otherpassword", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "admin", "admin@yandex.ru", "password", Role.ROLE_USER, Role.ROLE_ADMIN);

    public static void assertMatch(User actual, User expected) {
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        Assertions.assertThat(actual).isEqualTo(expected);
    }

}
