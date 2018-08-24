package com.py.restaurants.restaurants.rest;

import com.py.restaurants.ReflectionUtils;
import com.py.restaurants.restaurants.domain.Category;
import com.py.restaurants.restaurants.services.RestaurantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void shouldGetCategorySuccessfully() throws Exception {
        String categoryName = "my category name";
        Category category = new Category(categoryName);
        ReflectionUtils.setPrivateField(category, "id", 1L);

        given(restaurantService.getCategory(1L)).willReturn(category);

        mvc.perform(get("/restaurants/categories/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(categoryName)));
    }
}
