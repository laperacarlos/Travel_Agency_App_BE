package com.kodilla.travelagencybe.controller;

import com.kodilla.travelagencybe.domain.ComplaintAnswer;
import com.kodilla.travelagencybe.domain.ComplaintAnswerDto;
import com.kodilla.travelagencybe.exception.ComplaintAnswerNotFoundException;
import com.kodilla.travelagencybe.mapper.ComplaintAnswerMapper;
import com.kodilla.travelagencybe.service.ComplaintAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/travelAgencyBe/v1")
@RequiredArgsConstructor
public class ComplaintAnswerController {
    private final ComplaintAnswerService complaintAnswerService;
    private final ComplaintAnswerMapper complaintAnswerMapper;

    @PostMapping(value = "complaints/answers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ComplaintAnswerDto createComplaintAnswer(@RequestBody ComplaintAnswerDto complaintAnswerDto) {
        ComplaintAnswer complaintAnswer = complaintAnswerMapper.mapToComplaintAnswer(complaintAnswerDto);
        return complaintAnswerMapper.mapToComplaintAnswerDto(complaintAnswerService.saveNewAnswer(complaintAnswer));
    }

    @PutMapping(value = "complaints/answers")
    public void updateComplaintAnswer(@RequestBody ComplaintAnswerDto complaintAnswerDto) throws ComplaintAnswerNotFoundException {
        if(complaintAnswerService.getAnswerById(complaintAnswerDto.getId()).isPresent()) {
            complaintAnswerService.saveAnswer(complaintAnswerMapper.mapToComplaintAnswer(complaintAnswerDto));
        } else throw new ComplaintAnswerNotFoundException(complaintAnswerDto.getId());
    }

    @DeleteMapping(value = "complaints/answers/{id}")
    public void deleteComplaintAnswer(@PathVariable Long id) throws ComplaintAnswerNotFoundException {
        if(complaintAnswerService.getAnswerById(id).isPresent()) {
            complaintAnswerService.deleteById(id);
        } else throw new ComplaintAnswerNotFoundException(id);


    }
}
