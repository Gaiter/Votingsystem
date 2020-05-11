package com.example.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "menus")
public class Menu extends AbstractNamedEntity {

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @JsonManagedReference("menu_dish")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    private Set<Dish> dishes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    public Menu() {
    }

    public Menu(Integer id, String name, @NotNull LocalDate date) {
        super(id, name);
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "date=" + date +
                ", restaurant=" + restaurant +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
