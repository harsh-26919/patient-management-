package com.harsh.patientservice.service;


import com.harsh.patientservice.DTO.PatientRequestDTO;
import com.harsh.patientservice.DTO.PatientResponseDTO;
import com.harsh.patientservice.Model.Patient;
import com.harsh.patientservice.Repository.PatientRepository;
import com.harsh.patientservice.exception.EmailAlreadyExistsException;
import com.harsh.patientservice.exception.PatientNotFoundException;
import com.harsh.patientservice.grpc.BillingServiceGrpcClient;
import com.harsh.patientservice.kafka.KafkaProducer;
import com.harsh.patientservice.mapper.PateintMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService
{
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public PatientService(PatientRepository patientRepository,
                          BillingServiceGrpcClient billingServiceGrpcClient,
                          KafkaProducer kafkaProducer)
    {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDTO> getPateints()
    {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(PateintMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO)
    {
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail()))
        {
            throw new EmailAlreadyExistsException("A patient with this email " +
                    "already exists"+patientRequestDTO.getEmail());
        }
        Patient patient = PateintMapper.toModel(patientRequestDTO);
        Patient newPatient = patientRepository.save(patient);
        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(),
                newPatient.getName().toString(),
                newPatient.getEmail().toString());
        kafkaProducer.senEvent(newPatient);
        return PateintMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id,
                                            PatientRequestDTO patientRequestDTO)
    {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient Not Found with ID: "+ id));

        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id))
        {
            throw new EmailAlreadyExistsException("A patient with this email " +
                    "already exists"+patientRequestDTO.getEmail());
        }
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setEmail(patientRequestDTO.getEmail());
        Patient updatedPatient = patientRepository.save(patient);
        return PateintMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id)
    {
        patientRepository.deleteById(id);
    }
}
