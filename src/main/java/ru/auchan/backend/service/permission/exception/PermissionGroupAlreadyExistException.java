package ru.auchan.backend.service.permission.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PermissionGroupAlreadyExistException extends RuntimeException {
    public PermissionGroupAlreadyExistException(String message) {
        super(message);
    }
}
