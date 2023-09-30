package com.hasan.foraty.myblogapplication.exception;

import com.hasan.foraty.myblogapplication.payload.ErrorDetails;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionsHandler {

  @ExceptionHandler
  ResponseEntity<ErrorDetails> HandleResourceNotFound(ResourceNotFoundException exception,
      WebRequest webRequest){
    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.setDetails(webRequest.getDescription(false));
    errorDetails.setTimestamp(new Date());
    errorDetails.setMessage(exception.getMessage());
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  ResponseEntity<ErrorDetails> HandleBlogApiException(BlogApiException exception,WebRequest webRequest){
    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.setDetails(webRequest.getDescription(false));
    errorDetails.setTimestamp(new Date());
    errorDetails.setMessage(exception.getMessage());

    return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  ResponseEntity<ErrorDetails> HandleGlobalException(Exception exception,WebRequest webRequest){
    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.setDetails(webRequest.getDescription(false));
    errorDetails.setTimestamp(new Date());
    errorDetails.setMessage(exception.getMessage());

    return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
