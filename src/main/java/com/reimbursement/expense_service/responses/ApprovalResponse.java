package com.reimbursement.expense_service.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalResponse {
    private UUID id;
    private UUID expense_id;
    private String status;
}
