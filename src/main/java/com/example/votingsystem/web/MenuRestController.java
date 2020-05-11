package com.example.votingsystem.web;

import com.example.votingsystem.model.Menu;
import com.example.votingsystem.repository.RestaurantRepository;
import com.example.votingsystem.service.MenuService;
import com.example.votingsystem.to.MenuTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.example.votingsystem.util.Util.createMenuFromTo;

@RestController
@RequestMapping(MenuRestController.REST_URL)
public class MenuRestController {
    public static final String REST_URL = "/rest";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MenuService menuService;
    private final RestaurantRepository restaurantRepository;

    public MenuRestController(MenuService menuService, RestaurantRepository restaurantRepository) {
        this.menuService = menuService;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping(value = "/admin/menu/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable("id") int id) {
        log.info("get {}", id);
        return menuService.getById(id);
    }

    @DeleteMapping(value = "/admin/menu/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete {}", id);
        menuService.delete(id);
    }

    @PostMapping(value = "/admin/menu", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createMenuWithLocation(@RequestBody MenuTo menuTo,
                                                       @RequestParam(value = "restaurantId") int id) {
        Assert.notNull(menuTo, "menuTo must not be null");
        log.info("create {}", menuTo);
        Menu menu = createMenuFromTo(menuTo);
        menu.setRestaurant(restaurantRepository.getOne(id));
        Menu created = menuService.save(menu);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/admin/menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody MenuTo menuTo) {
        Assert.notNull(menuTo, "menuTo must not be null");
        log.info("update menu {}", menuTo);
        Menu menu = createMenuFromTo(menuTo);
        menu.setRestaurant(restaurantRepository.getByMenusId(menuTo.getId()));
        menu.setDishes(menuService.getById(menuTo.getId()).getDishes());
        menuService.update(menu);
    }

    @GetMapping(value = "/admin/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAll() {
        log.info("getAll");
        return menuService.findAll();
    }

    @GetMapping(value = "/profile/menus", produces = MediaType.APPLICATION_JSON_VALUE)
    Set<Menu> getWithDishesAndRestaurantByCurrentDate() {
        log.info("get menus with restaurants and dishes by current date");
        return menuService.getWithDishesAndRestaurantByDate(LocalDate.now());
    }
}
