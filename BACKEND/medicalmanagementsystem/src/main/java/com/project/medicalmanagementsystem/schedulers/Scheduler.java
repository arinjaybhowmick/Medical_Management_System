package com.project.medicalmanagementsystem.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.project.medicalmanagementsystem.service.AppointmentService;
import com.project.medicalmanagementsystem.service.DoctorService;

import jakarta.transaction.Transactional;

@Service
public class Scheduler {

    @Autowired
    AppointmentService appointmentService;
    @Autowired
    DoctorService doctorService;

    @Transactional
    @Async("taskScheduler")
    @Scheduled(cron = "* 15 10-13,14-17 * * *")
    public void completeAppointment() {
        System.out.println(Thread.currentThread().getName() + "APPOINTMENT");
        appointmentService.updateAppointmentStatus();

    }

    @Transactional
    @Async("taskScheduler")
    @Scheduled(cron = "0 * * * * *")
    public void updateDoctorStatus() {
        System.out.println(Thread.currentThread().getName());

        doctorService.updateDoctorStatus();

    }

    // @Transactional
    // @Async("taskScheduler")
    // @Scheduled(cron = "0 0 * * * *")
    // public void updateDoctorStatus() {
    // System.out.println(Thread.currentThread().getName());

    // doctorService.updateDoctorStatus();

    // }
}
