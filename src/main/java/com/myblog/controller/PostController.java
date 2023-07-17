package com.myblog.controller;

import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody  PostDto postDto){

        PostDto savedPostDto = postService.createPost(postDto);

        return new ResponseEntity<>(savedPostDto, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable("id") long id){

        PostDto getPostDto = postService.getPost(id);

        return new ResponseEntity<>(getPostDto,HttpStatus.OK);
    }

    @GetMapping
    public PostResponse getAllPost(@RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
                                   @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
                                   @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
                                   @RequestParam(value = "sortDir",defaultValue = "",required = false) String sortDir){

        PostResponse allPost = postService.getAllPost(pageNo,pageSize,sortBy,sortDir);

        return allPost;
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){

        postService.deletePost(id);

        return new ResponseEntity<>("Post is deleted !!. ",HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto ,@PathVariable("id") long id){

        PostDto getPostDto = postService.updatePost(postDto,id);

        return new ResponseEntity<>(getPostDto,HttpStatus.OK);
    }



}
