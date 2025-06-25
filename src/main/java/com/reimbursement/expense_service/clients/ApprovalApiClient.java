package com.reimbursement.expense_service.clients;

import com.reimbursement.expense_service.requests.ApprovalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ApprovalApiClient {
    private final RestTemplate restTemplate;
    private static final String APPROVAL_SERVICE_URL = "http://localhost:8081/approvals";

    public void create(UUID expenseId, String status) {
        ApprovalRequest request = new ApprovalRequest(expenseId, status);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            this.restTemplate.postForObject(
                    APPROVAL_SERVICE_URL,
                    request,
                    String.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create approval for expense: " + expenseId, e);
        }
    }
}




