package com.reimbursement.expense_service.controllers;

import com.reimbursement.expense_service.dtos.ExpenseDto;
import com.reimbursement.expense_service.dtos.StatusUpdateDto;
import com.reimbursement.expense_service.enums.Status;
import com.reimbursement.expense_service.services.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "expenses")
public class ExpenseController
{
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDto>> get() {
        List<ExpenseDto> expenses = this.expenseService.findAll();

        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> find(@PathVariable UUID id) {
        ExpenseDto expense = this.expenseService.findById(id);

        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExpenseDto> create(@RequestBody @Valid ExpenseDto expenseDto) {
        ExpenseDto createdExpense = this.expenseService.create(expenseDto);

        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ExpenseDto> update(@RequestBody @Valid ExpenseDto expenseDto
    ) {
        ExpenseDto updatedExpense = this.expenseService.update(expenseDto);

        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ExpenseDto> updateStatus(
            @PathVariable UUID id,
            @RequestBody @Valid StatusUpdateDto statusUpdate
    ) {
        ExpenseDto updatedExpense = this.expenseService.updateStatus(id, statusUpdate.getStatus());

        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.expenseService.softDelete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
