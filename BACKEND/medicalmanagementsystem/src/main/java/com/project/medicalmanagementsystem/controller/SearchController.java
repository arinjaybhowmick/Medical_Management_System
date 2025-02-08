package com.project.medicalmanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.medicalmanagementsystem.exception.DoctorNotFoundException;
import com.project.medicalmanagementsystem.dto.AppointmentSearchRequestDTO;
import com.project.medicalmanagementsystem.dto.DoctorDTO;
import com.project.medicalmanagementsystem.dto.FeedbackDTO;
import com.project.medicalmanagementsystem.model.Doctor;
import com.project.medicalmanagementsystem.model.Specialization;
import com.project.medicalmanagementsystem.service.AppointmentService;
import com.project.medicalmanagementsystem.service.DoctorService;
import com.project.medicalmanagementsystem.service.FeedbackService;
import com.project.medicalmanagementsystem.service.GenerateResponseService;
import com.project.medicalmanagementsystem.service.SpecializationService;
import com.project.medicalmanagementsystem.utility.enums.Status;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/search")
class SearchController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private SpecializationService specializationService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    private GenerateResponseService generateResponseService;

    // fetch all doctors
    @GetMapping("/doctors")
    public Page<DoctorDTO> searchDoctors(@RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "fees_asc") String sort) {
        System.out.println("sort" + query + page + size + sort);
        Pageable pageable = PageRequest.of(page, size);
        String sortBy = "";
        String sortDir = "";
        if (sort != null && !sort.isEmpty()) {
            String[] sortParams = sort.split("_");
            sortBy = sortParams[0];
            sortDir = sortParams[1];
        }

        return doctorService.searchDoctors(query, pageable, sortBy, sortDir);
    }

    @PostMapping("/searchAppointments")
    public ResponseEntity<Object> searchAppointments(
            @Valid @RequestBody AppointmentSearchRequestDTO appointmentSearchRequestDTO) {
        return generateResponseService.generateResponse(
                "Appointment List fetched",
                HttpStatus.OK,
                appointmentService.searchAppointments(appointmentSearchRequestDTO));
    }

    // fetch doctors according to specailization
    @GetMapping("/doctorsBySpecialization")
    public Page<DoctorDTO> searchDoctorsBySpecialization(@RequestParam String specialization,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return doctorService.findDoctorsBySpecialization(specialization, pageable);
    }

    @GetMapping("/specializations")
    public List<Specialization> getSpecializations() {
        return specializationService.getSpecializations();
    }

    @GetMapping("/searchAndFilter")
    public Page<DoctorDTO> searchAndFilterData(@RequestParam String searchQuery, @RequestParam Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return doctorService.searchAndFilterDoctors(searchQuery, status, pageable);
    }

    @GetMapping("/searchDoctors")
    public Page<DoctorDTO> searchDoctorsAdmin(@RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return doctorService.searchDoctorsAdmin(query, pageable);
    }

    @GetMapping("/doctorById")
    public Doctor getDoctor(@RequestParam Long id) throws DoctorNotFoundException {
        System.out.println("herehehe");
        return this.doctorService.getDoctorById(id);
    }

    @GetMapping("/get-feedback")
    public ResponseEntity<Object> getFeedback(@RequestParam Long id,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return generateResponseService.generateResponse("Feedback fetched successfully : ", HttpStatus.OK,
                feedbackService.getFeedbackByDoctorId(id, page, size));
    }

    @GetMapping("/feedback")
    public ResponseEntity<List<FeedbackDTO>> getDoctorFeedback(@RequestParam Long doctorId) {
        List<FeedbackDTO> doctorFeedback = feedbackService.getDoctorFeedback(doctorId);
        return new ResponseEntity<>(doctorFeedback, HttpStatus.OK);
    }

}
