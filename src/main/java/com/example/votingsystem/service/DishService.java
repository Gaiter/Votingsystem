package com.example.votingsystem.service;

import com.example.votingsystem.model.Dish;
import com.example.votingsystem.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service("dishService")
public class DishService {

    private final DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @CacheEvict(cacheNames = "menus", allEntries = true)
    public Dish save(Dish dish) {
        return dishRepository.save(dish);
    }

    public Dish getById(Integer id) {
        return checkNotFoundWithId(dishRepository.getById(id), id);
    }

    @CacheEvict(cacheNames = "menus", allEntries = true)
    public int delete(int id) {
        return checkNotFoundWithId(dishRepository.delete(id), id);
    }

    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    @CacheEvict(cacheNames = "menus", allEntries = true)
    public void update(Dish dish) {
        dishRepository.save(dish);
    }
}