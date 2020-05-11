package com.example.votingsystem.web;

import com.example.votingsystem.model.Dish;
import com.example.votingsystem.service.DishService;
import com.example.votingsystem.service.MenuService;
import com.example.votingsystem.to.DishTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.example.votingsystem.util.Util.createDishFromTo;

@RestController
@RequestMapping(DishRestController.REST_URL)
public class DishRestController {
    public static final String REST_URL = "/rest/admin/dish";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final DishService dishService;
    private final MenuService menuService;

    public DishRestController(DishService dishService, MenuService menuService) {
        this.dishService = dishService;
        this.menuService = menuService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable("id") int id) {
        log.info("get {}", id);
        return dishService.getById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete {}", id);
        dishService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@RequestBody DishTo dishTo, @RequestParam(value = "menuId") int id) {
        Assert.notNull(dishTo, "dishTo must not be null");
        log.info("create {}", dishTo);
        Dish dish = createDishFromTo(dishTo);
        dish.setMenu(menuService.getById(id));
        Dish created = dishService.save(dish);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody DishTo dishTo) {
        Assert.notNull(dishTo, "dishTo must not be null");
        log.info("update {}", dishTo);
        Dish dish = createDishFromTo(dishTo);
        dish.setMenu(menuService.getByDishesId(dishTo.getId()));
        dishService.update(dish);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll() {
        log.info("getAll");
        return dishService.findAll();
    }
}
