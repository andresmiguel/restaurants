package com.py.restaurants.pedidosya.auth;

import com.py.restaurants.pedidosya.exceptions.PyAccessTokenException;

public interface PyAccessTokenManager {

    String getToken() throws PyAccessTokenException;
    void refreshToken() throws PyAccessTokenException;
}
