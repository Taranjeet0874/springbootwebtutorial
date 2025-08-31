package com.taranxsi.youtube.springbootwebtutorial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.taranxsi.youtube.springbootwebtutorial.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name of the employee cannot be empty")
    @Size(min = 3, max = 30, message = "Number of characters should be in the range 3-10")
    private String name;

    @Email(message = "Email should be a valid Email")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Max(value = 80, message = "Age cannot be more than 80")
    @Min(value = 18, message = "Age cannot be less than 18")
    @NotNull(message = "Age cannot be blank")
    private Integer age;

    //@Pattern(regexp ="^(ADMIN|USER)$", message = "The role can be one of ADMIN/USER only")
    @EmployeeRoleValidation
    @NotBlank(message = "Role cannot be blank")
    private String role;

    @PastOrPresent(message = "Date can only be in the past")
    private LocalDate dateofJoining;

    @NotNull(message = "Salary cannot be null")
    @Positive(message = "Salary of the employee should be positive")
    @Digits(integer = 6, fraction = 2, message = "The Salary can be in the form XXXXXX.YY")
    private Double salary;

    @JsonProperty("isActive")
    @AssertTrue(message = "Employee should be active")
    private Boolean isActive;
}
