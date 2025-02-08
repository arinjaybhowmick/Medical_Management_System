package com.project.medicalmanagementsystem.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.management.InvalidAttributeValueException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.project.medicalmanagementsystem.mapper.SlotTimeMapper;
import com.project.medicalmanagementsystem.dto.AppointmentBookingRequestDTO;
import com.project.medicalmanagementsystem.dto.AppointmentBookingResponseDTO;
import com.project.medicalmanagementsystem.dto.AppointmentSearchRequestDTO;
import com.project.medicalmanagementsystem.dto.AppointmentSearchResponseDTO;
import com.project.medicalmanagementsystem.event.EmailSendingEvent;
import com.project.medicalmanagementsystem.exception.AppointmentAlreadyBookedException;
import com.project.medicalmanagementsystem.exception.AppointmentAlreadyCancelledException;
import com.project.medicalmanagementsystem.exception.NoConsecutiveBookingsException;
import com.project.medicalmanagementsystem.exception.NoSameBookingsWithSameDoctorByOnePatient;
import com.project.medicalmanagementsystem.model.Appointment;
import com.project.medicalmanagementsystem.model.Doctor;
import com.project.medicalmanagementsystem.model.Patient;
import com.project.medicalmanagementsystem.repository.AppointmentRepository;
import com.project.medicalmanagementsystem.utility.enums.AppointmentStatus;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@Service
public class AppointmentServiceImpl implements AppointmentService {

        @Autowired
        DoctorService doctorService;

        @Autowired
        PatientService patientService;

        @Autowired
        ApplicationEventPublisher eventPublisher;

        @Autowired
        AppointmentRepository appointmentRepository;

        @Autowired
        SlotTimeMapper slotTimeMapper;

        @Transactional
        public AppointmentBookingResponseDTO bookAppointment(AppointmentBookingRequestDTO appointmentBookingRequestDTO)
                        throws AppointmentAlreadyBookedException, ConstraintViolationException, NoSuchElementException,
                        DataIntegrityViolationException {
                AppointmentSearchRequestDTO appointmentSearchRequestDTO;
                appointmentSearchRequestDTO = new AppointmentSearchRequestDTO();
                appointmentSearchRequestDTO.setStartDate(appointmentBookingRequestDTO.getAppDate());
                appointmentSearchRequestDTO.setEndDate(appointmentBookingRequestDTO.getAppDate());
                appointmentSearchRequestDTO.setDoctorId(appointmentBookingRequestDTO.getDoctorId());
                appointmentSearchRequestDTO.setSlot(appointmentBookingRequestDTO.getSlot());
                appointmentSearchRequestDTO.setAppointmentStatus("PENDING");

                List<AppointmentSearchResponseDTO> sameAppointment = searchAppointments(appointmentSearchRequestDTO)
                                .getContent();

                if (sameAppointment.size() > 0) {
                        throw new AppointmentAlreadyBookedException("This appointment has already been booked");
                }

                if (appointmentBookingRequestDTO.getSlot() + 1 <= 24) {
                        appointmentSearchRequestDTO = new AppointmentSearchRequestDTO();
                        appointmentSearchRequestDTO.setStartDate(appointmentBookingRequestDTO.getAppDate());
                        appointmentSearchRequestDTO.setEndDate(appointmentBookingRequestDTO.getAppDate());
                        appointmentSearchRequestDTO.setDoctorId(appointmentBookingRequestDTO.getDoctorId());
                        appointmentSearchRequestDTO.setPatientId(appointmentBookingRequestDTO.getPatientId());
                        appointmentSearchRequestDTO.setSlot(appointmentBookingRequestDTO.getSlot() + 1);
                        appointmentSearchRequestDTO.setAppointmentStatus("PENDING");

                        List<AppointmentSearchResponseDTO> sameDayBookings = searchAppointments(
                                        appointmentSearchRequestDTO).getContent();

                        if (sameDayBookings.size() > 0) {
                                throw new NoConsecutiveBookingsException(
                                                "Cannot book consecutive appointments with this doctor on this day");
                        }
                }

                if (appointmentBookingRequestDTO.getSlot() - 1 >= 1) {
                        appointmentSearchRequestDTO = new AppointmentSearchRequestDTO();
                        appointmentSearchRequestDTO.setStartDate(appointmentBookingRequestDTO.getAppDate());
                        appointmentSearchRequestDTO.setEndDate(appointmentBookingRequestDTO.getAppDate());
                        appointmentSearchRequestDTO.setDoctorId(appointmentBookingRequestDTO.getDoctorId());
                        appointmentSearchRequestDTO.setPatientId(appointmentBookingRequestDTO.getPatientId());
                        appointmentSearchRequestDTO.setSlot(appointmentBookingRequestDTO.getSlot() - 1);
                        appointmentSearchRequestDTO.setAppointmentStatus("PENDING");

                        List<AppointmentSearchResponseDTO> sameDayBookings = (searchAppointments(
                                        appointmentSearchRequestDTO)).getContent();

                        if (sameDayBookings.size() > 0) {
                                throw new NoConsecutiveBookingsException(
                                                "Cannot book consecutive appointments with this doctor on this day");
                        }
                }

                appointmentSearchRequestDTO = new AppointmentSearchRequestDTO();
                appointmentSearchRequestDTO.setStartDate(appointmentBookingRequestDTO.getAppDate());
                appointmentSearchRequestDTO.setEndDate(appointmentBookingRequestDTO.getAppDate());
                appointmentSearchRequestDTO.setPatientId(appointmentBookingRequestDTO.getPatientId());
                appointmentSearchRequestDTO.setSlot(appointmentBookingRequestDTO.getSlot());
                appointmentSearchRequestDTO.setAppointmentStatus("PENDING");

                List<AppointmentSearchResponseDTO> samePatientBookings = (searchAppointments(
                                appointmentSearchRequestDTO)).getContent();

                if (samePatientBookings.size() > 0) {
                        throw new NoSameBookingsWithSameDoctorByOnePatient(
                                        "Cannot book appointments with multiple doctors on same day and on same slot");
                }

                Appointment appointment;
                appointment = new Appointment();
                Doctor doctor = (doctorService.getActiveDoctorById(appointmentBookingRequestDTO.getDoctorId())).get();

                System.out.println(doctor);

                appointment.setDoctor(doctor);
                Patient patient = patientService.getPatientById(appointmentBookingRequestDTO.getPatientId()).get();
                appointment.setPatient(patient);
                appointment.setAppStatus(AppointmentStatus.valueOf("PENDING"));
                appointment.setAppDate(appointmentBookingRequestDTO.getAppDate());
                appointment.setSlot(appointmentBookingRequestDTO.getSlot());
                System.out.println(appointment);
                AppointmentBookingResponseDTO appointmentBookingResponseDTO = new AppointmentBookingResponseDTO();
                appointment = appointmentRepository.save(appointment);
                appointmentBookingResponseDTO.setAppDate(appointment.getAppDate());
                appointmentBookingResponseDTO.setSlot(appointment.getSlot());
                appointmentBookingResponseDTO.setDoctorId(appointment.getDoctor().getId());
                appointmentBookingResponseDTO.setPatientId(appointment.getPatient().getId());

                // String emailMessage = "Dear " + appointment.getPatient().getName() + ",\n\n"
                // +
                // "We hope this message finds you well.\n\n" +
                // "We want to inform you that your appointment scheduled for " +
                // appointment.getAppDate()
                // + " at "
                // + slotTimeMapper.getTimeFromSlot(appointment.getSlot())
                // + " has been successfully booked in our medical management system.\n\n" +
                // "If you have any further questions or need assistance, please feel free to
                // contact our office.\n\n"
                // +
                // "Thank you.\n\n" +
                // "Best regards,\n" +
                // "[Your Medical Management System Team]";

                EmailSendingEvent event = new EmailSendingEvent(this, appointment.getPatient().getEmail(),
                                appointment.getAppDate().toString(),
                                slotTimeMapper.getTimeFromSlot(appointment.getSlot()),
                                appointment.getPatient().getName());

                // event.setRecipient(appointment.getPatient().getEmail())
                // event.setSubject("APPOINTMENT BOOKING CONFIRMATION")
                // event.setMessage(emailMessage);
                eventPublisher.publishEvent(event);
                return appointmentBookingResponseDTO;

        }

        @Transactional
        public String cancelAppointment(Long id)
                        throws AppointmentAlreadyCancelledException, InvalidAttributeValueException {
                Appointment appointment = appointmentRepository.findById(id)
                                .orElseThrow(() -> new InvalidAttributeValueException("Appointment ID doesnt exits"));
                if (appointment.getAppStatus() == AppointmentStatus.CANCELLED)
                        throw new AppointmentAlreadyCancelledException("Appointment already cancelled");

                appointment.setAppStatus(AppointmentStatus.CANCELLED);
                appointment = appointmentRepository.save(appointment);

                // String emailMessage = "Dear " + appointment.getPatient().getName() + ",\n\n"
                // +
                // "We hope this message finds you well.\n\n" +
                // "We want to inform you that your appointment scheduled for " +
                // appointment.getAppDate()
                // + " at "
                // + slotTimeMapper.getTimeFromSlot(appointment.getSlot())
                // + " has been successfully cancelled in our medical management system.\n\n" +
                // "If you have any further questions or need assistance, please feel free to
                // contact our office.\n\n"
                // +
                // "Thank you for your patience.\n\n" +
                // "Best regards,\n" +
                // "[Your Medical Management System Team]";
                EmailSendingEvent event = new EmailSendingEvent(this, appointment.getPatient().getEmail(),
                                appointment.getAppDate().toString(),
                                slotTimeMapper.getTimeFromSlot(appointment.getSlot()),
                                appointment.getPatient().getName());

                eventPublisher.publishEvent(event);

                return "Your Appointment with " + appointment.getDoctor().getName() + " is cancelled !!";

        }

        @Override
        public Appointment getAppointmentById(Long appointmentId) {
                return appointmentRepository.findById(appointmentId).get();
        }

        public Page<AppointmentSearchResponseDTO> searchAppointments(
                        AppointmentSearchRequestDTO appointmentSearchRequestDTO) {
                Pageable pageable;
                System.out.println(appointmentSearchRequestDTO);

                System.out.println("SEARHCING");
                if (appointmentSearchRequestDTO.getOffset() == null)
                        appointmentSearchRequestDTO.setOffset(0);

                if (appointmentSearchRequestDTO.getPageSize() == null)
                        appointmentSearchRequestDTO.setPageSize(200);

                pageable = PageRequest.of(appointmentSearchRequestDTO.getOffset(),
                                appointmentSearchRequestDTO.getPageSize());

                List<AppointmentSearchResponseDTO> searchResponseForDoctorName = appointmentRepository.findAppointments(
                                appointmentSearchRequestDTO.getDoctorId(),
                                appointmentSearchRequestDTO.getPatientId(),
                                appointmentSearchRequestDTO.getDoctorName(),
                                appointmentSearchRequestDTO.getSlot(),
                                appointmentSearchRequestDTO.getStartDate(),
                                appointmentSearchRequestDTO.getEndDate(),
                                appointmentSearchRequestDTO.getSpecialization(),
                                appointmentSearchRequestDTO.getAppointmentStatus() != null
                                                ? AppointmentStatus.valueOf(
                                                                appointmentSearchRequestDTO.getAppointmentStatus())
                                                : null);
                List<AppointmentSearchResponseDTO> searchResponseForSpecializationName = appointmentRepository
                                .findAppointments(
                                                appointmentSearchRequestDTO.getDoctorId(),
                                                appointmentSearchRequestDTO.getPatientId(),
                                                null,
                                                appointmentSearchRequestDTO.getSlot(),
                                                appointmentSearchRequestDTO.getStartDate(),
                                                appointmentSearchRequestDTO.getEndDate(),
                                                appointmentSearchRequestDTO.getDoctorName(),
                                                appointmentSearchRequestDTO.getAppointmentStatus() != null
                                                                ? AppointmentStatus.valueOf(appointmentSearchRequestDTO
                                                                                .getAppointmentStatus())
                                                                : null);
                Set<AppointmentSearchResponseDTO> mergedSet = new HashSet<>(searchResponseForDoctorName);
                mergedSet.addAll(searchResponseForSpecializationName);

                List<AppointmentSearchResponseDTO> result = new ArrayList<>(mergedSet);

                Collections.sort(result, Comparator
                                .comparing(AppointmentSearchResponseDTO::getAppDate)
                                .thenComparing(AppointmentSearchResponseDTO::getSlot));

                int start = (int) pageable.getOffset();
                int end = (start + pageable.getPageSize()) > result.size() ? result.size()
                                : (start + pageable.getPageSize());
                System.out.println("Start " + start + " End " + end);
                List<AppointmentSearchResponseDTO> truncatedResult = result.subList(start, end).stream()
                                .collect(Collectors.toList());

                return new PageImpl<>(truncatedResult, pageable, result.size());
        }

        @Transactional
        public void updateAppointmentStatus() {
                System.out.println(LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                                .toString());
                System.out.println(slotTimeMapper.getSlotFromTime(LocalDateTime.now().toLocalTime()
                                .format(DateTimeFormatter.ofPattern("HH:mm")).toString()));
                appointmentRepository.updateAppointmentStatus(LocalDate.now(),
                                slotTimeMapper.getSlotFromTime(LocalDateTime.now().toLocalTime()
                                                .format(DateTimeFormatter.ofPattern("HH:mm")).toString()));
        }

}