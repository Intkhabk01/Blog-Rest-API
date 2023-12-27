package com.blog.service;

import com.blog.dto.CommentDto;
import com.blog.model.Comment;

import java.util.List;

public interface CommentService {

    public List<CommentDto> getAllComment(Long postId);
    public CommentDto getCommentById(Long postId,Long cId);
    public CommentDto createComment(Long postId,CommentDto comment);
    public CommentDto updateCommentById(Long postId,Long cId,CommentDto commentDto);
    public void deleteCommentById(Long postId,Long cId);

}
