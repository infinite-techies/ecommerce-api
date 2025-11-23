package com.infinitetechies.ecommerce_api.service.inf;

import com.infinitetechies.ecommerce_api.model.dto.request.LoginRequest;
import com.infinitetechies.ecommerce_api.model.dto.request.RegisterRequest;
import com.infinitetechies.ecommerce_api.model.dto.response.AuthResponse;

public interface IAuthService {

    String register(RegisterRequest request);
    AuthResponse login(LoginRequest request);

}