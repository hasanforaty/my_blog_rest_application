package com.hasan.foraty.myblogapplication.controller;

import com.hasan.foraty.myblogapplication.payload.CommentDto;
import com.hasan.foraty.myblogapplication.payload.PaginationResponse;
import com.hasan.foraty.myblogapplication.service.CommentService;
import com.hasan.foraty.myblogapplication.utils.AppConstance;
import java.util.List;
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

@RestController
@RequestMapping("/api/posts/{post_id}/comments")
public class CommentController {

  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @PostMapping
  public ResponseEntity<CommentDto> createComment(@PathVariable(name = "post_id") long postId,@RequestBody CommentDto commentDto){
    return ResponseEntity.ok(commentService.createComment(commentDto,postId));
  }
  @GetMapping("/all")
  public ResponseEntity<List<CommentDto>> getAllComment(@PathVariable(name = "post_id")long postId){
    return ResponseEntity.ok(commentService.getAllComments(postId));
  }

  @GetMapping
  ResponseEntity<PaginationResponse<CommentDto>> getComment(
      @PathVariable(name = "post_id") long postId,
      @RequestParam(value = "pageNumber",defaultValue = AppConstance.DEFAULT_PAGE_NUMBER,required = false) int pageNum,
      @RequestParam(value = "pageSize",defaultValue = AppConstance.DEFAULT_PAGE_SIZE,required = false)int pageSize,
      @RequestParam(value = "sortBy",defaultValue = AppConstance.DEFAULT_SORT_BY,required = false)String sortBy,
      @RequestParam(value = "sortDir",defaultValue = AppConstance.DEFAULT_SORT_DIRECTION,required = false)String sortDir
  ){

    return ResponseEntity.ok(commentService.getCommentsWithPagination(postId,pageNum,pageSize,sortBy,sortDir));
  }
  @GetMapping("/{comment_id}")
  ResponseEntity<CommentDto> getCommentById(
      @PathVariable(name = "post_id") long postId,
      @PathVariable(name = "comment_id")long commentId
  ){
    return ResponseEntity.ok(commentService.getCommentById(postId,commentId));
  }

  @PutMapping("/{comment_id}")
  ResponseEntity<CommentDto> updateComment(
      @PathVariable("post_id")long postId,
      @PathVariable("comment_id")long commentId,
      @RequestBody CommentDto commentDto
  ){
    return ResponseEntity.ok(commentService.updateComment(commentDto,commentId,postId));
  }
  @DeleteMapping("/{comment_id}")
  ResponseEntity<String> deleteComment(
      @PathVariable("post_id")long postId,
      @PathVariable("comment_id")long commentId
  ){
    return ResponseEntity.ok(commentService.deleteComment(commentId,postId));
  }
}
