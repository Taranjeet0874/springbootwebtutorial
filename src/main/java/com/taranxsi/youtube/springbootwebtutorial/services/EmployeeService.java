package com.taranxsi.youtube.springbootwebtutorial.services;

import com.taranxsi.youtube.springbootwebtutorial.dto.EmployeeDTO;
import com.taranxsi.youtube.springbootwebtutorial.entities.EmployeeEntity;
import com.taranxsi.youtube.springbootwebtutorial.exceptions.ResourceNotFoundException;
import com.taranxsi.youtube.springbootwebtutorial.repositories.EmployeeRespository;
import org.apache.el.util.ReflectionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRespository employeeRespository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRespository employeeRespository, ModelMapper modelMapper) {
        this.employeeRespository = employeeRespository;
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        return employeeRespository.findById(id).map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRespository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity = employeeRespository.save(toSaveEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeebyID(Long employeeID, EmployeeDTO employeeDTO) {
        isExistEmployeeById(employeeID);
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(employeeID);
        EmployeeEntity savedEmployeeEntity = employeeRespository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long employeeId) {
        isExistEmployeeById(employeeId);
        employeeRespository.deleteById(employeeId);
        return true;
    }

    public boolean isExistEmployeeById(Long employeeId) {
        boolean exists = employeeRespository.existsById(employeeId);
        if(!exists) {
            throw new ResourceNotFoundException("Employee with id " + employeeId + " not found");
        }
        return true;
    }

    public EmployeeDTO updatePartialEmployeebyID(Long employeeId, Map<String, Object> updates) {
        isExistEmployeeById(employeeId);
        EmployeeEntity employeeEntity = employeeRespository.findById(employeeId).orElse(null);
        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.getRequiredField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });
        employeeRespository.save(employeeEntity);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }
}
