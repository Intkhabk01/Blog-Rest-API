package com.blog.service.impl;

import com.blog.dto.CommentDto;
import com.blog.dto.PostDto;
import com.blog.dto.PostResponse;
import com.blog.exception.BlogAPIException;
import com.blog.exception.ResourceNotFoundException;
import com.blog.model.Comment;
import com.blog.model.Post;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<CommentDto> getAllComment(Long postId) {
        //recieve Comment by postId
        List<Comment> comments=commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long cId) {

        Post post=postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post","Id",postId)
        );
        Comment comment=commentRepository.findById(cId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment","Id",cId)
        );
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment doesnot belongs to post");
        }

        return mapToDTO(comment);
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {

        Comment comment=mapToEntity(commentDto);
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","Id",postId)
        );
        //set post to entity
        comment.setPost(post);
        //save to database
        Comment newComment=commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    @Override
    public CommentDto updateCommentById(Long postId, Long cId,CommentDto commentDto) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","Id",postId)
        );
        Comment comment=commentRepository.findById(cId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment","Id",cId)
        );

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment doesnot belongs to post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment=commentRepository.save(comment);

        return mapToDTO(comment);
    }

    @Override
    public void deleteCommentById(Long postId, Long cId) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","Id",postId)
        );
        Comment comment=commentRepository.findById(cId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment","Id",cId)
        );

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment doesnot belongs to post");
        }
        commentRepository.deleteById(cId);
    }


    //convert entity to Dto
    private CommentDto mapToDTO(Comment comment){
        CommentDto commentDto=modelMapper.map(comment,CommentDto.class);
        return commentDto;
    }

    //convert Dto to entity
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment=modelMapper.map(commentDto,Comment.class);
        return comment;
    }


    /*
    //convert entity to Dto
    private CommentDto mapToDTO(Comment comment){
        CommentDto commentDto=new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    //convert Dto to entity
    private Comment mapToEntity(CommentDto commentDto){
        Post post=new Post();
        Comment comment=new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
    */

}
