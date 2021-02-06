package com.rabo.customer.statement.processor.service;


import com.rabo.customer.statement.processor.model.CustomerTransaction;
import com.rabo.customer.statement.processor.model.Results;
import java.util.List;

/**
 * Service for handling Customer Statement Transactions
 *
 * @author Ragesh Sharma
 */
public interface CustomerStatementService {

    /**
     * Validate the Transactions
     * @param transactions list of customer transactions
     * @return Results object
     */
    Results validTransactions(List<CustomerTransaction> transactions);
}
