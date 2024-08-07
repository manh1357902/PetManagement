package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.CareActivityNotification;
import com.project.petmanagement.petmanagement.models.entity.RecurringSchedule;
import com.project.petmanagement.petmanagement.payloads.requests.RecurringScheduleRequest;
import com.project.petmanagement.petmanagement.repositories.RecurringScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecurringScheduleService {
    private final RecurringScheduleRepository recurringScheduleRepository;

    @Transactional(rollbackFor = {Exception.class})
    public RecurringSchedule addRecurringSchedule(RecurringScheduleRequest recurringScheduleRequest, CareActivityNotification careActivityNotification) throws DataNotFoundException {
        RecurringSchedule recurringSchedule = RecurringSchedule.builder()
                .careActivityNotification(careActivityNotification)
                .name(recurringScheduleRequest.getName())
                .frequency(recurringScheduleRequest.getFrequency())
                .value(recurringScheduleRequest.getValue())
                .daysOfWeek(recurringScheduleRequest.getDaysOfWeek())
                .date(recurringScheduleRequest.getDate())
                .time(recurringScheduleRequest.getTime())
                .fromDate(recurringScheduleRequest.getFromDate())
                .toDate(recurringScheduleRequest.getToDate())
                .build();
        return recurringScheduleRepository.save(recurringSchedule);
    }

    @Transactional(rollbackFor = {Exception.class})
    public RecurringSchedule updateRecurringSchedule(RecurringScheduleRequest recurringScheduleRequest) throws DataNotFoundException {
        RecurringSchedule existingRecurringSchedule = recurringScheduleRepository.findById(recurringScheduleRequest.getId()).orElseThrow(() -> new DataNotFoundException("Can not find recurring schedule with ID: " + recurringScheduleRequest.getId()));
        existingRecurringSchedule.setName(recurringScheduleRequest.getName());
        existingRecurringSchedule.setFrequency(recurringScheduleRequest.getFrequency());
        existingRecurringSchedule.setValue(recurringScheduleRequest.getValue());
        existingRecurringSchedule.setDaysOfWeek(recurringScheduleRequest.getDaysOfWeek());
        existingRecurringSchedule.setDate(recurringScheduleRequest.getDate());
        existingRecurringSchedule.setTime(recurringScheduleRequest.getTime());
        existingRecurringSchedule.setFromDate(recurringScheduleRequest.getFromDate());
        existingRecurringSchedule.setToDate(recurringScheduleRequest.getToDate());
        return recurringScheduleRepository.save(existingRecurringSchedule);
    }
}
