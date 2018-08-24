package com.py.restaurants.restaurants.services;

import com.py.restaurants.restaurants.domain.Category;
import com.py.restaurants.restaurants.domain.Restaurant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public void shouldFindByDeletedFalseReturnValidRestaurantList() {
        Restaurant restaurant1 = Restaurant.builder().name("My restaurant").build();
        Restaurant restaurant2 = Restaurant.builder().name("You are not gonna find me").build();
        Restaurant restaurant3 = Restaurant.builder().name("rest but deleted").build();
        restaurant3.setDeleted(true);

        testEntityManager.persist(restaurant1);
        testEntityManager.persist(restaurant2);
        testEntityManager.persist(restaurant3);
        testEntityManager.flush();

        List<Restaurant> restaurants = restaurantRepository.findByDeletedFalse(new PageRequest(0, 10));
        assertThat(restaurants.size()).isEqualTo(2);
    }

    @Test
    public void shouldFindByNameContainingAndDeletedFalseReturnValidRestaurantList() {
        Restaurant restaurant1 = Restaurant.builder().name("My restaurant").build();
        Restaurant restaurant2 = Restaurant.builder().name("You are not gonna find me").build();
        Restaurant restaurant3 = Restaurant.builder().name("rest but deleted").build();
        restaurant3.setDeleted(true);

        testEntityManager.persist(restaurant1);
        testEntityManager.persist(restaurant2);
        testEntityManager.persist(restaurant3);
        testEntityManager.flush();

        Pageable pageRequest = new PageRequest(0, 10);
        List<Restaurant> restaurants = restaurantRepository.findByNameContainingAndDeletedFalse("rest", pageRequest);
        assertThat(restaurants.size()).isEqualTo(1);
    }

    @Test
    public void shouldFindByCategoriesIdAndDeletedFalseReturnValidRestaurantList() {
        Category category1 = new Category("Cat1");
        Category category2 = new Category("Cat2");
        Category category3 = new Category("Cat3");

        category1 = testEntityManager.persist(category1);
        category2 = testEntityManager.persist(category2);
        category3 = testEntityManager.persist(category3);
        testEntityManager.flush();

        Restaurant restaurant1 = Restaurant.builder()
                .name("My restaurant")
                .build();
        restaurant1.getCategories().addAll(new HashSet<>(Arrays.asList(category1, category2)));

        Restaurant restaurant2 = Restaurant.builder()
                .name("Another one")
                .build();
        restaurant2.getCategories().addAll(new HashSet<>(Arrays.asList(category1, category3)));

        Restaurant restaurant3 = Restaurant.builder()
                .name("Yet another one")
                .build();
        restaurant3.getCategories().addAll(new HashSet<>(Arrays.asList(category3)));

        Restaurant restaurant4 = Restaurant.builder()
                .name("Yet another one deleted")
                .build();
        restaurant4.setDeleted(true);
        restaurant4.getCategories().addAll(new HashSet<>(Arrays.asList(category1)));

        testEntityManager.persist(restaurant1);
        testEntityManager.persist(restaurant2);
        testEntityManager.persist(restaurant3);
        testEntityManager.persist(restaurant4);
        testEntityManager.flush();

        List<Restaurant> restaurants = restaurantRepository.findByCategoriesIdAndDeletedFalse(category1.getId(), new PageRequest(0, 10));

        assertThat(restaurants.size()).isEqualTo(2);

    }

    @Test
    public void shouldFindByCategoriesIdAndNameContainingAndDeletedFalseValidRestaurantList() {
        Category category1 = new Category("Cat1");
        Category category2 = new Category("Cat2");
        Category category3 = new Category("Cat3");

        category1 = testEntityManager.persist(category1);
        category2 = testEntityManager.persist(category2);
        category3 = testEntityManager.persist(category3);
        testEntityManager.flush();

        Restaurant restaurant1 = Restaurant.builder()
                .name("My restaurant")
                .build();
        restaurant1.getCategories().addAll(new HashSet<>(Arrays.asList(category1, category2)));

        Restaurant restaurant2 = Restaurant.builder()
                .name("Another one")
                .build();
        restaurant2.getCategories().addAll(new HashSet<>(Arrays.asList(category1, category3)));

        Restaurant restaurant3 = Restaurant.builder()
                .name("Yet another one")
                .build();
        restaurant3.getCategories().addAll(new HashSet<>(Arrays.asList(category3)));

        Restaurant restaurant4 = Restaurant.builder()
                .name("Yet another one restaurant")
                .build();
        restaurant4.getCategories().addAll(new HashSet<>(Arrays.asList(category1)));

        testEntityManager.persist(restaurant1);
        testEntityManager.persist(restaurant2);
        testEntityManager.persist(restaurant3);
        testEntityManager.persist(restaurant4);
        testEntityManager.flush();

        List<Restaurant> restaurants = restaurantRepository.findByCategoriesIdAndNameContainingAndDeletedFalse(
                category1.getId(),
                "rest",
                new PageRequest(0, 10));

        assertThat(restaurants.size()).isEqualTo(2);
    }

}
