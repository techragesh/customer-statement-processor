package com.rabo.customer.statement.processor.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Custom Validation Annotation for IBAN number
 *
 * @author Ragesh Sharma
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = AccountValidator.class)
public @interface AccountValidation {

    String message() default "This is not the valid IBAN Number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
