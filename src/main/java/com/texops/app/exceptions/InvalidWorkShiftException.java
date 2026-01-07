package com.texops.app.exceptions;

public class InvalidWorkShiftException extends RuntimeException {
    public InvalidWorkShiftException() {
        super("The start time must be earlier than end time");
    }
}
