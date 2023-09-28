package com.hasan.foraty.myblogapplication.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BlogApiException extends RuntimeException{

  public BlogApiException(String message, HttpStatus status) {
    super(message);
    this.status = status;
    this.message = message;
  }

  private HttpStatus status;
  private String message;

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "BlogApiException{" +
        "status=" + status +
        ", message='" + message + '\'' +
        '}';
  }
}
