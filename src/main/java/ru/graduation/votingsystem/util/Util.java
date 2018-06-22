package ru.graduation.votingsystem.util;

/**
 * Created by yriyMitsiuk on 14.06.2018.
 */
public final class Util {

    private Util() {
    }

    public static <T> T orElse(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

}
