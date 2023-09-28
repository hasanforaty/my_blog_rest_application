package com.hasan.foraty.myblogapplication.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "post")
public class Post {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(name = "title",unique = true,nullable = false)
  private String title;

  @Column(name = "description",nullable = false)
  private String description;

  @Column(name = "content",nullable = false)
  private String content;

  @OneToMany(mappedBy = "post",cascade = {CascadeType.ALL})
  List<Comment> comments;

  public void addComment(Comment comment){
    if(comments==null){
      comments = new ArrayList<>();
    }
    comments.add(comment);
  }



}
