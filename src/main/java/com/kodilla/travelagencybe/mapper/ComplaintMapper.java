package com.kodilla.travelagencybe.mapper;

import com.kodilla.travelagencybe.domain.Complaint;
import com.kodilla.travelagencybe.domain.ComplaintDto;
import org.springframework.stereotype.Service;

@Service
public class ComplaintMapper {

    public Complaint mapToComplaint(final ComplaintDto complaintDto) {
        return new Complaint(
                complaintDto.getId(),
                complaintDto.getReservation(),
                complaintDto.getDescription(),
                complaintDto.getCreationDate(),
                complaintDto.getClosingDate(),
                complaintDto.getComplaintAnswer(),
                complaintDto.getStatus()
        );
    }

    public ComplaintDto mapToComplaintDto(final Complaint complaint) {
        return new ComplaintDto(
                complaint.getId(),
                complaint.getReservation(),
                complaint.getDescription(),
                complaint.getCreationDate(),
                complaint.getClosingDate(),
                complaint.getComplaintAnswer(),
                complaint.getStatus()
        );
    }
}
