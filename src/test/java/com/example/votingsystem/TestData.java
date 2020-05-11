package com.example.votingsystem;

import com.example.votingsystem.model.Dish;
import com.example.votingsystem.model.Menu;
import com.example.votingsystem.model.Restaurant;
import com.example.votingsystem.model.Voice;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static com.example.votingsystem.web.json.JsonUtil.writeIgnoreProps;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class TestData {

    public static final Restaurant RESTAURANT = new Restaurant(100003, "Mandarin");
    public static final Restaurant RESTAURANT2 = new Restaurant(100004, "Stargorod");
    public static final Restaurant RESTAURANT3 = new Restaurant(100005, "Buffet");
    public static final Voice VOICE = new Voice(100006, LocalDate.now());
    public static final Voice VOICE2 = new Voice(100007, LocalDate.of(2018, Month.SEPTEMBER, 29));
    public static final Voice VOICE3 = new Voice(100008, LocalDate.now());
    public static final Menu MENU = new Menu(100009, "Lunch", LocalDate.now());
    public static final Menu MENU2 = new Menu(100010, "SuperLunch", LocalDate.now());
    public static final Menu MENU3 = new Menu(100011, "LiteLunch", LocalDate.of(2018, 9, 20));
    public static final Dish DISH = new Dish(100012, "Salad", 1000L);
    public static final Dish DISH2 = new Dish(100013, "Soup", 1500L);
    public static final Dish DISH3 = new Dish(100014, "Meat", 2000L);
    public static final Dish DISH4 = new Dish(100015, "Beer", 1000L);
    public static final Dish DISH5 = new Dish(100016, "Juice", 500L);
    public static final Dish DISH6 = new Dish(100017, "Pizza", 2000L);
    public static final List<Dish> DISHES = Arrays.asList(DISH, DISH2, DISH3, DISH4, DISH5, DISH6);

    public static <T> void assertMatch(T actual, T expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, getIgnoreFields(expected));
    }

    @SafeVarargs
    public static <T> void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static <T> void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields(getIgnoreFields(expected)).isEqualTo(expected);
    }

    @SafeVarargs
    public static <T> ResultMatcher contentJson(T... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), getIgnoreFields(expected)));
    }

    public static <T> ResultMatcher contentJson(T expected) {
        return content().json(writeIgnoreProps(expected, getIgnoreFields(expected)));
    }

    private static <T> String[] getIgnoreFields(T expected) {
        return getStrings(expected);
    }

    @SafeVarargs
    private static <T> String[] getIgnoreFields(T... expected) {
        return getStrings(expected[0]);
    }

    private static <T> String[] getIgnoreFields(Iterable<T> expected) {
        T clazz = expected.iterator().next();
        return getStrings(clazz);
    }

    private static <T> String[] getStrings(T clazz) {
        if (clazz instanceof Dish) {
            return new String[]{"menu"};
        } else if (clazz instanceof Menu) {
            return new String[]{"date", "dishes", "restaurant"};
        } else if (clazz instanceof Restaurant) {
            return new String[]{"menus", "voices"};
        } else if (clazz instanceof Voice) {
            return new String[]{"date_time", "user", "restaurant"};
        } else return null;
    }
}