package ru.graduation.votingsystem.controllers;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.ResultActions;
import ru.graduation.votingsystem.domain.Dish;

import java.math.BigDecimal;
import java.util.Arrays;

import static config.DishTestData.*;
import static config.RestaurantTestData.FALCONE_ID;
import static config.RestaurantTestData.IN_VINO_ID;
import static config.TestUtil.readFromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.votingsystem.json.JsonUtil.writeIgnoreProps;
import static ru.graduation.votingsystem.json.JsonUtil.writeValue;

public class DishAdminRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantAdminRestController.REST_URL + '/';

    @Test
    public void testGetAllByRestaurant() throws Exception {
        mockMvc.perform(get(REST_URL+FALCONE_ID+"/menu"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeIgnoreProps(Arrays.asList(DISH1_FALCONE, DISH2_FALCONE), "restaurant")));
    }

    @Test
    public void testGetByRestaurant() throws Exception {
        mockMvc.perform(get(REST_URL+IN_VINO_ID+"/menu/"+DISH1_INVINO_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeIgnoreProps(DISH1_INVINO, "restaurant")));
    }

    @Test
    @DirtiesContext
    public void testCreate() throws Exception {
        Dish created = new Dish(null, "Pork chop", new BigDecimal("7.27"));
        ResultActions resultActions = mockMvc.perform(post(REST_URL + IN_VINO_ID + "/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(created)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish returned = readFromJson(resultActions, Dish.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(dishRepository.findAllByRestaurantId(IN_VINO_ID), DISH1_INVINO, DISH2_INVINO, DISH3_INVINO, DISH4_INVINO, created);
    }

    @Test
    @DirtiesContext
    public void testUpdate() throws Exception {
        Dish updated = new Dish(DISH3_FALCONE);
        updated.setName("updated Pasta Carbonara");
        updated.setPrice(new BigDecimal("2.95"));
        mockMvc.perform(put(REST_URL+FALCONE_ID+"/menu")
                .contentType(MediaType.APPLICATION_JSON).content(writeIgnoreProps(updated, "restaurant")))
                .andDo(print())
                .andExpect(status().isCreated());
        assertMatch(dishRepository.findAllByRestaurantId(FALCONE_ID), DISH1_FALCONE, DISH2_FALCONE, updated, DISH4_FALCONE);
    }

    @Test
    @DirtiesContext
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL+IN_VINO_ID+"/menu/"+DISH2_INVINO_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(dishRepository.findAllByRestaurantId(IN_VINO_ID), DISH1_INVINO, DISH3_INVINO, DISH4_INVINO);
    }

    @Test
    public void getHistory() throws Exception {
        mockMvc.perform(get(REST_URL+FALCONE_ID+"/menu/history"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeIgnoreProps(Arrays.asList(DISH1_FALCONE, DISH2_FALCONE, DISH3_FALCONE, DISH4_FALCONE), "restaurant")));
    }

    @Test
    public void getBetween() throws Exception {
        mockMvc.perform(get(REST_URL+FALCONE_ID+"/menu/filter")
                .param("from", String.valueOf(DISH4_FALCONE.getDate().minusDays(1L)))
                .param("to", String.valueOf(DISH4_FALCONE.getDate().plusDays(1L))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeIgnoreProps(Arrays.asList(DISH3_FALCONE, DISH4_FALCONE), "restaurant")));
    }
}