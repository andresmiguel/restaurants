package com.py.restaurants.pedidosya.api;

import com.py.restaurants.pedidosya.api.dto.PyRestaurantMapper;
import com.py.restaurants.pedidosya.api.dto.PyRestaurantsResultDto;
import com.py.restaurants.pedidosya.auth.PyAccessTokenManager;
import com.py.restaurants.pedidosya.exceptions.PyAccessTokenException;
import com.py.restaurants.pedidosya.exceptions.PyRestaurantsApiException;
import com.py.restaurants.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PyRestaurantServiceImpl implements PyRestaurantService {

    @Value("${py.api.restaurants.search}")
    private String restaurantsSearhUrl;

    private RestTemplate restTemplate;
    private PyAccessTokenManager pyAccessTokenManager;

    public PyRestaurantServiceImpl(RestTemplate restTemplate, PyAccessTokenManager pyAccessTokenManager) {
        this.restTemplate = restTemplate;
        this.pyAccessTokenManager = pyAccessTokenManager;
    }

    @Override
    public List<PyRestaurant> findByCountryAndLocation(int country, Point point) throws PyRestaurantsApiException {
        URI uri = getRestaurantSearchUrl(country, point);
        PyRestaurantsResultDto restaurantResultDto;
        try {
            ResponseEntity<String> entity = makeRequest(uri);
            restaurantResultDto = JsonUtils.toObject(entity.getBody(), PyRestaurantsResultDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PyRestaurantsApiException(e);
        }

        return restaurantResultDto.data.stream()
                .map(PyRestaurantMapper::fromDto)
                .collect(Collectors.toList());
    }

    private ResponseEntity<String> makeRequest(URI uri) throws PyAccessTokenException {
        ResponseEntity<String> response;
        try {
            HttpEntity<String> httpEntity = getStringHttpEntity(pyAccessTokenManager.getToken());
            response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        } catch (HttpClientErrorException httpClientExc) {
            if (httpClientExc.getStatusCode().value() == 403) {
                pyAccessTokenManager.refreshToken();
                HttpEntity<String> httpEntity = getStringHttpEntity(pyAccessTokenManager.getToken());
                response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
            } else {
                throw httpClientExc;
            }
        }

        return response;
    }

    private HttpEntity<String> getStringHttpEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        return new HttpEntity<>("parameters", headers);
    }

    private URI getRestaurantSearchUrl(int country, Point point) {
        return UriComponentsBuilder.fromHttpUrl(restaurantsSearhUrl)
                .queryParam("country", country)
                .queryParam("point", point.getY() + "," + point.getX())
                .queryParam("fields", "name,allCategories")
                .build()
                .toUri();
    }
}
