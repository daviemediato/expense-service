package com.reimbursement.expense_service.dtos;

import com.reimbursement.expense_service.enums.Status;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {
    private UUID id;

    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull(message = "User ID cannot be null")
    private String userId;

    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

    @NotNull(message = "Type cannot be null")
    private String type;

    @NotNull(message = "Status cannot be null")
    @Enumerated(STRING)
    private Status status;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
