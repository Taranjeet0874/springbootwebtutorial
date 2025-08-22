package com.taranxsi.youtube.springbootwebtutorial.controllers;


import com.taranxsi.youtube.springbootwebtutorial.dto.EmployeeDTO;
import com.taranxsi.youtube.springbootwebtutorial.entities.EmployeeEntity;
import com.taranxsi.youtube.springbootwebtutorial.repositories.EmployeeRespository;
import com.taranxsi.youtube.springbootwebtutorial.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

//    @GetMapping(path = "/getSecretMessage")
//    public String getMySuperSecretMessgae() {
//        return "Secret Message: Hello ji";
//    }

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/{employeeID}")
    public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeID") Long id) {
        return employeeService.getEmployeeById(id);

    }

    @GetMapping
        public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false) Integer age) {
            return employeeService.getAllEmployees();

    }

//    @GetMapping
//    public String getAllEmployees(@RequestParam(required = false) Integer age) {
//        return "Hello World";
//
//    }

    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee) {
        return employeeService.createNewEmployee(inputEmployee);

    }

    @PutMapping(path = "/{employeeId}")
    public EmployeeDTO updateEmployeebyID(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long employeeId) {
        return employeeService.updateEmployeebyID(employeeId, employeeDTO);
    }
}
