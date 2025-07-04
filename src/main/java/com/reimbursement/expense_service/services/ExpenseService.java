package com.reimbursement.expense_service.services;

import java.util.List;
import java.util.UUID;

import com.reimbursement.expense_service.dtos.ExpenseDto;
import com.reimbursement.expense_service.enums.Status;

public interface ExpenseService {
    public List<ExpenseDto> findAll();

    public ExpenseDto findById(UUID id);

    public ExpenseDto create(ExpenseDto expenseDto);

    public void softDelete(UUID id);

    public void delete(UUID id);

    public ExpenseDto update(ExpenseDto expenseDto);

    public ExpenseDto updateStatus(UUID id, Status status);
}
