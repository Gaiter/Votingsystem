package com.example.votingsystem.web;

import com.example.votingsystem.model.Restaurant;
import com.example.votingsystem.model.User;
import com.example.votingsystem.model.Voice;
import com.example.votingsystem.repository.RestaurantRepository;
import com.example.votingsystem.repository.VoiceRepository;
import com.example.votingsystem.service.user.UserService;
import com.example.votingsystem.util.exception.TooLateToChangeVoiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.example.votingsystem.util.ValidationUtil.checkNotFound;

@RestController
@RequestMapping(VotingRestController.REST_URL)
public class VotingRestController {
    public static final String REST_URL = "/rest/profile/voting";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final RestaurantRepository restaurantRepository;
    private final VoiceRepository voiceRepository;
    private final UserService userService;

    public VotingRestController(RestaurantRepository restaurantRepository,
                                VoiceRepository voiceRepository, UserService userService) {
        this.restaurantRepository = restaurantRepository;
        this.voiceRepository = voiceRepository;
        this.userService = userService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Voice vote(@RequestParam(value = "restaurantId") int restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("user with id={} vote for restaurant with id={}", userId, restaurantId);
        Voice voice = voiceRepository.getByUserIdAndDate(userId, LocalDate.now());
        Restaurant restaurant = checkNotFound(restaurantRepository.getById(restaurantId), "restaurantId is wrong");
        if (voice == null) {
            User user = userService.get(userId);
            voice = new Voice(null, LocalDate.now(), user, restaurant);
            return voiceRepository.save(voice);
        } else if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            voice.setRestaurant(restaurant);
            return voiceRepository.save(voice);
        } else {
            throw new TooLateToChangeVoiceException("It is too late to change the voice");
        }
    }
}