package ru.graduation.votingsystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.graduation.votingsystem.AuthorizedUser;
import ru.graduation.votingsystem.domain.User;
import ru.graduation.votingsystem.repositories.UserRepository;

@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController {

    static final String REST_URL = "/rest/profile";

    private final UserRepository userRepository;

    public ProfileRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return userRepository.findById(AuthorizedUser.id()).orElse(null);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user) {
        if (user.getId() == AuthorizedUser.id())
            userRepository.save(user);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete() {
        userRepository.deleteById(AuthorizedUser.id());
    }

}
