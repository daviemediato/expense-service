package com.reimbursement.expense_service.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalRequest {
    private UUID expense_id;
    private String status;
}
