package com.project.petmanagement.petmanagement.constraints.annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.project.petmanagement.petmanagement.constraints.validators.ChangedValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ChangedValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Changed {
    String message() default "Data is not same"; 

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean condition() default true;
}
