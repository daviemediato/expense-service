package com.reimbursement.expense_service.services.impl;

import com.reimbursement.expense_service.dtos.ExpenseDto;
import com.reimbursement.expense_service.entities.ExpenseEntity;
import com.reimbursement.expense_service.exceptions.ResourceNotFound;
import com.reimbursement.expense_service.mappers.ExpenseMapper;
import com.reimbursement.expense_service.repositories.ExpenseRepository;
import com.reimbursement.expense_service.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    public List<ExpenseDto> findAll() {
        return this.expenseMapper.toDtoList(expenseRepository.findAll());
    }

    public ExpenseDto findById(UUID id) {
        return this.expenseMapper.toDto(expenseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFound("Expense not found with id: " + id)));
    }

    public ExpenseDto create(ExpenseDto expenseDto) {
        ExpenseEntity expenseEntity = this.expenseMapper.toEntity(expenseDto);
        ExpenseEntity savedExpense = this.expenseRepository.save(expenseEntity);

        return this.expenseMapper.toDto(savedExpense);
    }

    public void delete(UUID id) {
        if (!this.expenseRepository.existsById(id)) {
            throw new ResourceNotFound("Expense not found with id: " + id);
        }

        this.expenseRepository.deleteById(id);
    }
}
