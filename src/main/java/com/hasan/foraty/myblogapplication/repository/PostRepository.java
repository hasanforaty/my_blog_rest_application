package com.hasan.foraty.myblogapplication.repository;

import com.hasan.foraty.myblogapplication.entity.Category;
import com.hasan.foraty.myblogapplication.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * PostRepository implemented by JPA
 */
public interface PostRepository extends JpaRepository<Post,Long> {
  Page<Post> findAllByCategory(Category category, Pageable pageable);
  Page<Post> findByCategoryId(long categoryId,Pageable pageable);
}
