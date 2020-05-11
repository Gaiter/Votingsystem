package com.example.votingsystem.web;

import com.example.votingsystem.model.Restaurant;
import com.example.votingsystem.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.votingsystem.TestData.*;
import static com.example.votingsystem.TestUtil.readFromJson;
import static com.example.votingsystem.TestUtil.userHttpBasic;
import static com.example.votingsystem.UserTestData.ADMIN;
import static com.example.votingsystem.web.json.JsonUtil.writeValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantRestController.REST_URL;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void testGetRestaurant() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + 100003)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT));
    }

    @Test
    public void testDeleteRestaurant() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + 100003)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantRepository.findAll(), RESTAURANT2, RESTAURANT3);
    }

    @Test
    public void testCreateRestaurant() throws Exception {
        Restaurant expected = new Restaurant(null, "New");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(writeValue(expected)))
                .andExpect(status().isCreated());

        Restaurant returned = readFromJson(action, Restaurant.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(restaurantRepository.findAll(), RESTAURANT, RESTAURANT2, RESTAURANT3, expected);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT, RESTAURANT2, RESTAURANT3));
    }
}