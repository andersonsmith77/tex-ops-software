package com.texops.app.exceptions;

public class InvalidDeletionException extends RuntimeException {
    public InvalidDeletionException() {
        super("Employee with work shift can not be deleted");
    }
}
