package com.rabo.customer.statement.processor.service.impl;

import com.rabo.customer.statement.processor.CustomerTransactionData;
import com.rabo.customer.statement.processor.exception.ApplicationRuntimeException;
import com.rabo.customer.statement.processor.model.Results;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CustomerStatmentServiceImplTest {

    @InjectMocks
    CustomerStatementServiceImpl customerStatementService;


    @Test
    public void validTransactionsSuccess() throws Exception {
        Results results = customerStatementService.validTransactions(CustomerTransactionData.formCustomerTransactionSuccessList());
        assertEquals(results.getResult() , "SUCCESSFUL");
        assertEquals(results.getErrorRecords().size(), 0);
    }

    @Test
    public void validTransactionsDuplicateReference() throws Exception {
        Results results = customerStatementService.validTransactions(CustomerTransactionData.formCustomerTransactionDuplicateRefList());
        assertEquals(results.getResult() , "DUPLICATE_REFERENCE");
        assertEquals(results.getErrorRecords().size(), 2);
        assertEquals(results.getErrorRecords().get(0).getReference(), "12345");
        assertEquals(results.getErrorRecords().get(0).getAccountNumber(), "NL91ABNA1234567890");
        assertEquals(results.getErrorRecords().get(1).getReference(), "12345");
        assertEquals(results.getErrorRecords().get(1).getAccountNumber(), "NL91ABNA1234567891");
    }

    @Test
    public void validTransactionsIncorrectEndBalance() throws Exception {
        Results results = customerStatementService.validTransactions(CustomerTransactionData.formCustomerTransactionIncorrectEndBalList());
        assertEquals(results.getResult() , "INCORRECT_END_BALANCE");
        assertEquals(results.getErrorRecords().size(), 2);
        assertEquals(results.getErrorRecords().get(0).getReference(), "12345");
        assertEquals(results.getErrorRecords().get(0).getAccountNumber(), "NL91ABNA1234567890");
        assertEquals(results.getErrorRecords().get(1).getReference(), "1236");
        assertEquals(results.getErrorRecords().get(1).getAccountNumber(), "NL91ABNA1234567892");
    }

    @Test
    public void validTransactionsBoth() throws Exception {
        Results results = customerStatementService.validTransactions(CustomerTransactionData.formCustomerTransactionList());
        assertEquals(results.getResult() , "DUPLICATE_REFERENCE_INCORRECT_END_BALANCE");
        assertEquals(results.getErrorRecords().size(), 3);
        assertEquals(results.getErrorRecords().get(0).getReference(), "12345");
        assertEquals(results.getErrorRecords().get(0).getAccountNumber(), "NL91ABNA1234567890");
        assertEquals(results.getErrorRecords().get(1).getReference(), "12345");
        assertEquals(results.getErrorRecords().get(1).getAccountNumber(), "NL91ABNA1234567891");
        assertEquals(results.getErrorRecords().get(2).getReference(), "123456");
        assertEquals(results.getErrorRecords().get(2).getAccountNumber(), "NL91ABNA1234567893");
    }

    @Test
    public void validTransactionsException() throws Exception {
        ApplicationRuntimeException applicationRuntimeException = assertThrows(ApplicationRuntimeException.class, () -> {
            customerStatementService.validTransactions(CustomerTransactionData.formCustomerTransactionErrorList());
        });
        assertTrue(applicationRuntimeException.getMessage().contains("Exception occurred while validating the transaction"));
    }

}
