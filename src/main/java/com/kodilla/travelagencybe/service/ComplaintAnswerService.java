package com.kodilla.travelagencybe.service;

import com.kodilla.travelagencybe.domain.ComplaintAnswer;
import com.kodilla.travelagencybe.repository.ComplaintAnswerDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintAnswerService {

    private final ComplaintAnswerDao complaintAnswerDao;

    public ComplaintAnswer saveAnswer(final ComplaintAnswer complaintAnswer) {
        return complaintAnswerDao.save(complaintAnswer);
    }

    public Optional<ComplaintAnswer> getAnswerById(final Long id) {
        return complaintAnswerDao.findById(id);
    }

    public List<ComplaintAnswer> getAllAnswers() {
        return complaintAnswerDao.findAll();
    }
}
