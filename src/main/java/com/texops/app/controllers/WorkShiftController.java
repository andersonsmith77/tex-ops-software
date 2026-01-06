package com.texops.app.controllers;

import com.texops.app.models.WorkShift;
import com.texops.app.services.WorkShiftService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work_shifts")
public class WorkShiftController {
    private final WorkShiftService workShiftService;

    public WorkShiftController(WorkShiftService workShiftService) {
        this.workShiftService = workShiftService;
    }

    @PostMapping("/employees/{employeeId}")
    public ResponseEntity<WorkShift> createWorkShift(@PathVariable("employeeId") Long employeeId, @RequestBody WorkShift workShift) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(workShiftService.createWorkShift(employeeId, workShift));
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<List<WorkShift>> getAllWorkShiftsByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        return ResponseEntity.ok(workShiftService.getWorkShiftsByEmployee(employeeId));
    }

    @PutMapping("{id}")
    public ResponseEntity<WorkShift> updateWorkShift(@PathVariable("id") Long id, @RequestBody WorkShift workShift) {
        return ResponseEntity.ok(workShiftService.updateWorkShift(id, workShift));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteWorkShift(@PathVariable Long id) {
        workShiftService.deleteWorkShift(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
