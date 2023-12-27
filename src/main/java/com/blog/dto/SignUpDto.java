package com.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class SignUpDto {
    @NotEmpty
    @Size(min = 5,message = "name should have atleast 5 character")
    private String name;
    @NotEmpty
    @Size(min = 5,message = "user username should have atleast 5 character")
    private String username;
    @NotEmpty(message = "Name should not be null or empty")
    @Email
    private String email;
    @NotEmpty
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])[a-zA-Z\\d@#$%^!&+=]{5,10}$",
            message ="Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@#$%^&+=)")
    private String password;
}
