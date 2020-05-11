package com.example.votingsystem.web;

import com.example.votingsystem.model.Voice;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalTime;

import static com.example.votingsystem.TestUtil.readFromJson;
import static com.example.votingsystem.TestUtil.userHttpBasic;
import static com.example.votingsystem.UserTestData.USER;
import static com.example.votingsystem.UserTestData.USER2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VotingRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VotingRestController.REST_URL;

    @Test
    void vote() throws Exception {
        ResultActions action = mockMvc.perform(post(REST_URL + "?restaurantId=100004")
                .with(userHttpBasic(USER2)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Voice returned = readFromJson(action, Voice.class);

        assertEquals(returned.getRestaurant().getId().intValue(), 100004);
    }

    @Test
    void voteAgain() throws Exception {
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            ResultActions action = mockMvc.perform(post(REST_URL + "?restaurantId=100005")
                    .with(userHttpBasic(USER)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
            Voice returned = readFromJson(action, Voice.class);
            assertEquals(returned.getRestaurant().getId().intValue(), 100005);
        } else {
            mockMvc.perform(post(REST_URL + "?restaurantId=100004")
                    .with(userHttpBasic(USER)))
                    .andExpect(status().isNotAcceptable());
        }
    }
}