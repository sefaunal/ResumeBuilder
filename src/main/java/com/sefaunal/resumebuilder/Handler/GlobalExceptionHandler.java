package com.sefaunal.resumebuilder.Handler;

import com.sefaunal.resumebuilder.Exception.PasswordException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author github.com/sefaunal
 * @since 2024-01-14
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        // Get the validation errors from the exception
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        // Build the error response
        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errorMessages.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
        }

        LOG.error("Error -> Status: {}, Message: {}", 400, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(Exception ex) {
        LOG.error("Error -> Status: {}, Message: {}", 403, ex.getMessage());
        return "Error403";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handlerNotFoundException(Exception ex) {
        LOG.error("Error -> Status: {}, Message: {}", 404, ex.getMessage());
        return "Error404";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex) {
        LOG.error("Error -> Status: {}, Message: {}", 500, ex.getMessage());
        return "Error.html";
    }

    @ExceptionHandler(PasswordException.class)
    public RedirectView handlePasswordException(Exception e) {
        LOG.error("Error: {}", e.getMessage());

        return new RedirectView("/user/resume/details/pass");
    }
}
