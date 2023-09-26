package com.hasan.foraty.myblogapplication.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Resource not found exception.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
public class ResourceNotFoundException extends RuntimeException{
  private final String resourceName;
  private final String fieldName;
  private final String fieldValue;

  /**
   * Instantiates a new Resource not found exception.
   *
   * @param resourceName the resource name
   * @param fieldName    the field name
   * @param fieldValue   the field value
   */
  public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
    super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldValue));
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }
}
