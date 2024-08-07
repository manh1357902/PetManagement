package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.JWT.JWTUserDetail;
import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.CareActivityNotification;
import com.project.petmanagement.petmanagement.models.entity.Pet;
import com.project.petmanagement.petmanagement.models.entity.RecurringSchedule;
import com.project.petmanagement.petmanagement.models.entity.User;
import com.project.petmanagement.petmanagement.models.enums.FrequencyEnum;
import com.project.petmanagement.petmanagement.payloads.requests.CareActivityNotificationRequest;
import com.project.petmanagement.petmanagement.repositories.CareActivityNotificationRepository;
import com.project.petmanagement.petmanagement.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CareActivityNotificationService {
    private final PetRepository petRepository;
    private final CareActivityNotificationRepository careActivityNotificationRepository;

    public List<CareActivityNotification> getCareActivityNotificationByUser() {
        User user = ((JWTUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<Pet> pets = petRepository.findByUserAndIsActiveIsTrueOrderByIdDesc(user);
        List<CareActivityNotification> careActivityNotificationList = new ArrayList<>();
        for (Pet pet : pets) {
            careActivityNotificationList.addAll(careActivityNotificationRepository.findByPet(pet));
        }
        return careActivityNotificationList;
    }

    public List<CareActivityNotification> getCareActivityNotificationByPet(Pet pet) {
        return careActivityNotificationRepository.findByPet(pet);
    }

    public List<CareActivityNotification> getCareActivityNotificationByDate(Date date) {
        LocalDate currentDate = date.toLocalDate();
        List<CareActivityNotification> careActivityNotificationList = new ArrayList<>();
        for (CareActivityNotification careActivityNotification : getCareActivityNotificationByUser()) {
            if (careActivityNotification.getNotificationStatus()) {
                RecurringSchedule recurringSchedule = careActivityNotification.getSchedule();
                FrequencyEnum frequency = recurringSchedule.getFrequency();
                int step = recurringSchedule.getValue();
                boolean onTime = false;
                if (frequency.compareTo(FrequencyEnum.NO_REPEAT) == 0 && step == 0) {
                    LocalDate scheduledDate = recurringSchedule.getDate().toLocalDate();
                    if (scheduledDate.equals(currentDate)) {
                        onTime = true;
                    }
                } else if (frequency.compareTo(FrequencyEnum.DAILY) == 0 && step > 0) {
                    LocalDate fromDate = recurringSchedule.getFromDate().toLocalDate();
                    LocalDate toDate = recurringSchedule.getToDate().toLocalDate();
                    LocalDate tempDate = fromDate;
                    while (!tempDate.isAfter(toDate)) {
                        if (tempDate.equals(currentDate)) {
                            onTime = true;
                            break;
                        } else if (tempDate.isAfter(currentDate)) {
                            break;
                        }
                        tempDate = tempDate.plusDays(step);
                    }
                } else if (frequency.compareTo(FrequencyEnum.WEEKLY) == 0 && step > 0) {
                    List<DayOfWeek> scheduledDaysOfWeek = recurringSchedule.getDaysOfWeek();
                    LocalDate fromDate = recurringSchedule.getFromDate().toLocalDate();
                    LocalDate toDate = recurringSchedule.getToDate().toLocalDate();
                    DayOfWeek dateToDayOfWeek = currentDate.getDayOfWeek();
                    if (scheduledDaysOfWeek.contains(dateToDayOfWeek)) {
                        LocalDate firstDayOfWeek = fromDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                        LocalDate lastDayOfWeek = fromDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                        boolean stop = false;
                        while (!firstDayOfWeek.isAfter(lastDayOfWeek)) {
                            if (stop) {
                                break;
                            }
                            if (scheduledDaysOfWeek.contains(firstDayOfWeek.getDayOfWeek())) {
                                LocalDate tempDate = firstDayOfWeek;
                                while (!tempDate.isAfter(toDate)) {
                                    if (!tempDate.isBefore(fromDate)) {
                                        if (tempDate.equals(currentDate)) {
                                            onTime = true;
                                            stop = true;
                                            break;
                                        } else if (tempDate.isAfter(currentDate)) {
                                            break;
                                        }
                                    }
                                    tempDate = tempDate.plusWeeks(step);
                                }
                            }
                            firstDayOfWeek = firstDayOfWeek.plusDays(1);
                        }
                    }
                }
                if (onTime) {
                    careActivityNotificationList.add(careActivityNotification);
                }
            }
        }
        return careActivityNotificationList;
    }

    public CareActivityNotification getCareActivityNotificationDetails(Long careActivityNotificationId) throws DataNotFoundException {
        return careActivityNotificationRepository.findById(careActivityNotificationId).orElseThrow(() -> new DataNotFoundException("Can not find care activity notification with ID: " + careActivityNotificationId));
    }

    @Transactional(rollbackFor = {Exception.class})
    public CareActivityNotification addCareActivityNotification(CareActivityNotificationRequest careActivityNotificationRequest) throws DataNotFoundException {
        Pet pet = petRepository.findById(careActivityNotificationRequest.getPetId()).orElseThrow(() -> new DataNotFoundException("Can not find pet with ID: " + careActivityNotificationRequest.getPetId()));
        CareActivityNotification careActivityNotification = CareActivityNotification.builder()
                .pet(pet)
                .title(careActivityNotificationRequest.getTitle())
                .note(careActivityNotificationRequest.getNote())
                .notificationStatus(true)
                .build();
        return careActivityNotificationRepository.save(careActivityNotification);
    }

    @Transactional(rollbackFor = {Exception.class})
    public CareActivityNotification updateCareActivityNotification(Long careActivityNotificationId, CareActivityNotificationRequest careActivityNotificationRequest) throws DataNotFoundException {
        CareActivityNotification existingCareActivityNotification = careActivityNotificationRepository.findById(careActivityNotificationId).orElseThrow(() -> new DataNotFoundException("Can not find activity notification with ID: " + careActivityNotificationId));
        existingCareActivityNotification.setTitle(careActivityNotificationRequest.getTitle());
        existingCareActivityNotification.setNote(careActivityNotificationRequest.getNote());
        existingCareActivityNotification.setNotificationStatus(careActivityNotificationRequest.getNotificationStatus());
        return careActivityNotificationRepository.save(existingCareActivityNotification);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteCareActivityNotification(Long careActivityNotificationId) throws DataNotFoundException {
        CareActivityNotification existingCareActivityNotification = careActivityNotificationRepository.findById(careActivityNotificationId).orElseThrow(() -> new DataNotFoundException("Can not find activity notification with ID: " + careActivityNotificationId));
        if (existingCareActivityNotification != null) {
            careActivityNotificationRepository.deleteById(careActivityNotificationId);
        }
    }
}


