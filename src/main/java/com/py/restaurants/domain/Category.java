package com.py.restaurants.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Category {
    @Id
    @SequenceGenerator(name = "categoryRangeGen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoryRangeGen")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}
