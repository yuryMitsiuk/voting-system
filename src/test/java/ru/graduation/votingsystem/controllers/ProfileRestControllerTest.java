package ru.graduation.votingsystem.controllers;

import config.TestUtil;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import ru.graduation.votingsystem.domain.User;
import ru.graduation.votingsystem.json.JsonUtil;

import java.util.Arrays;

import static config.RestaurantTestData.*;
import static config.UserTestData.*;
import static config.UserTestData.assertMatch;
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
            mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeIgnoreProps(USER)))
        );
    }

    @Test
    @DirtiesContext
    public void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setEmail("updated@email.com");
        updated.setName("updatedName");
        updated.setPassword("updatedPass");
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());

        assertMatch(new User(userRepository.findByEmail("updated@email.com")), updated);
    }

    @Test
    @DirtiesContext
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL))
                .andExpect(status().isNoContent());
        assertMatch(userRepository.findAll(), OTHERUSER, ADMIN);
    }

    @Test
    public void getAllRestaurants() throws Exception {
        mockMvc.perform(get(REST_URL+"/restaurants"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JsonUtil.writeIgnoreProps(Arrays.asList(BELLA_ROSA, PERFETTO, VERANDA, IN_VINO, FALCONE))));
    }
}