package com.example.singletonspring.controller;

import com.example.singletonspring.model.Patient;
import com.example.singletonspring.service.MessageService;
import com.example.singletonspring.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<Patient> findAll() {
        return patientService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Patient findById(@PathVariable Long id) {
        Patient patient = patientService.findById(id);

        if (patient == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("patient.not-found"));
        }

        return patient;
    }

    @PostMapping
    public Patient save(@RequestBody Patient patient) {
        return patientService.save(patient);
    }

    @PutMapping(value = "/{id}")
    public Patient update(@PathVariable Long id, @RequestBody Patient patient) {
        Patient patientUpdated = patientService.update(id, patient);

        if (patientUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("patient.not-found"));
        }

        return patientUpdated;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        boolean deleted = patientService.deleteById(id);

        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("patient.not-found"));
        }
    }

    @GetMapping(value = "/get-patient-list")
    public List<Patient> getWaitingList() {
        return patientService.getWaitingList();
    }
}
