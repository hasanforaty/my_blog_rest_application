package com.hasan.foraty.myblogapplication.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
  private long id;
  private String comment;
  private String email;
  private String name;
}
