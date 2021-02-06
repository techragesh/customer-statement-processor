package com.rabo.customer.statement.processor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabo.customer.statement.processor.CustomerTransactionData;
import com.rabo.customer.statement.processor.model.CustomerTransaction;
import com.rabo.customer.statement.processor.model.Results;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerStatementControllerIntegrationTest {

    private String staticURL = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    public HttpHeaders httpHeaders;

    @BeforeEach
    public void setUp() {
        httpHeaders = new HttpHeaders();
    }

    @Test
    public void validTransactionsSuccess() throws Exception{

        String URI = "/api/v1/statement/transaction";
        String jsonOutput = this.convertToJson(CustomerTransactionData.formResultsSuccess());

        HttpEntity<List<CustomerTransaction>> httpEntity = new HttpEntity<List<CustomerTransaction>>(CustomerTransactionData.formCustomerTransactionSuccessList(),httpHeaders);

        ResponseEntity<Results> responseEntity = restTemplate.postForEntity(getCompleteEndPoint(URI), httpEntity , Results.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals("SUCCESSFUL", responseEntity.getBody().getResult());
        assertEquals(0, responseEntity.getBody().getErrorRecords().size());
        JSONAssert.assertEquals(jsonOutput, this.convertToJson(responseEntity.getBody()), false);
    }

    @Test
    public void validTransactionsDuplicateReference() throws Exception{

        String URI = "/api/v1/statement/transaction";
        String jsonOutput = this.convertToJson(CustomerTransactionData.formResultsDuplicateReference());

        HttpEntity<List<CustomerTransaction>> httpEntity = new HttpEntity<List<CustomerTransaction>>(CustomerTransactionData.formCustomerTransactionDuplicateRefList(),httpHeaders);

        ResponseEntity<Results> responseEntity = restTemplate.postForEntity(getCompleteEndPoint(URI), httpEntity , Results.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals("DUPLICATE_REFERENCE", responseEntity.getBody().getResult());
        assertEquals(2, responseEntity.getBody().getErrorRecords().size());
        JSONAssert.assertEquals(jsonOutput, this.convertToJson(responseEntity.getBody()), false);
    }

    @Test
    public void validTransactionsIncorrectEndBalance() throws Exception{

        String URI = "/api/v1/statement/transaction";
        String jsonOutput = this.convertToJson(CustomerTransactionData.formResultsIncorrectEndBalance());

        HttpEntity<List<CustomerTransaction>> httpEntity = new HttpEntity<List<CustomerTransaction>>(CustomerTransactionData.formCustomerTransactionIncorrectEndBalList(),httpHeaders);

        ResponseEntity<Results> responseEntity = restTemplate.postForEntity(getCompleteEndPoint(URI), httpEntity , Results.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals("INCORRECT_END_BALANCE", responseEntity.getBody().getResult());
        assertEquals(2, responseEntity.getBody().getErrorRecords().size());
        JSONAssert.assertEquals(jsonOutput, this.convertToJson(responseEntity.getBody()), false);
    }

    @Test
    public void validTransactionsDuplicateRefAndInCorrectEndBalance() throws Exception{
        String URI = "/api/v1/statement/transaction";
        String jsonOutput = this.convertToJson(CustomerTransactionData.formResultsBoth());

        HttpEntity<List<CustomerTransaction>> httpEntity = new HttpEntity<List<CustomerTransaction>>(CustomerTransactionData.formCustomerTransactionList(),httpHeaders);

        ResponseEntity<Results> responseEntity = restTemplate.postForEntity(getCompleteEndPoint(URI), httpEntity , Results.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE", responseEntity.getBody().getResult());
        assertEquals(3, responseEntity.getBody().getErrorRecords().size());
        JSONAssert.assertEquals(jsonOutput, this.convertToJson(responseEntity.getBody()), false);
    }

    @Test
    public void validTransactionsServerException() throws Exception{
        String URI = "/api/v1/statement/transaction";
        String jsonOutput = this.convertToJson(CustomerTransactionData.formResultsServerException());

        HttpEntity<List<CustomerTransaction>> httpEntity = new HttpEntity<List<CustomerTransaction>>(CustomerTransactionData.formCustomerTransactionErrorList(),httpHeaders);

        ResponseEntity<Results> responseEntity = restTemplate.postForEntity(getCompleteEndPoint(URI), httpEntity , Results.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals("INTERNAL_SERVER_ERROR", responseEntity.getBody().getResult());
        assertEquals(0, responseEntity.getBody().getErrorRecords().size());
        JSONAssert.assertEquals(jsonOutput, this.convertToJson(responseEntity.getBody()), false);
    }

    @Test
    public void validTransactionsBadRequest() throws Exception{

        String URI = "/api/v1/statement/transaction";
        String jsonOutput = this.convertToJson(CustomerTransactionData.formResultsClientException());

        HttpEntity<List<CustomerTransaction>> httpEntity = new HttpEntity<List<CustomerTransaction>>(CustomerTransactionData.formInvalidCustomerTransaction(),httpHeaders);

        ResponseEntity<Results> responseEntity = restTemplate.postForEntity(getCompleteEndPoint(URI), httpEntity , Results.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertEquals("BAD_REQUEST", responseEntity.getBody().getResult());
        assertEquals(0, responseEntity.getBody().getErrorRecords().size());
        JSONAssert.assertEquals(jsonOutput, this.convertToJson(responseEntity.getBody()), false);
    }


    public String convertToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    public String getCompleteEndPoint(String URI){
        return staticURL + port + URI;
    }
}
