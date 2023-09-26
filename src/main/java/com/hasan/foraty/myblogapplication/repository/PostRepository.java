package com.hasan.foraty.myblogapplication.repository;

import com.hasan.foraty.myblogapplication.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * PostRepository implemented by JPA
 */
public interface PostRepository extends JpaRepository<Post,Long> {

}
