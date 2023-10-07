package com.hasan.foraty.myblogapplication.controller;

import com.hasan.foraty.myblogapplication.payload.CategoryDto;
import com.hasan.foraty.myblogapplication.payload.PaginationResponse;
import com.hasan.foraty.myblogapplication.service.CategoryService;
import com.hasan.foraty.myblogapplication.utils.AppConstance;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping
  ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto theCategoryDTO){
    theCategoryDTO.setId(null);
    CategoryDto savedCategory = categoryService.createCategory(theCategoryDTO);
    return ResponseEntity.ok(savedCategory);
  }

  @GetMapping("/{id}")
  ResponseEntity<CategoryDto> getCategory(@PathVariable(name = "id")long id)
  {
    return ResponseEntity.ok(categoryService.getCategory(id));
  }
  @GetMapping
  ResponseEntity<PaginationResponse<CategoryDto>> getCategories(
      @RequestParam(value = "pageNumber",defaultValue = AppConstance.DEFAULT_PAGE_NUMBER,required = false) int pageNum,
      @RequestParam(value = "pageSize",defaultValue = AppConstance.DEFAULT_PAGE_SIZE,required = false)int pageSize
  ){
    return  ResponseEntity.ok(categoryService.getAllWithPagination(pageNum,pageSize));
  }
  @GetMapping("/all")
  ResponseEntity<List<CategoryDto>> getAllCategory(){
    return ResponseEntity.ok(categoryService.getAllCategory());
  }
  @PutMapping("/{id}")
  ResponseEntity<CategoryDto> updateCategory(
      @PathVariable(name = "id")long id,
      @RequestBody CategoryDto categoryDto
  ){
    return ResponseEntity.ok(categoryService.updateCategory(id,categoryDto));
  }
}
