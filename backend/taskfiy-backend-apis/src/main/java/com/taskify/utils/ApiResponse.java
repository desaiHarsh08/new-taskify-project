package com.taskify.utils;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private int status;
    private HttpStatus httpStatus;
    private String message;
    private Object payload;
    private boolean success;
}
