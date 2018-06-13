package ru.graduation.votingsystem.controllers;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.ResultActions;
import ru.graduation.votingsystem.domain.Restaurant;
import ru.graduation.votingsystem.json.JsonUtil;

import java.util.Arrays;

import static config.RestaurantTestData.*;
import static config.TestUtil.readFromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.votingsystem.json.JsonUtil.writeValue;

/**
 * Created by yriyMitsiuk on 13.06.2018.
 */
public class RestaurantAdminRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantAdminRestController.REST_URL + '/';

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JsonUtil.writeIgnoreProps(Arrays.asList(BELLA_ROSA, PERFETTO, VERANDA, IN_VINO, FALCONE))));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL+VERANDA_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(VERANDA)));
    }

    @Test
    @DirtiesContext
    public void testCreate() throws Exception {
        Restaurant created = new Restaurant("KFC");
        ResultActions action = mockMvc.perform(post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(writeValue(created)))
                .andExpect(status().isCreated());
        Restaurant returned = readFromJson(action, Restaurant.class);
        created.setId(returned.getId());
        assertMatch(restaurantRepository.findAll(), BELLA_ROSA, PERFETTO, VERANDA, IN_VINO, FALCONE, created);
    }

    @Test
    @DirtiesContext
    public void testUpdate() throws Exception {
        Restaurant updated = new Restaurant(BELLA_ROSA);
        updated.setName("KFC");
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON).content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isCreated());
        assertMatch(restaurantRepository.findAll(), updated, PERFETTO, VERANDA, IN_VINO, FALCONE);
    }

    @Test
    @DirtiesContext
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL+VERANDA_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantRepository.findAll(), BELLA_ROSA, PERFETTO, IN_VINO, FALCONE);
    }

    @Test
    public void testGetByName() throws Exception {
        mockMvc.perform(get(REST_URL+"/by?name="+VERANDA.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeValue(VERANDA)));
    }
}