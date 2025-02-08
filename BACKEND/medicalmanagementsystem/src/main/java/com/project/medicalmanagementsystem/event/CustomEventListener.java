package com.project.medicalmanagementsystem.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.project.medicalmanagementsystem.service.DoctorService;
import com.project.medicalmanagementsystem.service.EmailSendingServiceImpl;

import jakarta.mail.MessagingException;

@Component
public class CustomEventListener {

    @Autowired
    EmailSendingServiceImpl emailSendingService;

    @Autowired
    DoctorService doctorService;
    
    @EventListener(EmailSendingEvent.class)
    @Async("asyncTaskExecutor")
    public void handleEmailEvent(EmailSendingEvent event) throws InterruptedException, MessagingException {
        System.out.println("Inside event listener" + Thread.currentThread().getName());
        switch (event.getType()) {
            case "BOOK":
            emailSendingService.sendBookingConfirmationMail(event.getRecipient(),event.getDate(),event.getTime(),event.getName());
                break;
        
            case "CANCELLED":
            emailSendingService.sendBookingCancelledMail(event.getRecipient(),event.getDate(),event.getTime(),event.getName());
                break;
        
            default:
                break;
        }
       
    }
    @EventListener(DoctorAdditionEmailEvent.class)
    @Async("asyncTaskExecutor")
    public void handleDoctorAdditionEmailEvent(DoctorAdditionEmailEvent event) throws InterruptedException, MessagingException {
        System.out.println("Inside event listener addition" + Thread.currentThread().getName());
        emailSendingService.sendDoctorCredentials(event.getEmail(), event.getUsername(), event.getPassword());
        
       
    }
    @EventListener(DoctorRatingUpdationEvent.class)
    @Async("asyncTaskExecutor")
    public void handleDoctorRatingUpdationEvent(DoctorRatingUpdationEvent event) throws InterruptedException, MessagingException {
        System.out.println("Inside event listener"+event.getNewRating() +" "+event.getAppointmentId() +" " + Thread.currentThread().getName());
        doctorService.updateDoctorRating(event.getNewRating(),event.getAppointmentId());
    }
}
