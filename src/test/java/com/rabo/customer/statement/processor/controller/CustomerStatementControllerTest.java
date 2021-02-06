package com.rabo.customer.statement.processor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabo.customer.statement.processor.CustomerTransactionData;
import com.rabo.customer.statement.processor.exception.ApplicationExceptionHandler;
import com.rabo.customer.statement.processor.exception.ApplicationRuntimeException;
import com.rabo.customer.statement.processor.model.CustomerTransaction;
import com.rabo.customer.statement.processor.model.Results;
import com.rabo.customer.statement.processor.service.CustomerStatementService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class CustomerStatementControllerTest {

    private MockMvc mvc;

    @Mock
    private CustomerStatementService customerStatementService;

    @InjectMocks
    private CustomerStatementController customerStatementController;

    @Captor
    private ArgumentCaptor<List<CustomerTransaction>> listArgumentCaptor;

    private JacksonTester<List<CustomerTransaction>> jsonCustomerTransactions;

    private JacksonTester<Results> jsonResults;


    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(customerStatementController)
                .setControllerAdvice(new ApplicationExceptionHandler())
                .build();
    }

    @Test
    public void validTransactionsSuccess() throws Exception {

        when(customerStatementService.validTransactions(listArgumentCaptor.capture())).thenReturn(CustomerTransactionData.formResultsSuccess());

        MockHttpServletResponse response = mvc.perform(
                post("/api/v1/statement/transaction").contentType(MediaType.APPLICATION_JSON).content(
                        jsonCustomerTransactions.write(CustomerTransactionData.formCustomerTransactionSuccessList()).getJson()
                )).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertNotNull(response.getContentAsString());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonResults.write(CustomerTransactionData.formResultsSuccess()).getJson()
        );
        assertTrue(response.getContentAsString().contains("SUCCESSFUL"));
    }

    @Test
    public void validTransactionsDuplicateReference() throws Exception {

        when(customerStatementService.validTransactions(listArgumentCaptor.capture())).thenReturn(CustomerTransactionData.formResultsDuplicateReference());

        MockHttpServletResponse response = mvc.perform(
                post("/api/v1/statement/transaction").contentType(MediaType.APPLICATION_JSON).content(
                        jsonCustomerTransactions.write(CustomerTransactionData.formCustomerTransactionDuplicateRefList()).getJson()
                )).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertNotNull(response.getContentAsString());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonResults.write(CustomerTransactionData.formResultsDuplicateReference()).getJson()
        );
        assertTrue(response.getContentAsString().contains("DUPLICATE_REFERENCE"));
    }

    @Test
    public void validTransactionsIncorrectEndBalance() throws Exception {

        when(customerStatementService.validTransactions(listArgumentCaptor.capture())).thenReturn(CustomerTransactionData.formResultsIncorrectEndBalance());

        MockHttpServletResponse response = mvc.perform(
                post("/api/v1/statement/transaction").contentType(MediaType.APPLICATION_JSON).content(
                        jsonCustomerTransactions.write(CustomerTransactionData.formCustomerTransactionIncorrectEndBalList()).getJson()
                )).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertNotNull(response.getContentAsString());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonResults.write(CustomerTransactionData.formResultsIncorrectEndBalance()).getJson()
        );
        assertTrue(response.getContentAsString().contains("INCORRECT_END_BALANCE"));
    }


    @Test
    public void validTransactionsBoth() throws Exception {

        when(customerStatementService.validTransactions(listArgumentCaptor.capture())).thenReturn(CustomerTransactionData.formResultsBoth());

        MockHttpServletResponse response = mvc.perform(
                post("/api/v1/statement/transaction").contentType(MediaType.APPLICATION_JSON).content(
                        jsonCustomerTransactions.write(CustomerTransactionData.formCustomerTransactionList()).getJson()
                )).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertNotNull(response.getContentAsString());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonResults.write(CustomerTransactionData.formResultsBoth()).getJson()
        );
    }

    @Test
    public void validTransactionsServerException() throws Exception {

        when(customerStatementService.validTransactions(listArgumentCaptor.capture())).thenThrow(new ApplicationRuntimeException("Expected error"));

        MockHttpServletResponse response = mvc.perform(
                post("/api/v1/statement/transaction").contentType(MediaType.APPLICATION_JSON).content(
                        jsonCustomerTransactions.write(CustomerTransactionData.formCustomerTransactionErrorList()).getJson()
                )).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertNotNull(response.getContentAsString());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonResults.write(CustomerTransactionData.formResultsServerException()).getJson()
        );
        assertTrue(response.getContentAsString().contains("INTERNAL_SERVER_ERROR"));
    }

    @Test
    public void validTransactionsClientException() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post("/api/v1/statement/transaction").contentType(MediaType.APPLICATION_JSON).content(""
                )).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertNotNull(response.getContentAsString());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonResults.write(CustomerTransactionData.formResultsClientException()).getJson()
        );
        assertTrue(response.getContentAsString().contains("BAD_REQUEST"));
    }

}
