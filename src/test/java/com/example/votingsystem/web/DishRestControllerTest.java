package com.example.votingsystem.web;

import com.example.votingsystem.model.Dish;
import com.example.votingsystem.repository.DishRepository;
import com.example.votingsystem.to.DishTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.votingsystem.TestData.*;
import static com.example.votingsystem.TestUtil.*;
import static com.example.votingsystem.UserTestData.ADMIN;
import static com.example.votingsystem.web.json.JsonUtil.writeValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DishRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = DishRestController.REST_URL;

    @Autowired
    private DishRepository dishRepository;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + 100012)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + 100012)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(dishRepository.findAll(), DISH2, DISH3, DISH4, DISH5, DISH6);
    }

    @Test
    public void testCreate() throws Exception {
        Dish expected = new Dish(null, "New", 50000L);
        ResultActions action = mockMvc.perform(post(REST_URL + "?menuId=100009")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(writeValue(expected)))
                .andExpect(status().isCreated());

        Dish returned = readFromJson(action, Dish.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(dishRepository.findAll(), DISH, DISH2, DISH3, DISH4, DISH5, DISH6, returned);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH, DISH2, DISH3, DISH4, DISH5, DISH6));
    }

    @Test
    public void testUpdate() throws Exception {
        Dish expected = new Dish(100012, "New", 50000L);
        DishTo expectedTo = createToFromDish(expected);
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(writeValue(expectedTo)))
                .andExpect(status().isNoContent());

        assertMatch(dishRepository.getById(100012), expected);
    }
}