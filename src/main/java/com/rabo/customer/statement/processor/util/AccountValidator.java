package com.rabo.customer.statement.processor.util;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang.StringUtils;

/**
 * Custom Validation for the IBAN Account Number
 *
 * @author Ragesh Sharma
 */
public class AccountValidator implements ConstraintValidator<AccountValidation, String> {

    /**
     * IBAN Account Number Pattern
     */
    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("[A-Z]{2}[0-9]{2}[A-Z]{4}[0-9]{10}");

    @Override
    public boolean isValid(String accountNumber, ConstraintValidatorContext constraintValidatorContext) {
        return !StringUtils.isEmpty(accountNumber) && ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).matches();
    }
}
