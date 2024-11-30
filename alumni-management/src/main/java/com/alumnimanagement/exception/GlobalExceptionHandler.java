package com.alumnimanagement.exception;

import com.alumnimanagement.web.dto.APIResponseDTO;
import com.alumnimanagement.web.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    static final String MESSAGE = "message";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponseDTO<ErrorResponseDTO>> internalServerExceptionHandler(Exception exception, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, exception.getMessage());

        APIResponseDTO<ErrorResponseDTO> apiResponseDTO = APIResponseDTO
                .<ErrorResponseDTO>builder()
                .payload(new ErrorResponseDTO(errors, request.getDescription(false)))
                .build();
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
