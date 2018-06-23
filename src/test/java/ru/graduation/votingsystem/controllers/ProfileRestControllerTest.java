package ru.graduation.votingsystem.controllers;

import config.TestUtil;
import org.junit.Test;
import org.springframework.http.MediaType;
import ru.graduation.votingsystem.domain.User;
import ru.graduation.votingsystem.json.JsonUtil;
import ru.graduation.votingsystem.to.VoteTo;

import java.time.LocalTime;
import java.util.Arrays;

import static config.DishTestData.DISH1_FALCONE;
import static config.DishTestData.DISH2_FALCONE;
import static config.RestaurantTestData.*;
import static config.TestUtil.userHttpBasic;
import static config.UserTestData.*;
import static config.UserTestData.assertMatch;
import static config.VoteTestData.CREATED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.votingsystem.json.JsonUtil.writeIgnoreProps;

/**
 * Created by yriyMitsiuk on 12.06.2018.
 */
public class ProfileRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileRestController.REST_URL;

    @Test
    public void testGet() throws Exception {
        TestUtil.print(
            mockMvc.perform(get(REST_URL).with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeIgnoreProps(USER)))
        );
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setEmail("updated@email.com");
        updated.setName("updatedName");
        updated.setPassword("updatedPass");
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON).content(jsonWithPassword(updated, "updatedPass")).with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk());

        assertMatch(new User(userRepository.findByEmail("updated@email.com").get()), updated);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL).with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());
        assertMatch(userRepository.findAll(), OTHERUSER, ADMIN);
    }

    @Test
    public void testGetAllRestaurants() throws Exception {
        mockMvc.perform(get(REST_URL+"/restaurants").with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JsonUtil.writeIgnoreProps(Arrays.asList(BELLA_ROSA, PERFETTO, VERANDA, IN_VINO, FALCONE))));
    }

    @Test
    public void testGetMenu() throws Exception {
        mockMvc.perform(get(REST_URL+"/restaurants/"+FALCONE_ID+"/menu").with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JsonUtil.writeIgnoreProps(Arrays.asList(DISH1_FALCONE, DISH2_FALCONE), "restaurant")));
    }

    @Test
    public void testVote() throws Exception {
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            VoteTo voteTo = new VoteTo(FALCONE);
            mockMvc.perform(post(REST_URL + "/vote")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(voteTo)).with(userHttpBasic(USER)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(content().json(JsonUtil.writeIgnoreProps(CREATED)));
        }
    }

    @Test
    public void testVoteTimeExpired() throws Exception {
        if (LocalTime.now().isAfter(LocalTime.of(11, 0))) {
            VoteTo voteTo = new VoteTo(FALCONE);
            mockMvc.perform(post(REST_URL + "/vote")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(voteTo)).with(userHttpBasic(USER)))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity());
        }
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        User updated = new User(USER);
        updated.setPassword("");
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(updated)).with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testUnauthorized () throws Exception {
        mockMvc.perform(get(REST_URL+"/restaurants"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}