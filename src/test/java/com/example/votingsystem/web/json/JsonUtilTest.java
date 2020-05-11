package com.example.votingsystem.web.json;

import com.example.votingsystem.UserTestData;
import com.example.votingsystem.model.Dish;
import com.example.votingsystem.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.votingsystem.TestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilTest {

    @Test
    void testReadWriteValue(){
        String json = JsonUtil.writeValue(DISH);
        System.out.println(json);
        Dish dish = JsonUtil.readValue(json, Dish.class);
        assertMatch(dish, DISH);
    }

    @Test
    void testReadWriteValues(){
        String json = JsonUtil.writeValue(DISHES);
        System.out.println(json);
        List<Dish> dishes = JsonUtil.readValues(json, Dish.class);
        assertMatch(dishes, DISHES);
    }

    @Test
    void testWriteOnlyAccess(){
        String json = JsonUtil.writeValue(UserTestData.ADMIN);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserTestData.jsonWithPassword(UserTestData.USER, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}