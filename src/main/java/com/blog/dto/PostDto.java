package com.blog.dto;

import com.blog.model.Comment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {

    private Long id;
    @NotEmpty
    @Size(min = 2,message = "title should have atleast 2 character")
    private String title;
    @NotEmpty
    @Size(min = 10,message = "Description atleat contain 10 characters")
    private String description;
    @NotEmpty
    private String content;
    @Valid
    private Set<CommentDto> comment;

}
