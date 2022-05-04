package com.example.singletonspring.service;

import com.example.singletonspring.model.Doctor;
import com.example.singletonspring.model.Patient;
import com.example.singletonspring.model.WaitingList;
import com.example.singletonspring.repository.DoctorRepository;
import com.example.singletonspring.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private MessageService messageService;

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Doctor findById(Long id) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);

        if (!doctorOptional.isPresent()) {
            return null;
        }

        return doctorOptional.get();
    }

    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor update(Long id, Doctor doctor) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);

        if (!doctorOptional.isPresent()) {
            return null;
        }

        doctor.setId(id);
        return doctorRepository.save(doctor);
    }

    public Boolean deleteById(Long id) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);

        if (!doctorOptional.isPresent()) {
            return false;
        }

        doctorRepository.deleteById(id);
        return true;
    }

    public List<Patient> addPatientWaitingList(Long patientId) {
        Optional<Patient> patientOptional = patientRepository.findById(patientId);

        if (!patientOptional.isPresent()) {
            return null;
        }

        Patient patient = patientOptional.get();

        WaitingList waitingList = WaitingList.getInstance();
        List<Patient> patients = waitingList.getPatients();

        boolean match = patients.stream().anyMatch(p -> p.getId().equals(patient.getId()));

        if (match) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.get("patient.already-added"));
        }

        patients.add(patient);
        waitingList.setPatients(patients);

        return waitingList.getPatients();
    }

    public List<Patient> getWaitingList() {
        WaitingList waitingList = WaitingList.getInstance();
        return waitingList.getPatients();
    }
}
