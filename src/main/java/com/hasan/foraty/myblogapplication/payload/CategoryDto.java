package com.hasan.foraty.myblogapplication.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

  private long id;
  private String name;
  private String description;


}
