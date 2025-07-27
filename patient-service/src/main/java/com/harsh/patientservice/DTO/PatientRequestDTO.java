package com.harsh.patientservice.DTO;

import com.harsh.patientservice.DTO.Validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientRequestDTO
{
    @NotBlank(message = "Name is Required")
    @Size(max = 100,message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Email is Required")
    @Email(message = "Email should valid")
    private String email;

    @NotBlank(message = "Address is Required")
    private String address;

    @NotBlank(message = "Date of Birth is Required")
    private String dateOfBirth;

    @NotBlank(groups = CreatePatientValidationGroup.class,
            message = "Registered Date is Required")
    private String registeredDate;
}
