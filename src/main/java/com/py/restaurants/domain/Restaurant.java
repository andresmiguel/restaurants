package com.py.restaurants.domain;

import com.py.restaurants.domain.utils.Checker;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Restaurant {

    @Id
    @SequenceGenerator(name = "restaurantRangeGen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurantRangeGen")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    private String phone;
    private double latitude;
    private double longitude;
    @OneToOne
    private Schedule schedule;
    @ManyToMany
    private Set<Category> categories = new HashSet<>();

    @Builder
    public Restaurant(String name, String description, String phone, double latitude,
                      double longitude, Schedule schedule, Set<Category> categories) {
        Checker.isNotBlank(name, "Invalid Restaurant name!");
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.schedule = schedule;
        this.categories = categories;
    }
}
