package com.blog.service.impl;

import com.blog.dto.PostDto;
import com.blog.dto.PostResponse;
import com.blog.exception.ResourceNotFoundException;
import com.blog.model.Post;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public PostResponse getAllPost(int pageNo,int pazeSize, String sortBy,String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        //create pagable instance
        //Pageable pageable= PageRequest.of(pageNo,pazeSize);
        //Pageable pageable=PageRequest.of(pageNo,pazeSize, Sort.by(sortBy));
        Pageable pageable=PageRequest.of(pageNo,pazeSize,sort);

        Page<Post> posts=postRepository.findAll(pageable);
        //List<Post> posts=postRepository.findAll();

        //get content for page object
        List<Post> listOfPosts=posts.getContent();

        List<PostDto> content= listOfPosts.stream().map(post->mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPage(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return  postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post=postRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        return mapToDTO(post);
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //convert DTO to entity
        Post post=mapToEntity(postDto);
        post=postRepository.save(post);

        //convert entity to DTO
        PostDto postResponse = mapToDTO(post);

        return  postResponse;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post=postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post","Id",id)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post=postRepository.save(post);

        return mapToDTO(post);
    }
    //Delete post by id
    @Override
    public void deletePost(Long id) {
        Post post=postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post","Id",id)
        );
        postRepository.deleteById(id);
    }


    //Convert entity to DTO
    private PostDto mapToDTO(Post post){
        PostDto postDto=modelMapper.map(post,PostDto.class);
        return postDto;
    }

    //Conver DTO to Entity
    private Post mapToEntity(PostDto postDto){
        Post post=modelMapper.map(postDto,Post.class);
        return post;
    }


    /*
    //convert entity to Dto
    private PostDto mapToDTO(Post post){
        PostDto postDto=new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;

    }

    //convert Dto to entity
    private Post mapToEntity(PostDto postDto){
        Post post=new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
    */

}
