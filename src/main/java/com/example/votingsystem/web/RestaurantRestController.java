package com.example.votingsystem.web;

import com.example.votingsystem.model.Restaurant;
import com.example.votingsystem.repository.RestaurantRepository;
import com.example.votingsystem.to.RestaurantTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.example.votingsystem.util.Util.createRestaurantFromTo;
import static com.example.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(RestaurantRestController.REST_URL)
public class RestaurantRestController {
    public static final String REST_URL = "/rest/admin/restaurant";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantRestController(RestaurantRepository restaurantService) {
        this.restaurantRepository = restaurantService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(restaurantRepository.getById(id), id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(restaurantRepository.delete(id), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody RestaurantTo restaurantTo) {
        Assert.notNull(restaurantTo, "restaurantTo must not be null");
        log.info("create {}", restaurantTo);
        Restaurant restaurant = createRestaurantFromTo(restaurantTo);
        Restaurant created = restaurantRepository.save(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody RestaurantTo restaurantTo) {
        Assert.notNull(restaurantTo, "restaurantTo must not be null");
        log.info("update {} ", restaurantTo);
        Restaurant restaurant = createRestaurantFromTo(restaurantTo);
        restaurantRepository.save(restaurant);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantRepository.findAll();
    }
}
