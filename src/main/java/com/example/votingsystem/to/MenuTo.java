package com.example.votingsystem.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class MenuTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    private LocalDate date;

    public MenuTo() {
    }

    public MenuTo(Integer id, @NotBlank @Size(min = 2, max = 100) String name, @NotNull LocalDate date) {
        super(id);
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", id=" + id +
                '}';
    }
}
