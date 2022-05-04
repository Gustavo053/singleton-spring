package com.example.singletonspring.controller;

import com.example.singletonspring.model.Patient;
import com.example.singletonspring.service.MessageService;
import com.example.singletonspring.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
        Optional<Patient> patientOptional = patientService.findById(id);

        if (!patientOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("patient.not-found"));
        }

        return patientOptional.get();
    }

    @PostMapping
    public Patient save(@RequestBody Patient patient) {
        return patientService.save(patient);
    }

    @PutMapping(value = "/{id}")
    public Patient update(@PathVariable Long id, @RequestBody Patient patient) {
        Optional<Patient> patientOptional = patientService.findById(id);

        if (!patientOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("patient.not-found"));
        }

        patient.setId(id);
        return patientService.save(patient);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Optional<Patient> patientOptional = patientService.findById(id);

        if (!patientOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("patient.not-found"));
        }

        patientService.deleteById(id);
    }

    @GetMapping(value = "/get-patient-list")
    public List<Patient> getWaitingList() {
        return patientService.getWaitingList();
    }
}
