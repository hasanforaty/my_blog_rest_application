package com.hasan.foraty.myblogapplication.service;

import com.hasan.foraty.myblogapplication.payload.PostDto;
import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts();
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto,Long id);
    void deletePost(Long id);
}
