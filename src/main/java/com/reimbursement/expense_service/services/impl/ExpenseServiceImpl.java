package com.reimbursement.expense_service.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.reimbursement.expense_service.dtos.ExpenseDto;
import com.reimbursement.expense_service.dtos.StatusUpdateDto;
import com.reimbursement.expense_service.entities.ExpenseEntity;
import com.reimbursement.expense_service.enums.Status;
import com.reimbursement.expense_service.exceptions.ResourceNotFound;
import com.reimbursement.expense_service.mappers.ExpenseMapper;
import com.reimbursement.expense_service.repositories.ExpenseRepository;
import com.reimbursement.expense_service.services.ExpenseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    @Override
    public List<ExpenseDto> findAll() {
        return this.expenseMapper.toDtoList(expenseRepository.findAll());
    }

    @Override
    public ExpenseDto findById(UUID id) {
        return this.expenseMapper.toDto(expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Expense not found with id: " + id)));
    }

    @Override
    public ExpenseDto create(ExpenseDto expenseDto) {

        ExpenseEntity expenseEntity = this.expenseMapper.toEntity(expenseDto);
        ExpenseEntity savedExpense = this.expenseRepository.save(expenseEntity);

        try {
            // Send the approval to MQTT
            StatusUpdateDto statusUpdate = new StatusUpdateDto();
            statusUpdate.setExpense_id(savedExpense.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create approval for expense: " + savedExpense.getId(), e);
        }

        return this.expenseMapper.toDto(savedExpense);
    }

    @Override
    public ExpenseDto update(ExpenseDto expenseDto) {
        if (!this.expenseRepository.existsById(expenseDto.getId())) {
            throw new ResourceNotFound("Expense not found with id: " + expenseDto.getId());
        }

        ExpenseEntity savedExpense = this.expenseRepository.save(this.expenseMapper.toEntity(expenseDto));

        return this.expenseMapper.toDto(savedExpense);
    }

    @Override
    public ExpenseDto updateStatus(UUID id, Status status) {
        ExpenseEntity expenseEntity = this.expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Cant update status with id: " + id));

        expenseEntity.setStatus(status);
        ExpenseEntity updatedExpense = this.expenseRepository.save(expenseEntity);

        return this.expenseMapper.toDto(updatedExpense);
    }

    @Override
    public void softDelete(UUID id) {
        ExpenseEntity expenseEntity = this.expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Expense not found with id: " + id));

        expenseEntity.setDeletedAt(Instant.now());

        this.expenseRepository.save(expenseEntity);
    }

    @Override
    public void delete(UUID id) {
        if (!this.expenseRepository.existsById(id)) {
            throw new ResourceNotFound("Expense not found with id: " + id);
        }

        this.expenseRepository.deleteById(id);
    }
}
