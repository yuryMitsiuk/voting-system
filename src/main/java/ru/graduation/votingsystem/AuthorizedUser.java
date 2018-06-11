package ru.graduation.votingsystem;

import ru.graduation.votingsystem.domain.AbstractBaseEntity;

public class AuthorizedUser {
    private static long id = AbstractBaseEntity.START_SEQ;

    public static long id() {
        return id;
    }

    public static void setId(long id) {
        AuthorizedUser.id = id;
    }
}
