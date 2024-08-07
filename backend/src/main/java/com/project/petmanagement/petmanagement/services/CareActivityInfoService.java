package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.CareActivity;
import com.project.petmanagement.petmanagement.models.entity.CareActivityInfo;
import com.project.petmanagement.petmanagement.models.entity.CareActivityNotification;
import com.project.petmanagement.petmanagement.payloads.requests.CareActivityInfoRequest;
import com.project.petmanagement.petmanagement.repositories.CareActivityInfoRepository;
import com.project.petmanagement.petmanagement.repositories.CareActivityNotificationRepository;
import com.project.petmanagement.petmanagement.repositories.CareActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CareActivityInfoService {
    private final CareActivityRepository careActivityRepository;
    private final CareActivityInfoRepository careActivityInfoRepository;
    private final CareActivityNotificationRepository careActivityNotificationRepository;

    @Transactional(rollbackFor = {Exception.class})
    public List<CareActivityInfo> addCareActivityInfoList(List<CareActivityInfoRequest> careActivityInfoRequestList, CareActivityNotification careActivityNotification) throws DataNotFoundException {
        List<CareActivityInfo> careActivityInfoList = new ArrayList<>();
        for (CareActivityInfoRequest careActivityInfoRequest : careActivityInfoRequestList) {
            CareActivity careActivity = careActivityRepository.findById(careActivityInfoRequest.getCareActivityId()).orElseThrow(() -> new DataNotFoundException("Can not find care activity with ID: " + careActivityInfoRequest.getCareActivityId()));
            CareActivityInfo careActivityInfo = CareActivityInfo.builder()
                    .careActivityNotification(careActivityNotification)
                    .careActivity(careActivity)
                    .note(careActivityInfoRequest.getNote())
                    .build();
            careActivityInfoList.add(careActivityInfo);
        }
        return careActivityInfoRepository.saveAll(careActivityInfoList);
    }

    @Transactional(rollbackFor = {Exception.class})
    public List<CareActivityInfo> updateCareActivityInfoList(Long careActivityNotificationId, List<CareActivityInfoRequest> careActivityInfoRequestList) throws DataNotFoundException {
        List<CareActivityInfo> careActivityInfoList = new ArrayList<>();
        for (CareActivityInfoRequest careActivityInfoRequest : careActivityInfoRequestList) {
            if (careActivityInfoRequest.getId() != null) {
                CareActivityInfo existingCareActivityInfo = careActivityInfoRepository.findById(careActivityInfoRequest.getId()).orElseThrow(() -> new DataNotFoundException("Can not find care activity with ID: " + careActivityInfoRequest.getId()));
                CareActivity careActivity = careActivityRepository.findById(careActivityInfoRequest.getCareActivityId()).orElseThrow(() -> new DataNotFoundException("Can not find care activity with ID: " + careActivityInfoRequest.getCareActivityId()));
                existingCareActivityInfo.setCareActivity(careActivity);
                existingCareActivityInfo.setNote(careActivityInfoRequest.getNote());
                careActivityInfoList.add(careActivityInfoRepository.save(existingCareActivityInfo));
            } else {
                CareActivityNotification careActivityNotification = careActivityNotificationRepository.findById(careActivityNotificationId).orElseThrow(() -> new DataNotFoundException("Can not find activity notification with ID: " + careActivityNotificationId));
                CareActivity careActivity = careActivityRepository.findById(careActivityInfoRequest.getCareActivityId()).orElseThrow(() -> new DataNotFoundException("Can not find care activity with ID: " + careActivityInfoRequest.getCareActivityId()));
                CareActivityInfo existingCareActivityInfo = CareActivityInfo.builder()
                        .careActivityNotification(careActivityNotification)
                        .careActivity(careActivity)
                        .note(careActivityInfoRequest.getNote())
                        .build();
                careActivityInfoList.add(careActivityInfoRepository.save(existingCareActivityInfo));
            }

        }
        return careActivityInfoList;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteCareActivityInfo(Long careActivityInfoId) throws DataNotFoundException {
        CareActivityInfo existingCareActivityInfo = careActivityInfoRepository.findById(careActivityInfoId).orElseThrow(() -> new DataNotFoundException("Can not find care activity with ID: " + careActivityInfoId));
        if (existingCareActivityInfo != null) {
            careActivityInfoRepository.deleteById(careActivityInfoId);
        }
    }
}
