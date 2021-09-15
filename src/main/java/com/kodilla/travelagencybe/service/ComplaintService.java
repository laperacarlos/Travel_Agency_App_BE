package com.kodilla.travelagencybe.service;

import com.kodilla.travelagencybe.domain.Complaint;
import com.kodilla.travelagencybe.repository.ComplaintDao;
import com.kodilla.travelagencybe.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintDao complaintDao;
    private final TimeProvider timeProvider;

    public Complaint saveNewCompliant(final Complaint complaint) {
        complaint.setCreationDate(timeProvider.getTime());
        return complaintDao.save(complaint);
    }

    public Complaint saveCompliant(final Complaint complaint) {
        return complaintDao.save(complaint);
    }

    public Optional<Complaint> getComplaintById(final Long id) {
        return complaintDao.findById(id);
    }
}
