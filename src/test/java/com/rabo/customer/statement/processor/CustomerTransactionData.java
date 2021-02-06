package com.rabo.customer.statement.processor;

import com.rabo.customer.statement.processor.model.CustomerTransaction;
import com.rabo.customer.statement.processor.model.ErrorRecord;
import com.rabo.customer.statement.processor.model.Results;
import java.util.Arrays;
import java.util.List;

public class CustomerTransactionData {

    public static List<CustomerTransaction> formCustomerTransactionSuccessList() {

        CustomerTransaction transaction1 = new CustomerTransaction();
        transaction1.setTransactionReference(12345L);
        transaction1.setAccountNumber("NL91ABNA1234567890");
        transaction1.setStartBalance("3000.00");
        transaction1.setMutation("+200.00");
        transaction1.setEndBalance("3200.00");
        transaction1.setDescription("Groceries");

        CustomerTransaction transaction2 = new CustomerTransaction();
        transaction2.setTransactionReference(1234L);
        transaction2.setAccountNumber("NL91ABNA1234567891");
        transaction2.setStartBalance("2000.00");
        transaction2.setMutation("-100.00");
        transaction2.setEndBalance("1900.00");
        transaction2.setDescription("Shopping");

        List<CustomerTransaction> customerTransactions = Arrays.asList(transaction1, transaction2);
        return customerTransactions;
    }

    public static List<CustomerTransaction> formCustomerTransactionDuplicateRefList() {

        CustomerTransaction transaction1 = new CustomerTransaction();
        transaction1.setTransactionReference(12345L);
        transaction1.setAccountNumber("NL91ABNA1234567890");
        transaction1.setStartBalance("3000.00");
        transaction1.setMutation("+200.00");
        transaction1.setEndBalance("3200.00");
        transaction1.setDescription("Groceries");

        CustomerTransaction transaction2 = new CustomerTransaction();
        transaction2.setTransactionReference(12345L);
        transaction2.setAccountNumber("NL91ABNA1234567891");
        transaction2.setStartBalance("2000.00");
        transaction2.setMutation("-100.00");
        transaction2.setEndBalance("1900.00");
        transaction2.setDescription("Shopping");

        CustomerTransaction transaction3 = new CustomerTransaction();
        transaction3.setTransactionReference(1234L);
        transaction3.setAccountNumber("NL91ABNA1234567892");
        transaction3.setStartBalance("2000.00");
        transaction3.setMutation("-100.00");
        transaction3.setEndBalance("1900.00");
        transaction3.setDescription("Movies");

        List<CustomerTransaction> customerTransactions = Arrays.asList(transaction1,transaction2, transaction3);
        return customerTransactions;
    }


    public static List<CustomerTransaction> formCustomerTransactionIncorrectEndBalList() {

        CustomerTransaction transaction1 = new CustomerTransaction();
        transaction1.setTransactionReference(12345L);
        transaction1.setAccountNumber("NL91ABNA1234567890");
        transaction1.setStartBalance("3000.00");
        transaction1.setMutation("+200.00");
        transaction1.setEndBalance("3500.00");
        transaction1.setDescription("Groceries");

        CustomerTransaction transaction2 = new CustomerTransaction();
        transaction2.setTransactionReference(1234L);
        transaction2.setAccountNumber("NL91ABNA1234567891");
        transaction2.setStartBalance("2000.00");
        transaction2.setMutation("-100.00");
        transaction2.setEndBalance("1900.00");
        transaction2.setDescription("Shopping");

        CustomerTransaction transaction3 = new CustomerTransaction();
        transaction3.setTransactionReference(1236L);
        transaction3.setAccountNumber("NL91ABNA1234567892");
        transaction3.setStartBalance("2000.00");
        transaction3.setMutation("-100.00");
        transaction3.setEndBalance("1800.00");
        transaction3.setDescription("Movies");

        List<CustomerTransaction> customerTransactions = Arrays.asList(transaction1, transaction2, transaction3);

        return customerTransactions;
    }


    public static List<CustomerTransaction> formCustomerTransactionList() {

        CustomerTransaction transaction1 = new CustomerTransaction();
        transaction1.setTransactionReference(12345L);
        transaction1.setAccountNumber("NL91ABNA1234567890");
        transaction1.setStartBalance("3000.00");
        transaction1.setMutation("+200.00");
        transaction1.setEndBalance("3400.00");
        transaction1.setDescription("Groceries");

        CustomerTransaction transaction2 = new CustomerTransaction();
        transaction2.setTransactionReference(12345L);
        transaction2.setAccountNumber("NL91ABNA1234567891");
        transaction2.setStartBalance("2000.00");
        transaction2.setMutation("-100.00");
        transaction2.setEndBalance("1900.00");
        transaction2.setDescription("Shopping");

        CustomerTransaction transaction3 = new CustomerTransaction();
        transaction3.setTransactionReference(1234L);
        transaction3.setAccountNumber("NL91ABNA1234567892");
        transaction3.setStartBalance("3300.00");
        transaction3.setMutation("-200.00");
        transaction3.setEndBalance("3100.00");
        transaction3.setDescription("Movies");

        CustomerTransaction transaction4 = new CustomerTransaction();
        transaction4.setTransactionReference(123456L);
        transaction4.setAccountNumber("NL91ABNA1234567893");
        transaction4.setStartBalance("3700.00");
        transaction4.setMutation("-200.00");
        transaction4.setEndBalance("3100.00");
        transaction4.setDescription("Outing");

        List<CustomerTransaction> customerTransactions = Arrays.asList(transaction1, transaction2, transaction3, transaction4);

        return customerTransactions;
    }

    public static List<CustomerTransaction> formInvalidCustomerTransaction() {

        CustomerTransaction transaction1 = new CustomerTransaction();
        transaction1.setTransactionReference(12345L);
        transaction1.setAccountNumber("NL91ABNA1234567890");
        transaction1.setStartBalance("");
        transaction1.setMutation("+200.00");
        transaction1.setEndBalance("3200.00");
        transaction1.setDescription("Groceries");

        List<CustomerTransaction> customerTransactions = Arrays.asList(transaction1);

        return customerTransactions;
    }

    public static List<CustomerTransaction> formCustomerTransactionErrorList() {

        CustomerTransaction transaction1 = new CustomerTransaction();
        transaction1.setTransactionReference(12345L);
        transaction1.setAccountNumber("NL91ABNA1234567890");
        transaction1.setStartBalance("test");
        transaction1.setMutation("+200.00");
        transaction1.setEndBalance("3200.00");
        transaction1.setDescription("Groceries");

        List<CustomerTransaction> customerTransactions = Arrays.asList(transaction1);

        return customerTransactions;
    }

    public static Results formResultsSuccess() {
        Results results = new Results();
        results.setResult("SUCCESSFUL");
        return results;
    }

    public static Results formResultsServerException() {
        Results results = new Results();
        results.setResult("INTERNAL_SERVER_ERROR");
        return results;
    }

    public static Results formResultsClientException() {
        Results results = new Results();
        results.setResult("BAD_REQUEST");
        return results;
    }

    public static Results formResultsDuplicateReference() {
        Results results = new Results();
        results.setResult("DUPLICATE_REFERENCE");

        ErrorRecord record1 = new ErrorRecord();
        record1.setReference("12345");
        record1.setAccountNumber("NL91ABNA1234567890");

        ErrorRecord record2 = new ErrorRecord();
        record2.setReference("12345");
        record2.setAccountNumber("NL91ABNA1234567891");

        results.setErrorRecords(Arrays.asList(record1, record2));
        return results;
    }

    public static Results formResultsIncorrectEndBalance() {
        Results results = new Results();
        results.setResult("INCORRECT_END_BALANCE");

        ErrorRecord record1 = new ErrorRecord();
        record1.setReference("12345");
        record1.setAccountNumber("NL91ABNA1234567890");

        ErrorRecord record2 = new ErrorRecord();
        record2.setReference("1236");
        record2.setAccountNumber("NL91ABNA1234567892");

        results.setErrorRecords(Arrays.asList(record1, record2));
        return results;
    }

    public static Results formResultsBoth() {
        Results results = new Results();
        results.setResult("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE");

        ErrorRecord record1 = new ErrorRecord();
        record1.setReference("12345");
        record1.setAccountNumber("NL91ABNA1234567890");

        ErrorRecord record2 = new ErrorRecord();
        record2.setReference("12345");
        record2.setAccountNumber("NL91ABNA1234567891");

        ErrorRecord record3 = new ErrorRecord();
        record3.setReference("123456");
        record3.setAccountNumber("NL91ABNA1234567893");

        results.setErrorRecords(Arrays.asList(record1, record2, record3));

        return results;
    }
}
