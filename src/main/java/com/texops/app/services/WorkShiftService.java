package com.texops.app.services;

import com.texops.app.dao.WorkShiftRepository;
import com.texops.app.exceptions.ResourceNotFoundException;
import com.texops.app.exceptions.WorkShiftOverlapException;
import com.texops.app.models.Employee;
import com.texops.app.models.WorkShift;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WorkShiftService {
    private final WorkShiftRepository workShiftRepository;
    private final EmployeeService employeeService;

    public WorkShiftService(WorkShiftRepository workShiftRepository, EmployeeService employeeService) {
        this.workShiftRepository = workShiftRepository;
        this.employeeService = employeeService;
    }

    public WorkShift createWorkShift(Long employeeId, WorkShift workShift) {
        if (workShift.getStartTime().isAfter(workShift.getEndTime()) ||
                workShift.getStartTime().equals(workShift.getEndTime())) {
            throw new IllegalArgumentException("The start time must be strictly earlier than the end time.");
        }

        Employee employee = employeeService.getEmployee(employeeId);

        workShift.setEmployee(employee);

        List<WorkShift> existingShifts = workShiftRepository.findByEmployeeId(employeeId);

        for (WorkShift existing : existingShifts) {

            if (workShift.getId() != null && existing.getId().equals(workShift.getId())) {
                continue;
            }

            boolean overlaps = workShift.getStartTime().isBefore(existing.getEndTime()) &&
                    existing.getStartTime().isBefore(workShift.getEndTime());

            if (overlaps) {
                throw new WorkShiftOverlapException();
            }
        }

        return workShiftRepository.save(workShift);
    }

    public WorkShift getWorkShift(Long workShiftId) {
        return workShiftRepository.findById(workShiftId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkShift", "id", workShiftId));
    }

    @Transactional(readOnly = true)
    public List<WorkShift> getWorkShiftsByEmployee(Long employeeId) {
        return workShiftRepository.findByEmployeeId(employeeId);
    }

    public WorkShift updateWorkShift(Long workShiftId, WorkShift workShift) {
        WorkShift workShiftToUpdate = getWorkShift(workShiftId);

        workShiftToUpdate.setShiftName(workShift.getShiftName());
        workShiftToUpdate.setStartTime(workShift.getStartTime());
        workShiftToUpdate.setEndTime(workShift.getEndTime());

        return workShiftRepository.save(workShiftToUpdate);
    }

    public void deleteWorkShift(Long workShiftId) {
        workShiftRepository.deleteById(workShiftId);
    }
}
