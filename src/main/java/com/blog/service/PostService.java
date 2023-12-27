package com.blog.service;

import com.blog.dto.PostDto;
import com.blog.dto.PostResponse;

import java.util.List;

public interface PostService {

    public PostResponse getAllPost(int pageNo, int pazeSize,String sortBy,String sortDir);

    public PostDto getPostById(Long Id);

    public PostDto createPost(PostDto postDto);

    public PostDto updatePost(PostDto postDto,Long id);

    public void deletePost(Long id);

}
