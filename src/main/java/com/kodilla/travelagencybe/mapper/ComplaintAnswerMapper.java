package com.kodilla.travelagencybe.mapper;

import com.kodilla.travelagencybe.domain.ComplaintAnswer;
import com.kodilla.travelagencybe.domain.ComplaintAnswerDto;
import org.springframework.stereotype.Service;

@Service
public class ComplaintAnswerMapper {

    public ComplaintAnswer mapToComplaintAnswer(final ComplaintAnswerDto complaintAnswerDto) {
        return new ComplaintAnswer(
                complaintAnswerDto.getId(),
                complaintAnswerDto.getComplaint(),
                complaintAnswerDto.getAnswer(),
                complaintAnswerDto.getCreationDate()
        );
    }

    public ComplaintAnswerDto mapToComplaintAnswerDto(final ComplaintAnswer complaintAnswer) {
        return new ComplaintAnswerDto(
                complaintAnswer.getId(),
                complaintAnswer.getComplaint(),
                complaintAnswer.getAnswer(),
                complaintAnswer.getCreationDate()
        );
    }
}
