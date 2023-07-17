package com.myblog.service;

import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostDto getPost(long id);

    void deletePost(long id);

    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto updatePost(PostDto postDto, long id);
}
