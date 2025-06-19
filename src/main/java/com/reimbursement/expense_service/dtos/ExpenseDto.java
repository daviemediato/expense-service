package com.reimbursement.expense_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {
    private UUID id;
    private String description;
    private String userId;
    private BigDecimal amount;
    private String type;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
