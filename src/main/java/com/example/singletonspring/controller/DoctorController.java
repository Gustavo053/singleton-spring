package com.example.singletonspring.controller;

import com.example.singletonspring.model.Doctor;
import com.example.singletonspring.model.Patient;
import com.example.singletonspring.model.WaitingList;
import com.example.singletonspring.service.DoctorService;
import com.example.singletonspring.service.MessageService;
import com.example.singletonspring.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<Doctor> findAll() {
        return doctorService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Doctor findById(@PathVariable Long id) {
        Optional<Doctor> doctorOptional = doctorService.findById(id);

        if (!doctorOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("doctor.not-found"));
        }

        return doctorOptional.get();
    }

    @PostMapping
    public Doctor save(@RequestBody Doctor doctor) {
        return doctorService.save(doctor);
    }

    @PutMapping(value = "/{id}")
    public Doctor update(@PathVariable Long id, @RequestBody Doctor doctor) {
        Optional<Doctor> doctorOptional = doctorService.findById(id);

        if (!doctorOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("doctor.not-found"));
        }

        doctor.setId(id);
        return doctorService.save(doctor);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        Optional<Doctor> doctorOptional = doctorService.findById(id);

        if (!doctorOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("doctor.not-found"));
        }

        doctorService.deleteById(id);
    }

    @PostMapping(value = "/add-patient-list/{patientId}")
    public List<Patient> addPatientWaitingList(@PathVariable Long patientId) {
        Optional<Patient> patientOptional = patientService.findById(patientId);

        if (!patientOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("patient.not-found"));
        }

        Patient patient = patientOptional.get();

        WaitingList waitingList = WaitingList.getInstance();
        boolean match = waitingList.getPatients().stream().anyMatch(p -> p.getId().equals(patient.getId()));

        if (match) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.get("patient.already-added"));
        }

        doctorService.addPatientWaitingList(patient);
        return waitingList.getPatients();
    }

    @GetMapping(value = "/get-patient-list")
    public List<Patient> getWaitingList() {
        return doctorService.getWaitingList();
    }
}
