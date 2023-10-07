package com.hasan.foraty.myblogapplication.service;

import com.hasan.foraty.myblogapplication.payload.CategoryDto;
import com.hasan.foraty.myblogapplication.payload.PaginationResponse;
import java.util.List;

public interface CategoryService {
  CategoryDto createCategory(CategoryDto newCategory);
  CategoryDto getCategory(long id) ;
  List<CategoryDto> getAllCategory();
  PaginationResponse<CategoryDto> getAllWithPagination(int pageN,int perPage);

}
