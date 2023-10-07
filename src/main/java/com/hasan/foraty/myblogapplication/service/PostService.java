package com.hasan.foraty.myblogapplication.service;

import com.hasan.foraty.myblogapplication.payload.PaginationResponse;
import com.hasan.foraty.myblogapplication.payload.PostDto;
import java.util.List;


public interface PostService {
    PostDto createPost(PostDto postDto);
    PaginationResponse<PostDto> getPostsWithPagination(int pageNumber,int pageSize,String sortBy,String sortDirection,long categoryId);
    List<PostDto> getAllPost();
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto,Long id);
    void deletePost(Long id);
}
