
package com.rabo.customer.statement.processor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class ErrorRecord.
 *
 * @author Ragesh Sharma
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorRecord {

    /**
     * Field reference
     */
    private String reference;
    /**
     * Field accountNumber
     */
    private String accountNumber;

}
