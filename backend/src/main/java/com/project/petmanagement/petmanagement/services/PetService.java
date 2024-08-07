package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.JWT.JWTUserDetail;
import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.Breed;
import com.project.petmanagement.petmanagement.models.entity.Pet;
import com.project.petmanagement.petmanagement.models.entity.User;
import com.project.petmanagement.petmanagement.payloads.requests.PetRequest;
import com.project.petmanagement.petmanagement.repositories.BreedRepository;
import com.project.petmanagement.petmanagement.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petsRepository;
    private final BreedRepository breedRepository;

    public List<Pet> getPetsByUser(User user) {
        return petsRepository.findByUserAndIsActiveIsTrueOrderByIdDesc(user);
    }

    public Pet getPet(Long petId) throws Exception {
        return petsRepository.findByIdAndIsActiveIsTrue(petId).orElseThrow(() -> new DataNotFoundException("Can not find pet with ID: " + petId + ", or pet was deleted"));
    }

    @Transactional(rollbackFor = {Exception.class})
    public Pet addPet(PetRequest petRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JWTUserDetail userDetail = (JWTUserDetail) authentication.getPrincipal();
        Breed breed = breedRepository.findById(petRequest.getBreedId()).orElseThrow(() -> new DataNotFoundException("Can not find breed with ID: " + petRequest.getBreedId()));
        Pet pet = Pet.builder()
                .user(userDetail.getUser())
                .fullName(petRequest.getFullName())
                .breed(breed)
                .gender(petRequest.getGender())
                .dateOfBirth(petRequest.getDateOfBirth())
                .description(petRequest.getDescription())
                .image(petRequest.getImage())
                .isNeutered(petRequest.getIsNeutered())
                .isActive(true)
                .build();
        return petsRepository.save(pet);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Pet updatePet(Long petId, PetRequest petRequest) throws Exception {
        Pet existingPet = petsRepository.findById(petId).orElseThrow(() -> new DataNotFoundException("Can not find pet with ID: " + petId));
        if (existingPet != null) {
            Breed breed = breedRepository.findById(petRequest.getBreedId()).orElseThrow(() -> new DataNotFoundException("Can not find breed with ID: " + petRequest.getBreedId()));
            existingPet.setFullName(petRequest.getFullName());
            existingPet.setBreed(breed);
            existingPet.setGender(petRequest.getGender());
            existingPet.setDateOfBirth(petRequest.getDateOfBirth());
            existingPet.setDescription(petRequest.getDescription());
            existingPet.setImage(petRequest.getImage());
            existingPet.setIsNeutered(petRequest.getIsNeutered());
            return petsRepository.save(existingPet);
        }
        return null;
    }

    @Transactional(rollbackFor = {Exception.class})
    public Pet deletePet(Long petId) throws Exception {
        Pet existingPet = petsRepository.findById(petId).orElseThrow(() -> new DataNotFoundException("Can not find pet with ID: " + petId));
        existingPet.setIsActive(false);
        return petsRepository.save(existingPet);
    }
}
