package com.infinitetechies.ecommerce_api.model.dto.request;

import com.infinitetechies.ecommerce_api.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotNull(message = "First Name is required!")
    @Size(min = 5, max = 40, message = "First Name should be between 5 to 40 characters")
    private String firstName;

    @Size(min = 5, max = 40, message = "Last Name should be between 5 to 40 characters")
    @NotNull(message = "Last Name is required!")
    private String lastName;

    @Email(message = "Valid Email is required!")
    @NotNull(message = "Email is required!")
    private String email;

    @Size(min = 6, message = "Password should be atleast 6 characters!")
    @NotNull(message = "Password is required!")
    private String password;

    @NotNull(message = "Role is required!")
    private UserRole role;
}
