package com.example.hospitalManagement.service;

import com.example.hospitalManagement.entity.Insurence;
import com.example.hospitalManagement.entity.Patient;
import com.example.hospitalManagement.repository.InsurenceRepository;
import com.example.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsurenceRepository insuranceRepository;
    private  final PatientRepository patientRepository;

    @Transactional
    public Patient assignInsuranceToPatient(Insurence insurence, Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow(()->new EntityNotFoundException("patient not found with id :"+patientId));
        patient.setInsurence(insurence);

        insurence.setPatient(patient);

        return patient;

    }

    @Transactional
    public Patient disaccociateInsuranceFromPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));

        patient.setInsurence(null);
        return patient;
    }
}
