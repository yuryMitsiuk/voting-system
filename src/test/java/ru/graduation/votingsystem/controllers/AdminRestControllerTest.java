package ru.graduation.votingsystem.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.graduation.votingsystem.VotingSystemApplication;
import ru.graduation.votingsystem.bootstrap.Bootstrap;
import ru.graduation.votingsystem.domain.Role;
import ru.graduation.votingsystem.domain.User;
import ru.graduation.votingsystem.json.JsonUtil;
import ru.graduation.votingsystem.repositories.UserRepository;

import javax.annotation.PostConstruct;

import java.util.Arrays;

import static config.TestUtil.readFromJson;
import static config.UserTestData.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.votingsystem.json.JsonUtil.writeIgnoreProps;

/**
 * Created by yriyMitsiuk on 12.06.2018.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = VotingSystemApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminRestControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + '/';

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeIgnoreProps(Arrays.asList(USER, OTHERUSER, ADMIN))));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL+USER_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeIgnoreProps(USER)));
    }

    @Test
    public void testCreate() throws Exception {
        User expected = new User(null, "new", "new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());

        User returned = readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(userRepository.findAll(), USER, OTHERUSER, ADMIN, expected);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL+USER_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userRepository.findAll(), OTHERUSER, ADMIN);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setEmail("updated@gmail.com");
        updated.setVotedToday(true);
        updated.setPassword("updated");
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isCreated());
        assertMatch(userRepository.findById(USER_ID).get(), updated);
    }

    @Test
    public void testGetByMail() throws Exception {
        mockMvc.perform(get(REST_URL+"by?email="+USER.getEmail()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(writeIgnoreProps(USER)));
    }
}