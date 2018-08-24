package com.py.restaurants.services;

import com.py.restaurants.domain.Restaurant;
import com.py.restaurants.dto.PageableSearchRestaurantDto;
import com.py.restaurants.dto.SearchRestaurantDto;
import com.py.restaurants.exceptions.RestaurantSearchException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RestaurantServiceImplTests {

    private RestaurantServiceImpl restaurantService;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @Before
    public void init() {
        restaurantService = new RestaurantServiceImpl(restaurantRepository, categoryRepository);
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

        when(restaurantRepository.findByDeletedFalse(new PageRequest(0, 10)))
                .thenReturn(restaurantList);

        PageableSearchRestaurantDto pageableSearchRestaurantDto = new PageableSearchRestaurantDto();
        pageableSearchRestaurantDto.page = 0;
        pageableSearchRestaurantDto.pageSize = 10;
        List<Restaurant> returnedList = restaurantService.getAll(pageableSearchRestaurantDto).collect(Collectors.toList());

        assertThat(returnedList.size()).isEqualTo(restaurantList.size());
        assertThat(returnedList.get(0).getName()).isEqualTo(restaurantList.get(0).getName());
    }

    @Test
    public void shouldSearchReturnValidStreamWhenSearchByCategoryIdAndNamePart() throws RestaurantSearchException {
        List<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(Restaurant.builder()
                .name("r1")
                .build());
        restaurantList.add(Restaurant.builder()
                .name("r2")
                .build());
        PageRequest pageRequest = new PageRequest(0, 10);

        when(restaurantRepository.findByCategoriesIdAndNameContainingAndDeletedFalse(
                1L, "half name", pageRequest)
        ).thenReturn(restaurantList);

        SearchRestaurantDto searchRestaurantDto = SearchRestaurantDto.builder()
                .categoryId(1L)
                .namePart("half name")
                .build();
        searchRestaurantDto.setPage(0);
        searchRestaurantDto.setPageSize(10);

        List<Restaurant> returnedList = restaurantService.search(searchRestaurantDto).collect(Collectors.toList());

        assertThat(returnedList.size()).isEqualTo(restaurantList.size());
        assertThat(returnedList.get(0).getName()).isEqualTo(restaurantList.get(0).getName());
    }

    @Test(expected = RestaurantSearchException.class)
    public void shouldSearchThrowExceptionWhenSearchByCategoryIdAndNamePart() throws RestaurantSearchException {

        SearchRestaurantDto searchRestaurantDto = SearchRestaurantDto.builder()
                .categoryId(null)
                .namePart(null)
                .build();
        searchRestaurantDto.setPage(0);
        searchRestaurantDto.setPageSize(10);

        restaurantService.search(searchRestaurantDto);
    }
}
