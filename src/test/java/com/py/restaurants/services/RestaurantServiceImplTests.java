package com.py.restaurants.services;

import com.py.restaurants.domain.Restaurant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
public class RestaurantServiceImplTests {

    private RestaurantServiceImpl restaurantService;
    @MockBean
    private RestaurantRepository restaurantRepository;
    @MockBean
    private CategoryRepository categoryRepository;

    @Before
    public void init() {
        restaurantService = new RestaurantServiceImpl(restaurantRepository,categoryRepository);
    }

    @Test
    public void shouldGetAllReturnValidStream() {

        List<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(Restaurant.builder()
            .name("r1")
            .build());
        restaurantList.add(Restaurant.builder()
                .name("r2")
                .build());
        Mockito.when(restaurantRepository.findByDeletedFalse())
                .thenReturn(restaurantList);
        List<Restaurant> returnedList = restaurantService.getAll().collect(Collectors.toList());

        assertThat(returnedList.size()).isEqualTo(restaurantList.size());
        assertThat(returnedList.get(0).getName()).isEqualTo(restaurantList.get(0).getName());
    }
}
