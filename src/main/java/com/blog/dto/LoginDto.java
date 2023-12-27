package com.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {
    @NotEmpty
    @Size(min = 5,message = "user username and email should have atleast 5 character")
    private String usernameOrEmail;
    @NotEmpty
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])[a-zA-Z\\d@#$%^!&+=]{5,10}$",
            message ="Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@#$%^&+=)")
    private String password;
}
