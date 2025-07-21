package app.exception;

import app.dto.GeneralErrorResponse;
import app.util.ErrorResponseUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<GeneralErrorResponse> badRequestExceptionHandler(BadRequestException ex) {
        return ErrorResponseUtil.build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralErrorResponse> validationExceptionHandler(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return ErrorResponseUtil.build(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<GeneralErrorResponse> conflictExceptionHandler(ConflictException ex) {
        return ErrorResponseUtil.build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GeneralErrorResponse> notFoundExceptionHandler(NotFoundException ex) {
        return ErrorResponseUtil.build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<GeneralErrorResponse> forbiddenExceptionHandler(ForbiddenException ex) {
        return ErrorResponseUtil.build(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GeneralErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());
        return ErrorResponseUtil.build(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralErrorResponse> handleGeneralException(Exception ex) {
        return ErrorResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
