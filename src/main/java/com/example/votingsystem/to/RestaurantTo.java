package com.example.votingsystem.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RestaurantTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    public RestaurantTo() {
    }

    public RestaurantTo(Integer id, @NotBlank @Size(min = 2, max = 100) String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
