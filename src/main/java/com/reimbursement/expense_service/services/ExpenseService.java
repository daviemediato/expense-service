package com.reimbursement.expense_service.services;

import com.reimbursement.expense_service.dtos.ExpenseDto;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    public List<ExpenseDto> findAll();
    public ExpenseDto findById(UUID id);
    public ExpenseDto create(ExpenseDto expenseDto);
    public void delete(UUID id);
}
