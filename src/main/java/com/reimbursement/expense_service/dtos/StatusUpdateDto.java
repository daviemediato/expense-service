package com.reimbursement.expense_service.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateDto {
    @NotNull(message = "Status cannot be null")
    private String status;
}
