package com.kodilla.travelagencybe.service;

import com.kodilla.travelagencybe.domain.ComplaintAnswer;
import com.kodilla.travelagencybe.repository.ComplaintAnswerDao;
import com.kodilla.travelagencybe.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintAnswerService {

    private final ComplaintAnswerDao complaintAnswerDao;
    private final TimeProvider timeProvider;

    public ComplaintAnswer saveNewAnswer(final ComplaintAnswer complaintAnswer) {
        complaintAnswer.setCreationDate(timeProvider.getTime());
        return complaintAnswerDao.save(complaintAnswer);
    }

    public ComplaintAnswer saveAnswer(final ComplaintAnswer complaintAnswer) {
        return complaintAnswerDao.save(complaintAnswer);
    }

    public Optional<ComplaintAnswer> getAnswerById(final Long id) {
        return complaintAnswerDao.findById(id);
    }

    public void deleteById(final Long id) {
        complaintAnswerDao.deleteById(id);
    }
}
