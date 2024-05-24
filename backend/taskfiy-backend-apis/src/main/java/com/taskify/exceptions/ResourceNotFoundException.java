package com.taskify.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public String resourceName;
    public String fieldName;
    public Integer fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Integer fieldValue) {
        super(String.format("%s not found with %s: %d", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
