package com.reimbursement.expense_service.services;

import com.reimbursement.expense_service.dtos.ExpenseDto;
import com.reimbursement.expense_service.mappers.ExpenseMapper;
import com.reimbursement.expense_service.repositories.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    public List<ExpenseDto> findAll() {
        return expenseMapper.toDtoList(expenseRepository.findAll());
    }

    public ExpenseDto findById(UUID id) {
        return expenseMapper.toDto(expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found with id: " + id)));
    }
}
