package com.texops.app.dto;

import java.time.LocalTime;

public record WorkShiftDTO(
        Long id,
        String shiftName,
        LocalTime startTime,
        LocalTime endTime,
        Long employeeId,
        String employeeName
) { }
