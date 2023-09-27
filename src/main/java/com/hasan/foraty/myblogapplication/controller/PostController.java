package com.hasan.foraty.myblogapplication.controller;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }


  @PostMapping
  ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
    return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
  }

  @GetMapping
  ResponseEntity<List<PostDto>> getPosts(){
    return new ResponseEntity<>(postService.getAllPosts(),HttpStatus.OK);
  }
  @GetMapping("/{id}")
  ResponseEntity<PostDto> getPostById(@PathVariable long id){
    return ResponseEntity.ok(postService.getPostById(id));
  }
  @PutMapping("/{id}")
  ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable long id){
    return ResponseEntity.ok(postService.updatePost(postDto,id));
  }
  @DeleteMapping("/{id}")
  ResponseEntity<String> deletePost(@PathVariable Long id){
    postService.deletePost(id);
    return new ResponseEntity<>("Post Delete successfully",HttpStatus.OK);
  }

}
