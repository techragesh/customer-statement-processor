
package com.rabo.customer.statement.processor.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Results.
 *
 * @author Ragesh Sharma
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Results {

    /**
     * Field result
     */
    private String result;
    /**
     * Field errorRecords
     */
    private List<ErrorRecord> errorRecords=new ArrayList<>();
}
