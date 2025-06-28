package com.reimbursement.expense_service.integration.controllers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimbursement.expense_service.dtos.ExpenseDto;
import com.reimbursement.expense_service.enums.Status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExpenseControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testCreateExpense() throws Exception {
        ExpenseDto expenseDto = new ExpenseDto(null,
                "Almoço",
                "user1",
                new BigDecimal("50.0"),
                "FOOD",
                Status.PENDING,
                null,
                null,
                null);

        ResponseEntity<String> response = createExpense(expenseDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("Almoço"));
    }

    @Test
    public void testCreateExpenseWithNullValuesShouldFail() throws Exception {
        ExpenseDto expenseDto = new ExpenseDto(null,
                null,
                null,
                null,
                null,
                Status.PENDING,
                null,
                null,
                null);

        ResponseEntity<String> response = createExpense(expenseDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    private ResponseEntity<String> createExpense(ExpenseDto expenseDto) throws Exception {
        String expenseJson = objectMapper.writeValueAsString(expenseDto);
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(expenseJson, headers);
        return restTemplate.exchange(
                this.createURLWithPort("/expenses"), HttpMethod.POST, entity, String.class);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
