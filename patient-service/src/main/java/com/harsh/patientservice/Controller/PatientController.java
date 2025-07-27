package com.harsh.patientservice.Controller;

import com.harsh.patientservice.DTO.PatientRequestDTO;
import com.harsh.patientservice.DTO.PatientResponseDTO;
import com.harsh.patientservice.DTO.Validators.CreatePatientValidationGroup;
import com.harsh.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient" , description = "API for managing patients")
public class PatientController
{
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients()
    {
        return new ResponseEntity<>(patientService.getPateints(),null, OK);
    }
    @PostMapping
    @Operation(summary = "Create a new patient")
    public ResponseEntity<PatientResponseDTO> createPatient
            (@Validated({Default.class, CreatePatientValidationGroup.class})
             @RequestBody PatientRequestDTO patientRequestDTO)
    {
        return new ResponseEntity<>
                (patientService.createPatient(patientRequestDTO),null, CREATED);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update a  patient")
    public ResponseEntity<PatientResponseDTO> updatePatient
            (@PathVariable UUID id,
             @Validated({Default.class})
             @RequestBody PatientRequestDTO patientRequestDTO)
    {
        return new ResponseEntity<>
                (patientService.updatePatient(id,patientRequestDTO),null, OK);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id)
    {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
