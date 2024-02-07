package com.example.library.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Person {
    private int id;
    @NotEmpty(message = "Name is empty")
    @Size(min = 2, max = 30, message = "Name should be valid")
    private String name;
    @Min(value = 1900, message = "Year is not correct")
    private int year;

    public Person(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }
    public Person(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }
}
