package com.project.petmanagement.petmanagement.controllers;

import com.project.petmanagement.petmanagement.models.entity.NutritiousFood;
import com.project.petmanagement.petmanagement.payloads.responses.DataResponse;
import com.project.petmanagement.petmanagement.payloads.responses.ErrorResponse;
import com.project.petmanagement.petmanagement.services.NutritiousFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nutritious_food")
public class NutritiousFoodController {
    private final NutritiousFoodService nutritiousFoodService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllNutritiousFood() {
        List<NutritiousFood> nutritiousFoodList = nutritiousFoodService.getAllNutritiousFood();
        if (nutritiousFoodList.isEmpty()) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("There is no nutritious food in database.")
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        DataResponse dataResponse = DataResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Get all nutritious food successfully")
                .data(nutritiousFoodList)
                .build();
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findNutritiousFood(
            @RequestParam(value = "species_id", required = false) Long speciesId,
            @RequestParam(value = "food_type_id", required = false) Long foodTypeId,
            @RequestParam(value = "keywords", required = false) String keywords) {
        List<NutritiousFood> nutritiousFoodList = new ArrayList<>();
        try {
            nutritiousFoodList = nutritiousFoodService.searchNutritiousFoodByFilter(keywords, speciesId, foodTypeId);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get nutritious food successfully")
                    .data(nutritiousFoodList)
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getNutritiousFoodDetails(@PathVariable("id") Long nutritiousFoodId) {
        try {
            NutritiousFood nutritiousFood = nutritiousFoodService.getNutritiousFoodDetails(nutritiousFoodId);
            DataResponse dataResponse = DataResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get nutritious food details successfully")
                    .data(nutritiousFood)
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
