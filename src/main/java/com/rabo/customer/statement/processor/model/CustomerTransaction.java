package com.rabo.customer.statement.processor.model;

import com.rabo.customer.statement.processor.util.AccountValidation;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The Class CustomerTransaction.
 *
 * @author Ragesh Sharma
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CustomerTransaction {

    /**
     * Field transactionReference
     */
    @Min(1)
    @EqualsAndHashCode.Include
    private Long transactionReference;
    /**
     * Field accountNumber
     */
    @NotEmpty
    @AccountValidation
    private String accountNumber;
    /**
     * Field startBalance
     */
    @NotEmpty
    private String startBalance;
    /**
     * Field mutation
     */
    @NotEmpty
    private String mutation;
    /**
     * Field description
     */
    private String description;
    /**
     * Field endBalance
     */
    @NotEmpty
    private String endBalance;

}
