package com.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;

    @NotEmpty(message = "Name should not be null or empty")
    private String name;
    @NotEmpty(message = "Name should not be null or empty")
    @Email
    private String email;
    @NotEmpty
    @Size(min = 10,message = "body should have atleast 10 character")
    private String body;
}
