package com.texops.app.exceptions;

public class WorkShiftOverlapException extends RuntimeException {
    public WorkShiftOverlapException() {
        super("Cannot assign Work shift: It overlaps with an existing shift");
    }
}
