package com.harsh.patientservice.DTO;

import lombok.Data;

@Data
public class PatientResponseDTO
{
    private String id;
    private String name;
    private String email;
    private String address;
    private String dateOfBirth;
}
