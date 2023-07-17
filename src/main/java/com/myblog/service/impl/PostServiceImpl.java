package com.myblog.service.impl;

import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFoundException;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.repositories.PostRepository;
import com.myblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToEntity(postDto);

        Post newpost = postRepository.save(post);

        PostDto newPostDto = mapToDto(newpost);

        return newPostDto;
    }

    @Override
    public PostDto getPost(long id) {

        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "Id", id)
        );

//        Post post = byId.get();

        PostDto postDto = mapToDto(post);
        return postDto;
    }

    @Override
    public void deletePost(long id) {

        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "Id", id)

        );
        postRepository.deleteById(id);
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Page<Post> allPost = postRepository.findAll(pageable);
        List<Post> content = allPost.getContent();


        List<PostDto> allPostDto = content.stream().map(s -> mapToDto(s)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(allPostDto);
        postResponse.setPageNo(allPost.getNumber());
        postResponse.setTotalPages(allPost.getTotalPages());
        postResponse.setPageSize(allPost.getSize());
        postResponse.setTotalElements(allPost.getTotalElements());
        postResponse.setLast(allPost.isLast());

        return postResponse;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "Id", id)
        );

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);

    }


    private PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        postDto.setContent(postDto.getContent());
        return post;
    }









}
