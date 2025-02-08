package com.project.medicalmanagementsystem.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project.medicalmanagementsystem.dto.FeedbackDTO;

public interface FeedbackService {

    FeedbackDTO giveFeedback(FeedbackDTO feedback);

    public Page<FeedbackDTO> getFeedbackByDoctorId(Long id, int page, int size);

    public List<FeedbackDTO> getFeedbackByPatientId(Long id);

    List<FeedbackDTO> getDoctorFeedback(Long doctorId);
}
