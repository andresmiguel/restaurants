package com.py.restaurants;

import com.py.restaurants.domain.Category;
import com.py.restaurants.domain.Restaurant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RestaurantRepositoryTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void shouldFindByNameContainingReturnOnlyOneRestaurant() {
        Restaurant restaurant1 = Restaurant.builder().name("My restaurant").build();
        Restaurant restaurant2 = Restaurant.builder().name("You are not gonna find me").build();

        testEntityManager.persist(restaurant1);
        testEntityManager.persist(restaurant2);
        testEntityManager.flush();

        List<Restaurant> restaurants = restaurantRepository.findByNameContaining("rest");
        assertThat(restaurants.size()).isEqualTo(1);
    }

    @Test
    public void shouldFindByCategoriesIdReturnOnlyOneRestaurant() {
        Category category1 = new Category("Cat1");
        Category category2 = new Category("Cat2");
        Category category3 = new Category("Cat3");

        category1 = testEntityManager.persist(category1);
        category2 = testEntityManager.persist(category2);
        category3 = testEntityManager.persist(category3);
        testEntityManager.flush();

        Restaurant restaurant1 = Restaurant.builder()
                .name("My restaurant")
                .categories(new HashSet<>(Arrays.asList(category1, category2)))
                .build();

        Restaurant restaurant2 = Restaurant.builder()
                .name("Another one")
                .categories(new HashSet<>(Arrays.asList(category1, category3)))
                .build();

        Restaurant restaurant3 = Restaurant.builder()
                .name("Yet another one")
                .categories(new HashSet<>(Arrays.asList(category3)))
                .build();

        testEntityManager.persist(restaurant1);
        testEntityManager.persist(restaurant2);
        testEntityManager.persist(restaurant3);
        testEntityManager.flush();

        List<Restaurant> restaurants = restaurantRepository.findByCategoriesId(category1.getId());

        assertThat(restaurants.size()).isEqualTo(2);

    }

}
