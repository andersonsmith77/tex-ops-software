package com.texops.app.services;

import com.texops.app.dao.EmployeeRepository;
import com.texops.app.dao.WorkShiftRepository;
import com.texops.app.dto.EmployeeDTO;
import com.texops.app.dto.mappers.EmployeeDTOMapper;
import com.texops.app.exceptions.InvalidDeletionException;
import com.texops.app.exceptions.ResourceNotFoundException;
import com.texops.app.models.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final WorkShiftRepository workShiftRepository;
    private final EmployeeDTOMapper employeeDTOMapper;

    public EmployeeService(EmployeeRepository employeeRepository, WorkShiftRepository workShiftRepository, EmployeeDTOMapper employeeDTOMapper) {
        this.employeeRepository = employeeRepository;
        this.workShiftRepository = workShiftRepository;
        this.employeeDTOMapper = employeeDTOMapper;
    }

    public EmployeeDTO createEmployee(Employee employee) {
        employee.setId(null);
        employee.setActive(true);

        return employeeDTOMapper.apply(employeeRepository.save(employee));
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeDTOMapper)
                .toList();
    }

    public Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
    }

    public EmployeeDTO updateEmployee(Long employeeId, Employee employee) {
        Employee employeeToUpdate = getEmployee(employeeId);

        employeeToUpdate.setCode(employee.getCode());
        employeeToUpdate.setName(employee.getName());
        employeeToUpdate.setActive(employee.getActive());

        return employeeDTOMapper.apply(employeeRepository.save(employeeToUpdate));
    }

    public void deleteEmployee(Long employeeId) {
        if (workShiftRepository.existsByEmployeeId(employeeId)) {
            throw new InvalidDeletionException();
        }

        employeeRepository.deleteById(employeeId);
    }
}
