package com.example.votingsystem.service;

import com.example.votingsystem.model.Menu;
import com.example.votingsystem.repository.MenuRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.example.votingsystem.util.ValidationUtil.checkNotFound;
import static com.example.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Menu getByDishesId(Integer id) {
        return checkNotFound(menuRepository.getByDishesId(id), "DishesId" + id);
    }

    @CacheEvict(cacheNames = "menus", allEntries = true)
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    public Menu getById(Integer id) {
        return checkNotFoundWithId(menuRepository.getById(id), id);
    }

    @CacheEvict(cacheNames = "menus", allEntries = true)
    public int delete(int id) {
        return checkNotFoundWithId(menuRepository.delete(id), id);
    }

    @Cacheable(cacheNames = "menus")
    public Set<Menu> getWithDishesAndRestaurantByDate(LocalDate date) {
        return checkNotFound(menuRepository.getWithDishesAndRestaurantByDate(date), "ByDate" + date);
    }

    @Cacheable(cacheNames = "menus")
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @CacheEvict(cacheNames = "menus", allEntries = true)
    public void update(Menu menu) {
        menuRepository.save(menu);
    }
}