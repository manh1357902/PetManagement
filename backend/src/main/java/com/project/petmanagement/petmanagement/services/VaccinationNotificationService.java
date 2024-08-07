package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.JWT.JWTUserDetail;
import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.OneTimeSchedule;
import com.project.petmanagement.petmanagement.models.entity.Pet;
import com.project.petmanagement.petmanagement.models.entity.User;
import com.project.petmanagement.petmanagement.models.entity.VaccinationNotification;
import com.project.petmanagement.petmanagement.payloads.requests.VaccinationNotificationRequest;
import com.project.petmanagement.petmanagement.repositories.PetRepository;
import com.project.petmanagement.petmanagement.repositories.VaccinationNotificationRepository;
import com.project.petmanagement.petmanagement.repositories.VaccineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VaccinationNotificationService {
    private final PetRepository petRepository;
    private final VaccineRepository vaccineRepository;
    private final VaccinationNotificationRepository vaccinationNotificationRepository;

    public List<VaccinationNotification> getVaccinationNotificationByUser() {
        User user = ((JWTUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<Pet> pets = petRepository.findByUserAndIsActiveIsTrueOrderByIdDesc(user);
        List<VaccinationNotification> vaccinationNotificationList = new ArrayList<>();
        for (Pet pet : pets) {
            vaccinationNotificationList.addAll(vaccinationNotificationRepository.findByPet(pet));
        }
        return vaccinationNotificationList;
    }

    public List<VaccinationNotification> getVaccinationNotificationByPet(Pet pet) {
        return vaccinationNotificationRepository.findByPet(pet);
    }

    public List<VaccinationNotification> getVaccinationNotificationByDate(Date date) {
        LocalDate currentDate = date.toLocalDate();
        List<VaccinationNotification> vaccinationNotificationList = new ArrayList<>();
        for (VaccinationNotification vaccinationNotification : getVaccinationNotificationByUser()) {
            boolean ok = false;
            List<OneTimeSchedule> oneTimeScheduleList = vaccinationNotification.getSchedules();
            for (OneTimeSchedule oneTimeSchedule : oneTimeScheduleList) {
                if (!oneTimeSchedule.getVaccinationStatus()) {
                    LocalDate scheduledDate = oneTimeSchedule.getDate().toLocalDate();
                    if (scheduledDate.equals(currentDate)) {
                        ok = true;
                        break;
                    }
                }
            }
            if (ok) {
                vaccinationNotificationList.add(vaccinationNotification);
            }
        }
        return vaccinationNotificationList;
    }

    public VaccinationNotification getVaccinationNotificationDetails(Long vaccinationNotificationId) throws DataNotFoundException {
        return vaccinationNotificationRepository.findById(vaccinationNotificationId).orElseThrow(() -> new DataNotFoundException("Can not find vaccination notification with ID: " + vaccinationNotificationId));
    }

    @Transactional(rollbackFor = {Exception.class})
    public VaccinationNotification addVaccinationNotification(VaccinationNotificationRequest vaccinationNotificationRequest) throws DataNotFoundException {
        VaccinationNotification vaccinationNotification = VaccinationNotification.builder()
                .pet(petRepository.findById(vaccinationNotificationRequest.getPetId()).orElseThrow(() -> new DataNotFoundException("Can not find pet with ID: " + vaccinationNotificationRequest.getPetId())))
                .vaccine(vaccineRepository.findById(vaccinationNotificationRequest.getVaccineId()).orElseThrow(() -> new DataNotFoundException("Can not find vaccine with ID: " + vaccinationNotificationRequest.getVaccineId())))
                .title(vaccinationNotificationRequest.getTitle())
                .note(vaccinationNotificationRequest.getNote())
                .build();
        return vaccinationNotificationRepository.save(vaccinationNotification);
    }

    @Transactional(rollbackFor = {Exception.class})
    public VaccinationNotification updateVaccinationNotification(Long vaccinationNotificationId, VaccinationNotificationRequest vaccinationNotificationRequest) throws DataNotFoundException {
        VaccinationNotification existingVaccinationNotification = vaccinationNotificationRepository.findById(vaccinationNotificationId).orElseThrow(() -> new DataNotFoundException("Can not find vaccination notification with ID: " + vaccinationNotificationId));
        existingVaccinationNotification.setTitle(vaccinationNotificationRequest.getTitle());
        existingVaccinationNotification.setVaccine(vaccineRepository.findById(vaccinationNotificationRequest.getVaccineId()).orElseThrow(() -> new DataNotFoundException("Can not find vaccine with ID: " + vaccinationNotificationRequest.getVaccineId())));
        existingVaccinationNotification.setNote(vaccinationNotificationRequest.getNote());
        return vaccinationNotificationRepository.save(existingVaccinationNotification);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteVaccinationNotification(Long vaccinationNotificationId) throws DataNotFoundException {
        VaccinationNotification existingVaccinationNotification = vaccinationNotificationRepository.findById(vaccinationNotificationId).orElseThrow(() -> new DataNotFoundException("Can not find vaccination notification with ID: " + vaccinationNotificationId));
        if (existingVaccinationNotification != null) {
            vaccinationNotificationRepository.deleteById(vaccinationNotificationId);
        }
    }
}
