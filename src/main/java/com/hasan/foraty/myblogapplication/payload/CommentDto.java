package com.hasan.foraty.myblogapplication.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
  private long id;
  @NotEmpty
  @Size(min = 10,message = "Comment must have at least 10 character")
  private String comment;
  @NotEmpty
  @Email
  private String email;
  @NotEmpty
  private String name;
}
