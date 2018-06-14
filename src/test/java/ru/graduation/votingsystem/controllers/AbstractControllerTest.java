package ru.graduation.votingsystem.controllers;

import config.TimingRules;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.graduation.votingsystem.VotingSystemApplication;
import ru.graduation.votingsystem.repositories.DishRepository;
import ru.graduation.votingsystem.repositories.RestaurantRepository;
import ru.graduation.votingsystem.repositories.UserRepository;

/**
 * Created by yriyMitsiuk on 13.06.2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = VotingSystemApplication.class)
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

    @ClassRule
    public static ExternalResource summary = TimingRules.SUMMARY;

    @Rule
    public Stopwatch stopwatch = TimingRules.STOPWATCH;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RestaurantRepository restaurantRepository;

    @Autowired
    protected DishRepository dishRepository;
}
