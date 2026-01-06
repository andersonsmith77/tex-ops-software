package com.texops.app.dao;

import com.texops.app.models.WorkShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WorkShiftRepository extends JpaRepository<WorkShift, Long> {
    List<WorkShift> findByEmployeeId(Long employeeId);
    boolean existsByEmployeeId(Long employeeId);
}
