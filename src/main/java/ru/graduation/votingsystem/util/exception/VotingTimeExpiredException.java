package ru.graduation.votingsystem.util.exception;

public class VotingTimeExpiredException extends RuntimeException {
    public VotingTimeExpiredException(String s) {
        super(s);
    }
}
