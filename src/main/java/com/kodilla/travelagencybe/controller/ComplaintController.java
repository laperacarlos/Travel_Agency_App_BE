package com.kodilla.travelagencybe.controller;

import com.kodilla.travelagencybe.domain.Complaint;
import com.kodilla.travelagencybe.domain.ComplaintDto;
import com.kodilla.travelagencybe.exception.ComplaintNotFoundException;
import com.kodilla.travelagencybe.mapper.ComplaintMapper;
import com.kodilla.travelagencybe.service.ComplaintService;
import com.kodilla.travelagencybe.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/travelAgencyBe/v1")
@RequiredArgsConstructor
public class ComplaintController {
    private final ComplaintService complaintService;
    private final ComplaintMapper complaintMapper;
    private final TimeProvider timeProvider;

    @PostMapping(value = "complaints", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createComplaint(@RequestBody ComplaintDto complaintDto) {
        Complaint complaint = complaintMapper.mapToComplaint(complaintDto);
        complaint.setCreationDate(timeProvider.getTime());
        complaintService.saveCompliant(complaint);
    }

    @PutMapping(value = "complaints")
    public void updateComplaint(@RequestBody ComplaintDto complaintDto) throws ComplaintNotFoundException {
        if(complaintService.getComplaintById(complaintDto.getId()).isPresent()) {
            complaintService.saveCompliant(complaintMapper.mapToComplaint(complaintDto));
        } else throw new ComplaintNotFoundException(complaintDto.getId());
    }
}
