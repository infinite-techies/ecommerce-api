package com.infinitetechies.ecommerce_api.repository;

import com.infinitetechies.ecommerce_api.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(@Email(message = "Valid Email is required!") @NotBlank(message = "Email is required!") String email);
}
