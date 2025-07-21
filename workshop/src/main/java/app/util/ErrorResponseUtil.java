package app.util;

import app.dto.GeneralErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

public class ErrorResponseUtil {
    public static ResponseEntity<GeneralErrorResponse> build(HttpStatus status, String message) {
        return new ResponseEntity<>(
                new GeneralErrorResponse(status.value(), Collections.singletonList(message)),
                status
        );
    }

    public static ResponseEntity<GeneralErrorResponse> build(HttpStatus status, List<String> errors) {
        return new ResponseEntity<>(
                new GeneralErrorResponse(status.value(), errors),
                status
        );
    }
}
