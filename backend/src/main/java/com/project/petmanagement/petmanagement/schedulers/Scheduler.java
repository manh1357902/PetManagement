package com.project.petmanagement.petmanagement.schedulers;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.*;
import com.project.petmanagement.petmanagement.models.enums.FrequencyEnum;
import com.project.petmanagement.petmanagement.payloads.requests.CareActivityNotificationRequest;
import com.project.petmanagement.petmanagement.payloads.requests.FCMNotification;
import com.project.petmanagement.petmanagement.repositories.CareActivityNotificationRepository;
import com.project.petmanagement.petmanagement.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class Scheduler {
    private final CareActivityNotificationRepository careActivityNotificationRepository;
    private final UserService userService;
    private final PetService petService;
    private final VaccinationNotificationService vaccinationNotificationService;
    private final CareActivityNotificationService careActivityNotificationService;
    private final FirebaseService firebaseService;

    private LocalDate isValid(int year, int month, int day) {
        try {
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            return null;
        }
    }

    @Scheduled(cron = "0 0 6 * * *")
    @Transactional
    public void sendVaccinationNotification() {
        log.info("Start to send vaccination notification ...");
        LocalDate currentDate = LocalDate.now();
        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            List<Pet> petList = petService.getPetsByUser(user);
            for (Pet pet : petList) {
                List<VaccinationNotification> vaccinationNotificationList = vaccinationNotificationService.getVaccinationNotificationByPet(pet);
                for (VaccinationNotification vaccinationNotification : vaccinationNotificationList) {
                    List<OneTimeSchedule> oneTimeScheduleList = vaccinationNotification.getSchedules();
                    for (OneTimeSchedule oneTimeSchedule : oneTimeScheduleList) {
                        if (!oneTimeSchedule.getVaccinationStatus()) {
                            LocalDate scheduledDate = oneTimeSchedule.getDate().toLocalDate();
                            String time = oneTimeSchedule.getTime();
                            if (scheduledDate.equals(currentDate)) {
                                log.info("User's ID: " + user.getId());
                                log.info("FCM Token: " + user.getFcmToken());
                                log.info("Pet's ID: " + pet.getId());
                                log.info("Date: " + scheduledDate);
                                FCMNotification fcmNotification = FCMNotification.builder().fcmToken(user.getFcmToken()).title(vaccinationNotification.getTitle()).body(time + ": " + vaccinationNotification.getNote()).build();
                                try {
                                    String message = firebaseService.pushNotification(fcmNotification);
                                    log.info(message);
                                } catch (FirebaseMessagingException e) {
                                    log.error(e.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void sendCareActivityNotification() {
        log.info("Start to send care activity notification ...");
        LocalDate currentDate = LocalDate.now();
        String currentTime = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();
        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            List<Pet> petList = petService.getPetsByUser(user);
            for (Pet pet : petList) {
                List<CareActivityNotification> careActivityNotificationList = careActivityNotificationService.getCareActivityNotificationByPet(pet);
                for (CareActivityNotification careActivityNotification : careActivityNotificationList) {
                    if (careActivityNotification.getNotificationStatus()) {
                        RecurringSchedule recurringSchedule = careActivityNotification.getSchedule();
                        FrequencyEnum frequency = recurringSchedule.getFrequency();
                        int step = recurringSchedule.getValue();
                        String scheduledTime = recurringSchedule.getTime();
                        boolean onTime = false;
                        if (frequency.compareTo(FrequencyEnum.NO_REPEAT) == 0 && step == 0) {
                            LocalDate scheduledDate = recurringSchedule.getDate().toLocalDate();
                            if (scheduledDate.equals(currentDate) && scheduledTime.equals(currentTime)) {
                                log.info("NO_REPEAT: OK");
                                onTime = true;
                            }
                        } else if (frequency.compareTo(FrequencyEnum.DAILY) == 0 && step > 0) {
                            LocalDate fromDate = recurringSchedule.getFromDate().toLocalDate();
                            LocalDate toDate = recurringSchedule.getToDate().toLocalDate();
                            LocalDate date = fromDate;
                            while (!date.isAfter(toDate)) {
                                log.info("DAILY: " + date);
                                if (date.equals(currentDate) && scheduledTime.equals(currentTime)) {
                                    log.info("DAILY: OK");
                                    onTime = true;
                                    break;
                                } else if (date.isAfter(currentDate)) {
                                    break;
                                }
                                date = date.plusDays(step);
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
                                                log.info("WEEKLY: " + tempDate);
                                                if (tempDate.equals(currentDate) && scheduledTime.equals(currentTime)) {
                                                    log.info("WEEKLY: OK");
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
                            log.info("User's ID: " + user.getId());
                            log.info("FCM Token: " + user.getFcmToken());
                            log.info("Pet's ID: " + pet.getId());
                            log.info("Frequency: " + frequency.name());
                            log.info("Date: " + currentDate);
                            FCMNotification fcmNotification = FCMNotification.builder().fcmToken(user.getFcmToken()).title(careActivityNotification.getTitle()).body(careActivityNotification.getNote()).build();
                            try {
                                String message = firebaseService.pushNotification(fcmNotification);
                                log.info(message);
                            } catch (FirebaseMessagingException e) {
                                log.error(e.getMessage());
                            }
                            if (frequency.compareTo(FrequencyEnum.NO_REPEAT) == 0) {
                                careActivityNotification.setNotificationStatus(false);
                                careActivityNotificationRepository.save(careActivityNotification);
                            }
                        }
                    }
                }
            }
        }
    }
}
