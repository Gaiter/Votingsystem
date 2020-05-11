package com.example.votingsystem.web;

import com.example.votingsystem.model.Dish;
import com.example.votingsystem.model.Menu;
import com.example.votingsystem.model.Restaurant;
import com.example.votingsystem.repository.MenuRepository;
import com.example.votingsystem.to.MenuTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static com.example.votingsystem.TestData.*;
import static com.example.votingsystem.TestUtil.*;
import static com.example.votingsystem.UserTestData.ADMIN;
import static com.example.votingsystem.UserTestData.USER;
import static com.example.votingsystem.web.json.JsonUtil.writeValue;
import static java.time.LocalDate.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MenuRestController.REST_URL + '/';

    @Autowired
    private MenuRepository menuRepository;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + "admin/menu/" + 100009)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MENU));
    }

    @Test
    public void testGetWithoutAccess() throws Exception {
        mockMvc.perform(get(REST_URL + "admin/menu/" + 100009)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "admin/menu/" + 100009)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(menuRepository.findAll(), MENU2, MENU3);
    }

    @Test
    public void testCreate() throws Exception {
        Menu expected = new Menu(null, "New", of(2015, Month.MAY, 30));
        ResultActions action = mockMvc.perform(post(REST_URL + "/admin/menu?restaurantId=100003")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(writeValue(expected)))
                .andExpect(status().isCreated());

        Menu returned = readFromJson(action, Menu.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(menuRepository.findAll(), MENU, MENU2, MENU3, expected);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL + "admin/menu")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MENU, MENU2, MENU3));
    }

    @Test
    void update() throws Exception {
        Menu expected = new Menu(100009, "NewLunch", LocalDate.of(2018, 9, 20));
        MenuTo expectedTo = createToFromMenu(expected);
        mockMvc.perform(put(REST_URL + "admin/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(writeValue(expectedTo)))
                .andExpect(status().isNoContent());

        assertMatch(menuRepository.getById(100009), expected);
    }

    @Test
    void getWithDishesAndRestaurant() throws Exception {
        ResultActions action = mockMvc.perform(get(REST_URL + "profile/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER)));

        List<Menu> returned = readFromJsonList(action, Menu.class);
        assertMatch(returned, MENU, MENU2);
        List<Dish> returnedDish = new ArrayList<>();
        for (Menu menu : returned) {
            returnedDish.addAll(menu.getDishes());
        }
        assertMatch(returnedDish, DISH2, DISH, DISH4, DISH3);
        List<Restaurant> returnedRestaurants = new ArrayList<>();
        for (Menu menu : returned) {
            returnedRestaurants.add(menu.getRestaurant());
        }
        assertMatch(returnedRestaurants, RESTAURANT, RESTAURANT2);
    }
}