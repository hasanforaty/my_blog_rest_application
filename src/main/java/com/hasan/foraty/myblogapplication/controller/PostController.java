package com.hasan.foraty.myblogapplication.controller;

import com.hasan.foraty.myblogapplication.payload.PaginationResponse;
import com.hasan.foraty.myblogapplication.payload.PostDto;
import com.hasan.foraty.myblogapplication.service.PostService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Post controller.
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {

  private final PostService postService;

  /**
   * Instantiates a new Post controller.
   *
   * @param postService the post service
   */
  public PostController(PostService postService) {
    this.postService = postService;
  }


  /**
   * Create post response entity.
   *
   * @param postDto the post dto
   * @return the response entity
   */
  @PostMapping
  ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
    return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
  }

  /**
   * Gets get All of Post with pagination and sort Support.
   *
   * @param pageNum  Number of current page with default = 0
   * @param pageSize size of every request default = 10
   * @param sortBy   with which properties the result must be sorted
   * @param sortDir  sort direction for Ascending use 'asc' and for Descending use 'decs' default = 'asc'
   * @return the posts
   */
  @GetMapping
  ResponseEntity<PaginationResponse<PostDto>> getPosts(
      @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNum,
      @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
      @RequestParam(value = "sortBy",defaultValue = "id",required = false)String sortBy,
      @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
      ){

    return new ResponseEntity<>(postService.getPostsWithPagination(pageNum,pageSize,sortBy,sortDir),HttpStatus.OK);
  }

  /**
   * Get all post response entity.
   *
   * @return the response entity
   */
  @GetMapping("/all")
  ResponseEntity<List<PostDto>> getAllPost(){

    return ResponseEntity.ok(postService.getAllPost());
  }

  /**
   * Get post by id response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @GetMapping("/{id}")
  ResponseEntity<PostDto> getPostById(@PathVariable long id){
    return ResponseEntity.ok(postService.getPostById(id));
  }

  /**
   * Update post response entity.
   *
   * @param postDto the post dto
   * @param id      the id
   * @return the response entity
   */
  @PutMapping("/{id}")
  ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable long id){
    return ResponseEntity.ok(postService.updatePost(postDto,id));
  }

  /**
   * Delete post response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @DeleteMapping("/{id}")
  ResponseEntity<String> deletePost(@PathVariable Long id){
    postService.deletePost(id);
    return new ResponseEntity<>("Post Delete successfully",HttpStatus.OK);
  }

}
