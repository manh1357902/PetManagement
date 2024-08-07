package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.models.entity.CareActivity;
import com.project.petmanagement.petmanagement.repositories.CareActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CareActivityService {
    private final CareActivityRepository careActivityRepository;

    public List<CareActivity> getAllCareActivities(){
        return careActivityRepository.findAll();
    }
}
