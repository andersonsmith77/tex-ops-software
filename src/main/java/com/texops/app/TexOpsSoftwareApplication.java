package com.texops.app;

import com.texops.app.dao.EmployeeRepository;
import com.texops.app.models.Employee;
import com.texops.app.models.WorkShift;
import com.texops.app.services.WorkShiftService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalTime;

@SpringBootApplication
public class TexOpsSoftwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(TexOpsSoftwareApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(EmployeeRepository employeeRepository, WorkShiftService workShiftService) {
		return args -> {

			Employee employeeOne = new Employee(null, "EMP001", "Juan Pérez", true);
			Employee employeeTwo = new Employee(null, "EMP002", "María García", true);

			employeeRepository.save(employeeOne);
			employeeRepository.save(employeeTwo);

			WorkShift shiftOne = new WorkShift();
			shiftOne.setShiftName("Turno Mañana");
			shiftOne.setStartTime(LocalTime.of(8, 0));
			shiftOne.setEndTime(LocalTime.of(14, 0));
			workShiftService.createWorkShift(employeeOne.getId(), shiftOne);

			WorkShift shiftTwo = new WorkShift();
			shiftTwo.setShiftName("Turno Tarde");
			shiftTwo.setStartTime(LocalTime.of(14, 0));
			shiftTwo.setEndTime(LocalTime.of(20, 0));
			workShiftService.createWorkShift(employeeTwo.getId(), shiftTwo);

//			try {
//				WorkShift shiftConflictivo = new WorkShift();
//				shiftConflictivo.setShiftName("Turno Conflicto");
//				shiftConflictivo.setStartTime(LocalTime.of(10, 0)); // Choca con shift1
//				shiftConflictivo.setEndTime(LocalTime.of(16, 0));
//				workShiftService.createWorkShift(employeeOne.getId(), shiftConflictivo);
//			} catch (Exception e) {
//				System.out.println("Validación confirmada: No se pudo cargar el turno solapado -> " + e.getMessage());
//			}
		};
	}
}
