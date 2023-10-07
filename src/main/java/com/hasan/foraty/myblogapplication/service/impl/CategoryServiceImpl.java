package com.hasan.foraty.myblogapplication.service.impl;

import com.hasan.foraty.myblogapplication.entity.Category;
import com.hasan.foraty.myblogapplication.exception.ResourceNotFoundException;
import com.hasan.foraty.myblogapplication.payload.CategoryDto;
import com.hasan.foraty.myblogapplication.payload.PaginationResponse;
import com.hasan.foraty.myblogapplication.repository.CategoryRepository;
import com.hasan.foraty.myblogapplication.service.CategoryService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository repository;
  private final ModelMapper modelMapper;

  public CategoryServiceImpl(CategoryRepository repository, ModelMapper modelMapper) {
    this.repository = repository;
    this.modelMapper = modelMapper;
  }

  @Override
  public CategoryDto createCategory(CategoryDto newCategory) {
    Category theCategory = modelMapper.map(newCategory, Category.class);
    var savedCategory = repository.save(theCategory);

    return modelMapper.map(savedCategory,CategoryDto.class);
  }

  @Override
  public CategoryDto getCategory(long id) {
    return modelMapper.map(repository.findById(id).orElseThrow(()->new ResourceNotFoundException("category","id",String.valueOf(id))),CategoryDto.class);
  }

  @Override
  public List<CategoryDto> getAllCategory() {
    return repository.findAll().stream().map((ob)->modelMapper.map(ob,CategoryDto.class)).collect(
        Collectors.toList());
  }

  @Override
  public PaginationResponse<CategoryDto> getAllWithPagination(int pageN, int perPage) {
    Pageable pageable = PageRequest.of(pageN,perPage);
    Page<Category> page =repository.findAll(pageable);

    return new PaginationResponse<>(pageN,page.getTotalPages(),perPage,page.getTotalElements(),page.get().map((ob)->modelMapper.map(ob,CategoryDto.class)).collect(
        Collectors.toList()));
  }
}
