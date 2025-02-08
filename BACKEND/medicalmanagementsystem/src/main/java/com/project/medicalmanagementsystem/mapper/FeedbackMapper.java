package com.project.medicalmanagementsystem.mapper;

import org.springframework.stereotype.Component;

import com.project.medicalmanagementsystem.dto.FeedbackDTO;
import com.project.medicalmanagementsystem.model.Feedback;

@Component
public class FeedbackMapper {
    public FeedbackDTO convertToFeedbackDTO(Feedback feedback) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setAppointment_id(feedback.getAppointment().getId());
        feedbackDTO.setRating(feedback.getRating());
        feedbackDTO.setReview(feedback.getReview());

        return feedbackDTO;
    }
}
