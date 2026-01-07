package com.texops.app.util;

import com.texops.app.exceptions.WorkShiftOverlapException;
import com.texops.app.models.WorkShift;

import java.util.List;

public class WorkShiftUtils {
    public static boolean isWorkShiftValid(WorkShift workShift) {
        if (workShift.getStartTime().isAfter(workShift.getEndTime()) ||
                workShift.getStartTime().equals(workShift.getEndTime())) {
            return false;
        }
        return true;
    }

    public static boolean isWorkShiftOverlaping(WorkShift workShift, List<WorkShift> existingWorkShifts) {
        for (WorkShift existing : existingWorkShifts) {
            if (workShift.getId() != null && existing.getId().equals(workShift.getId())) {
                continue;
            }

            return workShift.getStartTime().isBefore(existing.getEndTime()) &&
                    existing.getStartTime().isBefore(workShift.getEndTime());
        }
        return false;
    }
}
