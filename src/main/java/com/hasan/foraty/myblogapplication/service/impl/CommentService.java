package com.hasan.foraty.myblogapplication.service.impl;

import com.hasan.foraty.myblogapplication.payload.CommentDto;
import com.hasan.foraty.myblogapplication.payload.PaginationResponse;
import java.util.List;

public interface CommentService {
  CommentDto createComment(CommentDto comment,long postId);
  PaginationResponse<CommentDto> getCommentsWithPagination(long postId,int pageNumber,int pageSize,String sortBy,String sortDirection);
  List<CommentDto> getAllComments(long postId);
  CommentDto getCommentById(long postId,long commentId);
  CommentDto updateComment(CommentDto comment,long commentId,long postId);
  String  deleteComment(long commentId,long postId);

}
