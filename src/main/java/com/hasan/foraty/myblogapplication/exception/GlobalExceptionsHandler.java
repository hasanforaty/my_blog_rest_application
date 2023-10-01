package com.hasan.foraty.myblogapplication.exception;

import com.hasan.foraty.myblogapplication.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  ResponseEntity<ErrorDetails> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException, WebRequest webRequest){

    ErrorDetails errorDetails = new ErrorDetails();
    Map<String ,String > errors = new HashMap<>();
    List<ObjectError> objectErrors = methodArgumentNotValidException.getBindingResult().getAllErrors();
    for (ObjectError objectError : objectErrors){
      String fieldName = ((FieldError) objectError).getField();
      String massage = objectError.getDefaultMessage();
      errors.put(fieldName,massage);
    }
    errorDetails.setMessage(String.valueOf(errors));

    errorDetails.setDetails(webRequest.getDescription(false));
    errorDetails.setTimestamp(new Date());
    return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
  }



  @ExceptionHandler
  ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception, WebRequest webRequest){

    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.setDetails(webRequest.getDescription(false));
    errorDetails.setTimestamp(new Date());
    errorDetails.setMessage(exception.getMessage());
    return new ResponseEntity<>(errorDetails,HttpStatus.UNAUTHORIZED);
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
