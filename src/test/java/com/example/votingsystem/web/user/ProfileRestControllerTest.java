package com.example.votingsystem.web.user;

import com.example.votingsystem.TestUtil;
import com.example.votingsystem.model.Role;
import com.example.votingsystem.model.User;
import com.example.votingsystem.service.user.UserService;
import com.example.votingsystem.to.UserTo;
import com.example.votingsystem.util.Util;
import com.example.votingsystem.web.AbstractControllerTest;
import com.example.votingsystem.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.votingsystem.TestUtil.readFromJson;
import static com.example.votingsystem.TestUtil.userHttpBasic;
import static com.example.votingsystem.UserTestData.*;
import static com.example.votingsystem.web.user.ProfileRestController.REST_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    protected UserService userService;

    @Test
    void testGet() throws Exception {
        TestUtil.print(
                mockMvc.perform(get(REST_URL + "/profile")
                        .with(userHttpBasic(USER)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(contentJson(USER))
        );
    }

    @Test
    void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL + "/profile"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/profile")
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN, USER2);
    }

    @Test
    void testUpdate() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");

        mockMvc.perform(put(REST_URL + "/profile").contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(userService.getByEmail("newemail@ya.ru"), Util.updateUserFromTo(new User(USER), updatedTo));
    }

    @Test
    void testRegister() throws Exception {
        User expected = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(jsonWithPassword(expected, "newPass")))
                .andExpect(status().isCreated());

        User returned = readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(userService.getAll(), ADMIN, expected, USER, USER2);
    }
}