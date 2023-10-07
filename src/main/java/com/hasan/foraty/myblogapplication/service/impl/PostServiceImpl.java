package com.hasan.foraty.myblogapplication.service.impl;

import com.hasan.foraty.myblogapplication.entity.Category;
import com.hasan.foraty.myblogapplication.entity.Post;
import com.hasan.foraty.myblogapplication.exception.ResourceNotFoundException;
import com.hasan.foraty.myblogapplication.payload.PaginationResponse;
import com.hasan.foraty.myblogapplication.payload.PostDto;
import com.hasan.foraty.myblogapplication.repository.CategoryRepository;
import com.hasan.foraty.myblogapplication.repository.PostRepository;
import com.hasan.foraty.myblogapplication.service.PostService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements
    PostService {
  private final PostRepository postRepository;
  private final ModelMapper modelMapper;
  private final CategoryRepository categoryRepository;
  public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper,
      CategoryRepository categoryRepository) {
    this.postRepository = postRepository;
    this.modelMapper = modelMapper;
    this.categoryRepository = categoryRepository;
  }


  @Override
  public PostDto createPost(PostDto postDto) {

    Post myPost = modelMapper.map(postDto,Post.class);
    Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
        ()->new ResourceNotFoundException("category","categoryId",String.valueOf(postDto.getCategoryId())));
    myPost.setCategory(category);
    Post post = postRepository.save(myPost);
    postDto.setId(post.getId());;
    return postDto;
  }

  @Override
  public PaginationResponse<PostDto> getPostsWithPagination(int pageNumber,int pageSize,String sortBy,String sortDirection,long categoryId) {

    //check for sort by Direction.
    Direction direction;
    if(sortDirection.equalsIgnoreCase("asc")){
      direction=Direction.ASC;
    }else if (sortDirection.equalsIgnoreCase("desc")){
      direction=Direction.DESC;
    }else {
      throw new ResourceNotFoundException(
          "Direction","sortBy",sortDirection
      );
    }

    Pageable pageable = PageRequest.of(pageNumber,pageSize,Sort.by(direction,sortBy));

    Page<Post> pages;
    if(categoryId==-1){
      pages = postRepository.findAll(pageable);
    }else {
      Category category =categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",String.valueOf(categoryId)));
      pages = postRepository.findAllByCategory(category,pageable);
    }
    return new PaginationResponse<>(
        pageNumber,
        pages.getTotalPages(),
        pageSize,
        pages.getTotalElements(),
        pages.getContent().stream().map(this::mapToDTO).collect(Collectors.toList())
        );
  }

  @Override
  public List<PostDto> getAllPost() {
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
    postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post ","id",String.valueOf(id)));
    Category category =categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",String.valueOf(postDto.getCategoryId())));
    Post post = modelMapper.map(postDto,Post.class);
    post.setCategory(category);
    postRepository.save(post);
    return postDto;
  }


  @Override
  public void deletePost(Long id) {
    Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post ","id",String.valueOf(id)));
    postRepository.delete(post);
  }


  private PostDto mapToDTO(Post post){
    return modelMapper.map(post,PostDto.class);
  }
  private Post mapToEntity(PostDto postDto){
    //    myPost.setContent(postDto.getContent());
//    myPost.setDescription(postDto.getDescription());
//    myPost.setTitle(postDto.getTitle());

    return modelMapper.map(postDto,Post.class);
  }
}
