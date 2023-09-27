package com.hasan.foraty.myblogapplication.service.impl;

import com.hasan.foraty.myblogapplication.entity.Post;
import com.hasan.foraty.myblogapplication.exception.ResourceNotFoundException;
import com.hasan.foraty.myblogapplication.payload.PostDto;
import com.hasan.foraty.myblogapplication.repository.PostRepository;
import com.hasan.foraty.myblogapplication.service.PostService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements
    PostService {
  private final PostRepository postRepository;

  public PostServiceImpl(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public PostDto createPost(PostDto postDto) {
    Post myPost = mapToEntity(postDto);
    Post post = postRepository.save(myPost);
    postDto.setId(post.getId());
    return postDto;
  }

  @Override
  public List<PostDto> getAllPosts() {
    return postRepository.findAll().stream().map(this::mapToDTO).collect(
        Collectors.toList());
  }

  @Override
  public PostDto getPostById(Long id) {
    var getPost = postRepository.findById(id);
    return mapToDTO(getPost.orElseThrow(()->new ResourceNotFoundException("Get Post ","id",id.toString())));
  }

  @Override
  public PostDto updatePost(PostDto postDto, Long id) {
    Post post =postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post ","id",String.valueOf(id)));
    post.setTitle(postDto.getTitle());
    post.setDescription(postDto.getDescription());
    post.setContent(postDto.getContent());
    Post requestPost = postRepository.save(post);


    return mapToDTO(requestPost);
  }

  @Override
  public void deletePost(Long id) {
    Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post ","id",String.valueOf(id)));
    postRepository.delete(post);
  }


  private PostDto mapToDTO(Post post){
    return new PostDto(post.getId(),post.getTitle(),post.getDescription(),post.getContent());
  }
  private Post mapToEntity(PostDto postDto){
    Post myPost = new Post();
    myPost.setContent(postDto.getContent());
    myPost.setDescription(postDto.getDescription());
    myPost.setTitle(postDto.getTitle());
    return myPost;
  }
}
