package com.example.singletonspring.controller;

import com.example.singletonspring.model.Doctor;
import com.example.singletonspring.model.Patient;
import com.example.singletonspring.service.DoctorService;
import com.example.singletonspring.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<Doctor> findAll() {
        return doctorService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Doctor findById(@PathVariable Long id) {
        Doctor doctor = doctorService.findById(id);

        if (doctor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("doctor.not-found"));
        }

        return doctor;
    }

    @PostMapping
    public Doctor save(@RequestBody Doctor doctor) {
        return doctorService.save(doctor);
    }

    @PutMapping(value = "/{id}")
    public Doctor update(@PathVariable Long id, @RequestBody Doctor doctor) {
        Doctor doctorUpdated = doctorService.update(id, doctor);

        if (doctorUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("doctor.not-found"));
        }

        return doctorUpdated;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        boolean deleted = doctorService.deleteById(id);

        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("doctor.not-found"));
        }
    }

    @PostMapping(value = "/add-patient-list/{patientId}")
    public List<Patient> addPatientWaitingList(@PathVariable Long patientId) {
        List<Patient> patients = doctorService.addPatientWaitingList(patientId);

        if (patients == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.get("patient.not-found"));
        }

        return patients;
    }

    @GetMapping(value = "/get-patient-list")
    public List<Patient> getWaitingList() {
        return doctorService.getWaitingList();
    }
}
