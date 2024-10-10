package ru.liga.parcelloader.api.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TruckFormValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TruckFormMustBePresent {
    String message() default "Неправильная форма грузовика";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
