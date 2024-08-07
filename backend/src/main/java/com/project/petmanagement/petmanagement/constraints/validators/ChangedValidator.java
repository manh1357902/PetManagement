package com.project.petmanagement.petmanagement.constraints.validators;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.petmanagement.petmanagement.JWT.JWTUserDetail;
import com.project.petmanagement.petmanagement.constraints.annotations.Changed;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ChangedValidator implements ConstraintValidator<Changed, Object> {

    private boolean condition;

    @Override
    public void initialize(Changed changed) {
        this.condition = changed.condition();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String oldPassword = ((JWTUserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPassword();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return condition ? encoder.matches((String) value, oldPassword) : !encoder.matches((String) value, oldPassword);
    }
    
}
