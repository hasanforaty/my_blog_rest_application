package com.hasan.foraty.myblogapplication.repository;

import com.hasan.foraty.myblogapplication.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Category repository.
 */
public interface CategoryRepository extends JpaRepository<Category,Long> {

}
