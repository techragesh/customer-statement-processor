package com.rabo.customer.statement.processor.controller;


import com.rabo.customer.statement.processor.model.CustomerTransaction;
import com.rabo.customer.statement.processor.model.Results;
import com.rabo.customer.statement.processor.service.CustomerStatementService;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller of Customer Statement Processor
 *
 * @author Ragesh Sharma
 */
@RestController
@RequestMapping("/api/v1/statement")
@Slf4j
@Validated
public class CustomerStatementController {


    /**
     * Instantiates customerStatementService
     */
    @Autowired
    private CustomerStatementService customerStatementService;

    /**
     * Add Customer Transactions
     *
     * @param transactions list of customer transactions
     * @return results object with message and error records
     */
    @PostMapping("/transaction")
    public ResponseEntity<Results> validTransactions(@RequestBody List<@Valid CustomerTransaction> transactions) {
       return ResponseEntity.ok(customerStatementService.validTransactions(transactions));
    }
}
