package com.reimbursement.expense_service.dtos;

import java.util.UUID;

import com.reimbursement.expense_service.enums.Status;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateDto {

    @NotNull(message = "status cannot be null!")
    private Status status;

    private UUID expense_id;
}
