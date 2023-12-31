package com.blog.controller;

import com.blog.dto.CommentDto;
import com.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentByPostId(@PathVariable Long postId,
                                                               @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentService.getAllComment(postId));
    }
    @GetMapping("{postId}/comments/{cId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") Long postId,
                                                      @PathVariable("cId") Long cId){
        return ResponseEntity.ok(commentService.getCommentById(postId,cId));
    }

    @PutMapping("/{postId}/comments/{cId}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable("postId") Long postId,
                                                        @PathVariable("cId") Long cId,
                                                        @Valid @RequestBody CommentDto commentDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.updateCommentById(postId,cId,commentDto));
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<CommentDto> addComment(@PathVariable("postId")Long postId,
                                                 @Valid @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentService.createComment(postId,commentDto));
    }

    @DeleteMapping("{postId}/comments/{cId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId")Long postId,
                                                @PathVariable("cId") Long cId){
        commentService.deleteCommentById(postId,cId);
        return new ResponseEntity<>("Comment Deleted with Id: "+cId+" Successfully",HttpStatus.OK);
    }
}
