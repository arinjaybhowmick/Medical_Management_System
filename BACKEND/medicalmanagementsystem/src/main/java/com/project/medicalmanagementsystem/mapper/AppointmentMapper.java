package com.project.medicalmanagementsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.medicalmanagementsystem.dto.AppointmentBookingRequestDTO;
import com.project.medicalmanagementsystem.dto.AppointmentBookingResponseDTO;
import com.project.medicalmanagementsystem.model.Appointment;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentBookingResponseDTO appointmentToAppointmentBookingResponseDTO(Appointment appointment);

    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "patient.id", target = "patientId")
    AppointmentBookingResponseDTO appointmentToAppointmentBookingResponseDTOWithIds(Appointment appointment);

    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "patient", ignore = true)
    Appointment appointmentBookingRequestDTOToAppointment(AppointmentBookingRequestDTO appointmentBookingRequestDTO);
}