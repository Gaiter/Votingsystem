package com.example.votingsystem.repository;

import com.example.votingsystem.model.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Transactional
    Menu save(Menu menu);

    Menu getById(Integer id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);

    Menu getByDishesId(int id);

    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Menu m LEFT JOIN m.restaurant WHERE m.date = :date")
    Set<Menu> getWithDishesAndRestaurantByDate(@Param("date") LocalDate date);

    @Override
    List<Menu> findAll();
}
