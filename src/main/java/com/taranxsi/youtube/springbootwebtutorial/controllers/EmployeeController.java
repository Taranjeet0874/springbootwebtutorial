package com.taranxsi.youtube.springbootwebtutorial.controllers;


import com.taranxsi.youtube.springbootwebtutorial.dto.EmployeeDTO;
import com.taranxsi.youtube.springbootwebtutorial.entities.EmployeeEntity;
import com.taranxsi.youtube.springbootwebtutorial.exceptions.ResourceNotFoundException;
import com.taranxsi.youtube.springbootwebtutorial.repositories.EmployeeRespository;
import com.taranxsi.youtube.springbootwebtutorial.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRespository employeeRespository;

    public EmployeeController(EmployeeService employeeService, EmployeeRespository employeeRespository) {
        this.employeeService = employeeService;
        this.employeeRespository = employeeRespository;
    }

    @GetMapping(path = "/{employeeID}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeID") Long id) {
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);
        return employeeDTO
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @GetMapping
        public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) Integer age) {
            return ResponseEntity.ok(employeeService.getAllEmployees());

    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee) {
        EmployeeDTO savedEmployee = employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeebyID(@RequestBody @Valid EmployeeDTO employeeDTO, @PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.updateEmployeebyID(employeeId, employeeDTO));
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId) {
        boolean deletedEmployee = employeeService.deleteEmployeeById(employeeId);
        if (deletedEmployee) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeebyID(@PathVariable Long employeeId, @RequestBody Map<String, Object> updates) {
        EmployeeDTO updatedEmployee = employeeService.updatePartialEmployeebyID(employeeId, updates);
        if (updatedEmployee != null) {
            return ResponseEntity.ok(updatedEmployee);
        }
        return ResponseEntity.notFound().build();
    }
}
