package com.texops.app.services;

import com.texops.app.dao.EmployeeRepository;
import com.texops.app.dao.WorkShiftRepository;
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

    public EmployeeService(EmployeeRepository employeeRepository, WorkShiftRepository workShiftRepository) {
        this.employeeRepository = employeeRepository;
        this.workShiftRepository = workShiftRepository;
    }

    public Employee createEmployee(Employee employee) {
        employee.setId(null);
        employee.setActive(true);

        return employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
    }

    public Employee updateEmployee(Long employeeId, Employee employee) {
        Employee employeeToUpdate = getEmployee(employeeId);

        employeeToUpdate.setCode(employee.getCode());
        employeeToUpdate.setName(employee.getName());
        employeeToUpdate.setActive(employee.getActive());

        return employeeRepository.save(employeeToUpdate);
    }

    public void deleteEmployee(Long employeeId) {
        if (workShiftRepository.existsByEmployeeId(employeeId)) {
            throw new ResourceNotFoundException("WorkShift", "id", employeeId);
        }

        employeeRepository.deleteById(employeeId);
    }
}
