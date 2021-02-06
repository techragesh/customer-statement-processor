package com.rabo.customer.statement.processor.service.impl;

import com.rabo.customer.statement.processor.common.CommonConstants;
import com.rabo.customer.statement.processor.exception.ApplicationRuntimeException;
import com.rabo.customer.statement.processor.model.CustomerTransaction;
import com.rabo.customer.statement.processor.model.ErrorRecord;
import com.rabo.customer.statement.processor.model.Results;
import com.rabo.customer.statement.processor.service.CustomerStatementService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * Implementation of the Customer Statement Transaction Service
 *
 * @author Ragesh Sharma
 */
@Service
@Slf4j
public class CustomerStatementServiceImpl implements CustomerStatementService {

    @Override
    public Results validTransactions(List<CustomerTransaction> transactions) {
        log.info("CustomerStatementServiceImpl addTransactions");
        Results results;
        try {
            //Get Duplicate Reference
            List<ErrorRecord> duplicateRefList = findDuplicateRef(transactions);
            //Get Incorrect End Balance
            List<ErrorRecord> incorrectEndBalList = findIncorrectEndBalance(transactions);
            results = buildResults(duplicateRefList, incorrectEndBalList);
        } catch (NumberFormatException e) {
            throw new ApplicationRuntimeException("Exception occurred while validating the transaction", e);
        }
        return results;
    }

    private Results buildResults(List<ErrorRecord> duplicateRefList, List<ErrorRecord> incorrectEndBalList) {
        Results results = new Results();
        List<ErrorRecord> errorRecords = new ArrayList<>();
        //Build Error Records for Duplicate Reference
        buildErrorRecords(results, duplicateRefList, errorRecords, CommonConstants.DUPLICATE_REFERENCE);
        //Build Error Records for Incorrect End Balance
        buildErrorRecords(results, incorrectEndBalList, errorRecords, CommonConstants.INCORRECT_END_BALANCE);
        //Build Error Records for Duplicate Reference and Incorrect End Balance
        errorRecords = formDuplicateAndIncorrectBalance(results, duplicateRefList, incorrectEndBalList, errorRecords);
        //No Error Records
        results.setResult(errorRecords.isEmpty() ? CommonConstants.SUCCESSFUL : results.getResult());
        results.setErrorRecords(errorRecords);
        return results;
    }

    private List<ErrorRecord> findDuplicateRef(List<CustomerTransaction> transactions) {
        return transactions
                .stream()
                .filter(record -> Collections.frequency(transactions, record) > 1)
                .map(transaction -> formErrorRecord(transaction))
                .collect(Collectors.toList());
    }

    private List<ErrorRecord> findIncorrectEndBalance(List<CustomerTransaction> transactions) {
        return transactions
                .stream()
                .filter(record -> isIncorrectEndBalance(record))
                .map(transaction -> formErrorRecord(transaction))
                .collect(Collectors.toList());
    }

    private boolean isIncorrectEndBalance(CustomerTransaction transaction) {
        return doubleValue(transaction.getStartBalance())
                + doubleValue(transaction.getMutation()) != doubleValue(transaction.getEndBalance());
    }

    private ErrorRecord formErrorRecord(CustomerTransaction transaction) {
        ErrorRecord errorRecord = new ErrorRecord();
        errorRecord.setReference(String.valueOf(transaction.getTransactionReference()));
        errorRecord.setAccountNumber(transaction.getAccountNumber());
        return errorRecord;
    }

    private List<ErrorRecord> formDuplicateAndIncorrectBalance(Results results, List<ErrorRecord> duplicateRefList, List<ErrorRecord> incorrectEndBalList, List<ErrorRecord> errorRecords) {
        if (!duplicateRefList.isEmpty() && !incorrectEndBalList.isEmpty()) {
            results.setResult(CommonConstants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);
        }
        return errorRecords.stream().distinct().collect(Collectors.toList());
    }

    private void buildErrorRecords(Results results, List<ErrorRecord> incorrectData, List<ErrorRecord> errorRecords, String errorDefinition) {
        if (!isEmpty(incorrectData)) {
            results.setResult(errorDefinition);
            errorRecords.addAll(incorrectData);
        }
    }

    private Double doubleValue(String amount) {
        return Double.valueOf(amount);
    }
}
