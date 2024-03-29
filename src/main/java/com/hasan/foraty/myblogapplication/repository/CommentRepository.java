package com.hasan.foraty.myblogapplication.repository;

import com.hasan.foraty.myblogapplication.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment,Long> {
  Page<Comment> getCommentByPostId(long postId, Pageable pageable);
}
