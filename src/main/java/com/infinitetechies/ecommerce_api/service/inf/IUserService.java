package com.infinitetechies.ecommerce_api.service.inf;

import com.infinitetechies.ecommerce_api.model.User;
import com.infinitetechies.ecommerce_api.model.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {

     List<UserResponse> getUsers();
     UserResponse getUserById(Long id);
     User getCurrentUser();
}
