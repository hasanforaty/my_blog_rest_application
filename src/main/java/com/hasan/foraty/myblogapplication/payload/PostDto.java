package com.hasan.foraty.myblogapplication.payload;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
  private Long id;
  private String title;
  private String description;
  private String content;
  private List<CommentDto> comments;
}
