package com.hasan.foraty.myblogapplication.payload;

import java.util.List;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

  private Long id;

  @NotEmpty
  @Size(min = 2,message = "Post title should have at least 2 character")
  private String title;
  //should not be null or empty
  //should be at least 5 character
  @NotEmpty
  @Size(min = 5,message = "Post description should have at least 5 character")
  private String description;
  //should not be null or empty
  @NotEmpty
  private String content;
  private List<CommentDto> comments;
}
