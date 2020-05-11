package com.example.votingsystem.util;

import com.example.votingsystem.model.Dish;
import com.example.votingsystem.model.Menu;
import com.example.votingsystem.model.Restaurant;
import com.example.votingsystem.model.User;
import com.example.votingsystem.to.DishTo;
import com.example.votingsystem.to.MenuTo;
import com.example.votingsystem.to.RestaurantTo;
import com.example.votingsystem.to.UserTo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

public class Util {

    public static UserTo userAsTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

    public static User updateUserFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User prepareUserToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.isEmpty(password) ? password : passwordEncoder.encode(password));
        user.setEmail(user.getEmail().trim().toLowerCase());
        return user;
    }

    public static Dish createDishFromTo(DishTo dishTo) {
        return new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice());
    }

    public static Menu createMenuFromTo(MenuTo menuTo) {
        return new Menu(menuTo.getId(), menuTo.getName(), menuTo.getDate());
    }

    public static Restaurant createRestaurantFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName());
    }
}