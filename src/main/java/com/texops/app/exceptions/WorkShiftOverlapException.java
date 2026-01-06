package com.texops.app.exceptions;

import com.texops.app.models.WorkShift;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WorkShiftOverlapException extends RuntimeException {
    public WorkShiftOverlapException() {
        super("Cannot assign Work shift: It overlaps with an existing shift");
    }
}
