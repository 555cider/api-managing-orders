package com.github.prgrms.errors;

import static com.github.prgrms.utils.ApiUtils.error;

import javax.validation.ConstraintViolationException;

import com.github.prgrms.utils.ApiUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GeneralExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private ResponseEntity<ApiUtils.ApiResult<?>> newResponse(Throwable throwable, HttpStatus status) {
        return this.newResponse(throwable.getMessage(), status);
    }

    private ResponseEntity<ApiUtils.ApiResult<?>> newResponse(String message, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(error(message, status), headers, status);
    }

    @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class, ConstraintViolationException.class,
            BindException.class, MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, OrderException.class, ReviewException.class,
            ProductException.class })
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        return this.newResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(Exception e) {
        this.LOGGER.debug("로그인 아이디/비밀번호 미일치 (HTTP STATUS 401): {}", e.getMessage(), e);
        return this.newResponse(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ NoHandlerFoundException.class, NotFoundException.class })
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        return this.newResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowedException(Exception e) {
        return this.newResponse(e, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<?> handleHttpMediaTypeException(Exception e) {
        return this.newResponse(e, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler({ Exception.class, RuntimeException.class })
    public ResponseEntity<?> handleException(Exception e) {
        this.LOGGER.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return this.newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}