package com.blog.controller;

import com.blog.dto.PostDto;
import com.blog.dto.PostResponse;
import com.blog.filters.TestFilter;
import com.blog.service.PostService;
import com.blog.utils.AppConstants;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/posts")
public class PostController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestFilter.class);
   private PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
    }
    //get all Posts
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNO,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_PAGE_SORT_BY,required = false ) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR,required = false) String sortDir
    ){

        return ResponseEntity.ok(postService.getAllPost(pageNO,pageSize,sortBy,sortDir));
    }

    //get Post by id
    @GetMapping("{postId}")
    public  ResponseEntity<PostDto> getPost(@PathVariable("postId") Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }


    //create blog Post
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED );
    }


    //update Post
    @PutMapping("{postId}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,
                                              @PathVariable("postId") Long postId){
        PostDto postDto1=postService.updatePost(postDto,postId);
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }

    //delete Post By id
    @DeleteMapping("{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") Long postId){
        postService.deletePost(postId);
        return ResponseEntity.ok("Post with Id: "+postId+" is Deleted Successfully !!!");
    }













}
