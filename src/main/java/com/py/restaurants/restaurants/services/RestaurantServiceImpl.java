package com.py.restaurants.restaurants.services;

import com.py.restaurants.pedidosya.api.PyRestaurant;
import com.py.restaurants.pedidosya.api.PyRestaurantService;
import com.py.restaurants.pedidosya.exceptions.PyRestaurantsApiException;
import com.py.restaurants.restaurants.domain.Category;
import com.py.restaurants.restaurants.domain.Restaurant;
import com.py.restaurants.restaurants.dto.*;
import com.py.restaurants.restaurants.dto.mappers.CategoryMapper;
import com.py.restaurants.restaurants.dto.mappers.RestaurantMapper;
import com.py.restaurants.restaurants.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 100;
    private static final int COUNTRY = 1; // Uruguay

    private RestaurantRepository restaurantRepository;
    private CategoryRepository categoryRepository;
    private PyRestaurantService pyRestaurantService;


    public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
                                 CategoryRepository categoryRepository,
                                 PyRestaurantService pyRestaurantService) {
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
        this.pyRestaurantService = pyRestaurantService;
    }

    @Override
    public Restaurant get(Long id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findOne(id);

        if (restaurant == null || restaurant.isDeleted()) {
            throw new RestaurantNotFoundException(id);
        }

        return restaurant;
    }

    @Override
    public Stream<Restaurant> getAll(PageableSearchRestaurantDto pageableSearchRestaurantDto) {
        Pageable pageable = buildPageRequest(pageableSearchRestaurantDto);
        List<Restaurant> restaurants = restaurantRepository.findByDeletedFalse(pageable);
        return restaurants.stream();
    }

    private Pageable buildPageRequest(PageableSearchRestaurantDto searchRestaurantDto) {
        int page = searchRestaurantDto.page != null ? searchRestaurantDto.page : DEFAULT_PAGE;
        int pageSize = searchRestaurantDto.pageSize != null ? searchRestaurantDto.pageSize : DEFAULT_PAGE_SIZE;

        return new PageRequest(page, pageSize);
    }

    @Override
    public Restaurant add(RestaurantDto dto) throws DuplicateRestaurantNameException {
        Restaurant restaurant = RestaurantMapper.fromDto(dto);
        setRestaurantCategories(dto, restaurant);

        try {
            restaurant = restaurantRepository.save(restaurant);
        } catch (DataIntegrityViolationException div) {
            throw new DuplicateRestaurantNameException();
        }

        return restaurant;
    }

    @Override
    public Restaurant update(Long id, RestaurantDto dto) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findOne(id);

        if (restaurant == null) {
            throw new RestaurantNotFoundException(id);
        }

        RestaurantMapper.copyFromDto(restaurant, dto);
        restaurant.getCategories().clear();
        setRestaurantCategories(dto, restaurant);

        return restaurantRepository.save(restaurant);
    }

    private void setRestaurantCategories(RestaurantDto dto, Restaurant restaurant) {
        Set<Category> categories = dto.categories
                .stream()
                .map(c -> c.id)
                .map(categoryRepository::findOne)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        restaurant.getCategories().addAll(categories);
    }

    @Override
    public void delete(Long id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findOne(id);

        if (restaurant == null) {
            throw new RestaurantNotFoundException(id);
        }

        restaurant.setDeleted(true);
        restaurantRepository.save(restaurant);
    }

    @Override
    public Category getCategory(Long id) throws CategoryNotFoundException {
        Category category = categoryRepository.findOne(id);

        if (category == null) {
            throw new CategoryNotFoundException(id);
        }

        return category;
    }

    @Override
    public Stream<Category> getAllCategories() {
        return categoryRepository.findAll().stream();
    }

    @Override
    public Category addCategory(CategoryDto dto) throws DuplicateCategoryNameException {

        Category category = CategoryMapper.fromDto(dto);
        try {
            category = categoryRepository.save(category);
        } catch (DataIntegrityViolationException div) {
            throw new DuplicateCategoryNameException();
        }

        return category;
    }

    @Override
    public Category updateCategory(Long id, CategoryDto dto) throws CategoryNotFoundException {
        Category category = categoryRepository.findOne(id);

        if (category == null) {
            throw new CategoryNotFoundException(id);
        }

        CategoryMapper.copyFromDto(category, dto);

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) throws CategoryNotFoundException {
        try {
            categoryRepository.delete(id);
        } catch (EmptyResultDataAccessException erda) {
            throw new CategoryNotFoundException(id);
        }
    }

    @Override
    public Stream<Restaurant> search(SearchRestaurantDto searchRestaurantDto) throws RestaurantSearchException {
        Pageable pageable = buildPageRequest(searchRestaurantDto);
        List<Restaurant> restaurants;

        if (searchRestaurantDto.categoryId != null && searchRestaurantDto.namePart != null) {
            restaurants = restaurantRepository.findByCategoriesIdAndNameContainingAndDeletedFalse(
                    searchRestaurantDto.categoryId,
                    searchRestaurantDto.namePart,
                    pageable
            );
        } else if (searchRestaurantDto.categoryId != null) {
            restaurants = restaurantRepository.findByCategoriesIdAndDeletedFalse(
                    searchRestaurantDto.categoryId,
                    pageable);
        } else if (searchRestaurantDto.namePart != null) {
            restaurants = restaurantRepository.findByNameContainingAndDeletedFalse(
                    searchRestaurantDto.namePart,
                    pageable
            );
        } else {
            throw new RestaurantSearchException();
        }

        return restaurants.stream();
    }

    @Override
    public List<CompetitorDto> findCompetitors(Long id) throws RestaurantNotFoundException, ExternalServiceException {
        Restaurant restaurant = this.get(id);
        List<CompetitorDto> competitorDtos = new ArrayList<>();

        try {
            List<PyRestaurant> competitors = this.pyRestaurantService.findByCountryAndLocation(COUNTRY, restaurant.getLocation());

            for (PyRestaurant pyRestaurant : competitors) {
                Set<String> pyCategories = getCommonCategories(restaurant, pyRestaurant);

                if (!pyCategories.isEmpty()) {
                    CompetitorDto competitorDto = CompetitorDto.builder()
                            .name(pyRestaurant.getName())
                            .commonCategories(pyCategories)
                            .build();
                    competitorDtos.add(competitorDto);
                }
            }
        } catch (PyRestaurantsApiException e) {
            throw new ExternalServiceException(e.getMessage());
        }

        return competitorDtos;
    }

    private Set<String> getCommonCategories(Restaurant restaurant, PyRestaurant pyRestaurant) {

        return restaurant.getCategories().stream()
                .map(Category::getName)
                .filter(pyRestaurant.getCategories()::contains)
                .collect(Collectors.toSet());
    }
}
