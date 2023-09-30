package com.hasan.foraty.myblogapplication.service.impl;

import com.hasan.foraty.myblogapplication.entity.Comment;
import com.hasan.foraty.myblogapplication.entity.Post;
import com.hasan.foraty.myblogapplication.exception.BlogApiException;
import com.hasan.foraty.myblogapplication.exception.ResourceNotFoundException;
import com.hasan.foraty.myblogapplication.payload.CommentDto;
import com.hasan.foraty.myblogapplication.payload.PaginationResponse;
import com.hasan.foraty.myblogapplication.repository.CommentRepository;
import com.hasan.foraty.myblogapplication.repository.PostRepository;
import com.hasan.foraty.myblogapplication.service.CommentService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements
    CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final ModelMapper modelMapper;

  public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,
      ModelMapper modelMapper) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public CommentDto createComment(CommentDto comment, long postId) {
    Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",String.valueOf(postId)));
    Comment createdComment = mapFromDTO(comment);
    createdComment.setPost(post);
    createdComment =commentRepository.save(createdComment);

    return mapToDTO(createdComment);
  }

  @Override
  public PaginationResponse<CommentDto> getCommentsWithPagination(long postId, int pageNumber,
      int pageSize, String sortBy, String sortDirection) {
    Sort sort;
    if (sortDirection.equalsIgnoreCase(Direction.ASC.name())){
      sort = Sort.by(Direction.ASC,sortBy);
    }else if (sortDirection.equalsIgnoreCase(Direction.DESC.name())){
      sort = Sort.by(Direction.DESC,sortBy);
    }else {
      throw new ResourceNotFoundException("Sort Direction ","sortDir",sortDirection);
    }
    Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
    Page<Comment> result = commentRepository.getCommentByPostId(postId,pageable);


    return new PaginationResponse<>(
        pageNumber,
        result.getTotalPages(),
        pageSize,
        result.getTotalElements(),
        result.getContent().stream().map(this::mapToDTO).collect(Collectors.toList())
    );
  }

  @Override
  public List<CommentDto> getAllComments(long postId) {
    Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",String.valueOf(postId)));
    List<Comment> comments = post.getComments();
    return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
  }

  @Override
  public CommentDto getCommentById(long postId, long commentId) {
    Comment comment = getComment(commentId,postId);
    return mapToDTO(comment);
  }


  @Override
  public CommentDto updateComment(CommentDto commentDto,long commentId,long postId) {
    Comment comment = getComment(commentId,postId);

    comment.setName(commentDto.getName());
    comment.setBody(commentDto.getComment());
    comment.setEmail(commentDto.getEmail());


    return mapToDTO(commentRepository.save(comment));
  }

  @Override
  public String  deleteComment(long commentId,long postId) {
    Comment comment = getComment(commentId, postId);
    commentRepository.delete(comment);
    return "Comment deleted successfully";
  }



  private Comment getComment(long commentId,long postId){
    Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",String.valueOf(postId)));
    Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment ","id",String.valueOf(commentId)));
    if (!post.equals(comment.getPost())){
      throw new BlogApiException("Comment not belong to Post ",HttpStatus.BAD_REQUEST);
    }
    return comment;
  }


  private Comment mapFromDTO(CommentDto commentDto){
    //    myComment.setBody(commentDto.getComment());
//    myComment.setName(commentDto.getName());
//    myComment.setEmail(commentDto.getEmail());

    return modelMapper.map(commentDto,Comment.class);
  }
  private CommentDto mapToDTO(Comment comment){
    //    myCommentDTO.setComment(comment.getBody());
//    myCommentDTO.setName(comment.getName());
//    myCommentDTO.setEmail(comment.getEmail());
//    myCommentDTO.setId(comment.getId());
    return modelMapper.map(comment,CommentDto.class);
  }
}
