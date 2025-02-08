package com.project.medicalmanagementsystem.controller;

import java.util.NoSuchElementException;

import javax.management.InvalidAttributeValueException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.medicalmanagementsystem.exception.AppointmentAlreadyBookedException;
import com.project.medicalmanagementsystem.exception.AppointmentAlreadyCancelledException;
import com.project.medicalmanagementsystem.exception.NoConsecutiveBookingsException;
import com.project.medicalmanagementsystem.exception.UnauthorizedAccessException;
import com.project.medicalmanagementsystem.exception.UnauthorizedloginException;
import com.project.medicalmanagementsystem.exception.NoSameBookingsWithSameDoctorByOnePatient;
import com.project.medicalmanagementsystem.exception.UsernameAlreadyExistsException;
import com.project.medicalmanagementsystem.service.GenerateResponseService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PessimisticLockException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionController {

    @Autowired
    GenerateResponseService generateResponseService;

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleException(BadCredentialsException e) {

        return generateResponseService.generateResponse(e.getMessage(), HttpStatusCode.valueOf(401));
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<Object> handleInternalAuthenticationServiceException(
            InternalAuthenticationServiceException e) {

        return generateResponseService.generateResponse("Invalid Username or Password !!", HttpStatusCode.valueOf(401));
    }

    @ExceptionHandler(AppointmentAlreadyBookedException.class)
    public ResponseEntity<Object> handleAppointmentBookingException(AppointmentAlreadyBookedException e) {
        return generateResponseService.generateResponse(e.getMessage(), HttpStatusCode.valueOf(409));
    }

    @ExceptionHandler(NoSameBookingsWithSameDoctorByOnePatient.class)
    public ResponseEntity<Object> NoSameBookingsWithSameDoctorByOnePatient(NoSameBookingsWithSameDoctorByOnePatient e) {
        return generateResponseService.generateResponse(e.getMessage(), HttpStatusCode.valueOf(409));
    }

    @ExceptionHandler(AppointmentAlreadyCancelledException.class)
    public ResponseEntity<Object> handleAppointmentCancelException(AppointmentAlreadyCancelledException e) {
        return generateResponseService.generateResponse(e.getMessage(), HttpStatusCode.valueOf(409));
    }

    @ExceptionHandler(NoConsecutiveBookingsException.class)
    public ResponseEntity<Object> handleNoConsecutiveBookingsException(NoConsecutiveBookingsException e) {
        return generateResponseService.generateResponse(e.getMessage(), HttpStatusCode.valueOf(409));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e) {

        return generateResponseService.generateResponse("No element by that name", HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        return generateResponseService.generateResponse("Please give data in correct format",
                HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        return generateResponseService.generateResponse("Please give data in correct format",
                HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {

        return generateResponseService.generateResponse("There is already an element for this",
                HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(InvalidAttributeValueException.class)
    public ResponseEntity<Object> handleInvalidAttributeValue(InvalidAttributeValueException e) {

        return generateResponseService.generateResponse(e.getMessage(), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleInvalidUserOrPatientId(UsernameNotFoundException e) {

        return generateResponseService.generateResponse(e.getMessage(), HttpStatusCode.valueOf(401));
    }

    @ExceptionHandler({ OptimisticLockException.class, PessimisticLockException.class })
    public ResponseEntity<Object> handleLockConflictException(Exception e) {
        return generateResponseService.generateResponse("Please try again", HttpStatusCode.valueOf(409));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        // System.out.println(ex.getMessage());
        return generateResponseService.generateResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        // System.out.println(ex.getMessage());
        return generateResponseService.generateResponse(ex.getMessage(), HttpStatusCode.valueOf(403));
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleSignatureException(SignatureException ex) {
        // System.out.println(ex.getMessage());
        return generateResponseService.generateResponse(ex.getMessage(), HttpStatusCode.valueOf(403));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
        // System.out.println(ex.getMessage());
        return generateResponseService.generateResponse(ex.getMessage(), HttpStatusCode.valueOf(666));
    }

    @ExceptionHandler(UnauthorizedloginException.class)
    public ResponseEntity<Object> handleUnauthorizedloginException(UnauthorizedloginException ex) {
        // System.out.println(ex.getMessage());
        return generateResponseService.generateResponse(ex.getMessage(), HttpStatusCode.valueOf(621));
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Object> handleAccessException(UnauthorizedAccessException ex) {
        // System.out.println(ex.getMessage());
        return generateResponseService.generateResponse(ex.getMessage(), HttpStatusCode.valueOf(612));
    }

    /*
     * @ExceptionHandler(Exception.class)
     * public ResponseEntity<Object> handleSecurityException(Exception ex) {
     * ResponseEntity<Object> errorDetail = null;
     * 
     * if (ex instanceof AccessDeniedException) {
     * 
     * return generateResponseService.generateResponse(ex.getMessage(),
     * HttpStatusCode.valueOf(403));
     * 
     * // errorDetail =
     * ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),
     * ex.getMessage());
     * // errorDetail.setProperty("access_denied_reason", "not_authorized!");
     * 
     * }
     * 
     * if (ex instanceof SignatureException) {
     * 
     * return generateResponseService.generateResponse(ex.getMessage(),
     * HttpStatusCode.valueOf(403));
     * 
     * // errorDetail =
     * ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),
     * ex.getMessage());
     * // errorDetail.setProperty("access_denied_reason",
     * "JWT Signature not valid");
     * }
     * if (ex instanceof ExpiredJwtException) {
     * // errorDetail =
     * ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),
     * ex.getMessage());
     * // errorDetail.setProperty("access_denied_reason",
     * "JWT Token already expired !");
     * 
     * return generateResponseService.generateResponse(ex.getMessage(),
     * HttpStatusCode.valueOf(403));
     * }
     * 
     * return errorDetail;
     * }
     */

}
