package com.hasan.foraty.myblogapplication.config;

import com.hasan.foraty.myblogapplication.entity.Comment;
import com.hasan.foraty.myblogapplication.payload.CommentDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;

@org.springframework.context.annotation.Configuration
public class Configuration {
  @Bean
  public ModelMapper getModelMapper(){
    ModelMapper modelMapper =new ModelMapper();
    //Add configurations
    modelMapper.typeMap(Comment.class, CommentDto.class).addMappings(
        mapper->{
          mapper.map(Comment::getBody,CommentDto::setComment);
        }
    );
    modelMapper.typeMap(CommentDto.class,Comment.class).addMappings(
        mapper->{
          mapper.map(CommentDto::getComment,Comment::setBody);
        }
    );

    return modelMapper;
  }
}
