package com.example.votingsystem;

import com.example.votingsystem.model.Dish;
import com.example.votingsystem.model.Menu;
import com.example.votingsystem.model.User;
import com.example.votingsystem.to.DishTo;
import com.example.votingsystem.to.MenuTo;
import com.example.votingsystem.web.json.JsonUtil;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class TestUtil {

    public static DishTo createToFromDish(Dish dish) {
        return new DishTo(dish.getId(), dish.getName(), dish.getPrice());
    }

    public static MenuTo createToFromMenu(Menu menu) {
        return new MenuTo(menu.getId(), menu.getName(), menu.getDate());
    }

    public static String getContent(ResultActions action) throws UnsupportedEncodingException {
        return action.andReturn().getResponse().getContentAsString();
    }

    public static ResultActions print(ResultActions action) throws UnsupportedEncodingException {
        System.out.println(getContent(action));
        return action;
    }

    public static <T> T readFromJson(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(action), clazz);
    }

    public static <T> List<T> readFromJsonList(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValues(getContent(action), clazz);
    }

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }
}
