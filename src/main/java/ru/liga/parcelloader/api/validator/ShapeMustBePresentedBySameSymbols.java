package ru.liga.parcelloader.api.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ShapeFormValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ShapeMustBePresentedBySameSymbols {
    String message() default "Для представления формы необходимо использовать один и тот же символ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
