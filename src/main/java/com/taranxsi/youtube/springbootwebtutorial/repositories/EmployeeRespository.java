package com.taranxsi.youtube.springbootwebtutorial.repositories;

import com.taranxsi.youtube.springbootwebtutorial.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRespository extends JpaRepository<EmployeeEntity, Long> {

}
