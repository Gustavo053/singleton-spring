package com.example.singletonspring.service;

import com.example.singletonspring.model.Patient;
import com.example.singletonspring.model.WaitingList;
import com.example.singletonspring.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Optional<Patient> findById(Long id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        return patientOptional;
    }

    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }

    public List<Patient> getWaitingList() {
        WaitingList waitingList = WaitingList.getInstance();
        return waitingList.getPatients();
    }
}
