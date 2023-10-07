package com.hasan.foraty.myblogapplication.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
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

  @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
  @JoinColumn(name = "category_id")
  private Category category;

  public void addComment(Comment comment){
    if(comments==null){
      comments = new ArrayList<>();
    }
    comments.add(comment);
  }



}
