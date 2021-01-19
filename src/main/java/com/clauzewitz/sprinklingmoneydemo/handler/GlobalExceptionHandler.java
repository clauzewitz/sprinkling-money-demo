package com.clauzewitz.sprinklingmoneydemo.handler;

import com.clauzewitz.sprinklingmoneydemo.handler.exception.*;
import com.mongodb.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Component
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(DuplicateKeyException.class)
    public CustomError duplicateException(HttpServletRequest request, DuplicateKeyException exception) {
        return CustomError.builder()
                .path(request.getRequestURI())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(exception.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    public CustomError badRequestException(HttpServletRequest request, BadRequestException exception) {
        return CustomError.builder()
                .path(request.getRequestURI())
                .status(exception.getStatus())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(ExpiredException.class)
    public CustomError expiredException(HttpServletRequest request, ExpiredException exception) {
        return CustomError.builder()
                .path(request.getRequestURI())
                .status(exception.getStatus())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(exception.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(NoPermissionException.class)
    public CustomError noPermissionException(HttpServletRequest request, NoPermissionException exception) {
        return CustomError.builder()
                .path(request.getRequestURI())
                .status(exception.getStatus())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(exception.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(NotExsistException.class)
    public CustomError notExsistException(HttpServletRequest request, NotExsistException exception) {
        return CustomError.builder()
                .path(request.getRequestURI())
                .status(exception.getStatus())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(exception.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(AlreadyReceivedException.class)
    public CustomError alreadyReceivedException(HttpServletRequest request, AlreadyReceivedException exception) {
        return CustomError.builder()
                .path(request.getRequestURI())
                .status(exception.getStatus())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(NotReceivedException.class)
    public CustomError notReceivedException(HttpServletRequest request, NotReceivedException exception) {
        return CustomError.builder()
                .path(request.getRequestURI())
                .status(exception.getStatus())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(exception.getLocalizedMessage())
                .build();
    }
}
