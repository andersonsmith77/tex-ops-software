package com.texops.app.dto.mappers;

import com.texops.app.dto.EmployeeDTO;
import com.texops.app.dto.WorkShiftDTO;
import com.texops.app.models.Employee;
import com.texops.app.models.WorkShift;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class WorkShiftDTOMapper implements Function<WorkShift, WorkShiftDTO> {
    @Override
    public WorkShiftDTO apply(WorkShift workShift) {
        return new WorkShiftDTO(
                workShift.getId(),
                workShift.getShiftName(),
                workShift.getStartTime(),
                workShift.getEndTime(),
                workShift.getEmployee().getId(),
                workShift.getEmployee().getName()
        );
    }
}
