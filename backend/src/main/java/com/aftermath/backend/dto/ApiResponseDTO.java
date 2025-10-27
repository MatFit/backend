package com.aftermath.backend.dto;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;


@Getter
@Setter
public class ApiResponseDTO<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private String message;
    private HttpStatus status;
    private Boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object error;
    private final String timestamp = LocalDateTime.now().toString();

    // Default constructor
    public ApiResponseDTO() {}

    // Constructor for success response
    public ApiResponseDTO(T data) {
        this.data = data;
        this.success = true;
        this.status = HttpStatus.OK;
        this.message = "Request was successful";
    }

    // Constructor for error response
    public ApiResponseDTO(String message, Object error, HttpStatus status) {
        this.message = message;
        this.error = error;
        this.status = status;
        this.success = false;
    }

    public ApiResponseDTO(boolean b, String localizedMessage) {
        this.message = localizedMessage;
        this.error = error;
        this.status = status;
        this.success = b;
    }

    // Static method to create a success response
    public static <T> ApiResponseDTO<T> success(T data) {
        return new ApiResponseDTO<>(data);
    }

    // Static method to create an error response
    public static <T> ApiResponseDTO<T> error(String message, Object error, HttpStatus status) {
        return new ApiResponseDTO<>(message, error, status);
    }

    // Convert to ResponseEntity
    public ResponseEntity<ApiResponseDTO<T>> toResponseEntity() {
        return ResponseEntity.status(this.status).body(this);
    }

}