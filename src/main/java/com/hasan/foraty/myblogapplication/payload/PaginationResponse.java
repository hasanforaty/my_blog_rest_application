package com.hasan.foraty.myblogapplication.payload;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginationResponse<T> {
  int page;
  int page_count;
  int per_page;
  long total_count;
  List<T> data;

}
