package com.texops.app.services;

import com.texops.app.dao.WorkShiftRepository;
import com.texops.app.dto.WorkShiftDTO;
import com.texops.app.dto.mappers.WorkShiftDTOMapper;
import com.texops.app.exceptions.InvalidWorkShiftException;
import com.texops.app.exceptions.ResourceNotFoundException;
import com.texops.app.exceptions.WorkShiftOverlapException;
import com.texops.app.models.Employee;
import com.texops.app.models.WorkShift;
import com.texops.app.util.WorkShiftUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WorkShiftService {
    private final WorkShiftRepository workShiftRepository;
    private final EmployeeService employeeService;
    private final WorkShiftDTOMapper workShiftDTOMapper;

    public WorkShiftService(WorkShiftRepository workShiftRepository, EmployeeService employeeService, WorkShiftDTOMapper workShiftDTOMapper) {
        this.workShiftRepository = workShiftRepository;
        this.employeeService = employeeService;
        this.workShiftDTOMapper = workShiftDTOMapper;
    }

    public WorkShiftDTO createWorkShift(Long employeeId, WorkShift workShift) {

        if (!WorkShiftUtils.isWorkShiftValid(workShift)) {
            throw new InvalidWorkShiftException();
        }

        List<WorkShift> existingShifts = workShiftRepository.findByEmployeeId(employeeId);

        if (WorkShiftUtils.isWorkShiftOverlaping(workShift, existingShifts)) {
            throw new WorkShiftOverlapException();
        }

        Employee employee = employeeService.getEmployee(employeeId);
        workShift.setEmployee(employee);

        return workShiftDTOMapper.apply(workShiftRepository.save(workShift));
    }

    public WorkShift getWorkShift(Long workShiftId) {
        return workShiftRepository.findById(workShiftId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkShift", "id", workShiftId));
    }

    @Transactional(readOnly = true)
    public List<WorkShiftDTO> getWorkShiftsByEmployee(Long employeeId) {
        return workShiftRepository.findByEmployeeId(employeeId)
                .stream()
                .map(workShiftDTOMapper)
                .toList();
    }

    public WorkShiftDTO updateWorkShift(Long workShiftId, WorkShift workShift) {

        if (!WorkShiftUtils.isWorkShiftValid(workShift)) {
            throw new InvalidWorkShiftException();
        }

        WorkShift workShiftToUpdate = getWorkShift(workShiftId);

        workShiftToUpdate.setShiftName(workShift.getShiftName());
        workShiftToUpdate.setStartTime(workShift.getStartTime());
        workShiftToUpdate.setEndTime(workShift.getEndTime());

        return workShiftDTOMapper.apply(workShiftRepository.save(workShiftToUpdate));
    }

    public void deleteWorkShift(Long workShiftId) {
        workShiftRepository.deleteById(workShiftId);
    }
}
