package com.example.votingsystem.to;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class DishTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Range(min = 1, max = 5000000)
    private Long price;

    public DishTo() {
    }

    public DishTo(Integer id, String name, Long price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "DishTo{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", id=" + id +
                '}';
    }
}
