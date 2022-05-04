package com.example.singletonspring.service;

import com.example.singletonspring.model.Doctor;
import com.example.singletonspring.model.Patient;
import com.example.singletonspring.model.WaitingList;
import com.example.singletonspring.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> findById(Long id) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        return doctorOptional;
    }

    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteById(Long id) {
        doctorRepository.deleteById(id);
    }

    public List<Patient> addPatientWaitingList(Patient patient) {
        WaitingList waitingList = WaitingList.getInstance();
        List<Patient> patients = waitingList.getPatients();

        patients.add(patient);
        waitingList.setPatients(patients);

        return waitingList.getPatients();
    }

    public List<Patient> removePatientWaitingList(Long patientId) {
        WaitingList waitingList = WaitingList.getInstance();
        List<Patient> patients = waitingList.getPatients();

        patients.removeIf(patient -> patient.getId().equals(patientId));
        return patients;
    }

    public List<Patient> getWaitingList() {
        WaitingList waitingList = WaitingList.getInstance();
        return waitingList.getPatients();
    }
}
