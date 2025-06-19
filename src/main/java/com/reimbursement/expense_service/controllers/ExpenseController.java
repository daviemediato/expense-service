package com.reimbursement.expense_service.controllers;

import com.reimbursement.expense_service.dtos.ExpenseDto;
import com.reimbursement.expense_service.services.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public ResponseEntity<ExpenseDto> getById(UUID id) {
        ExpenseDto expense = this.expenseService.findById(id);

        return new ResponseEntity<>(expense, HttpStatus.OK);
    }
}
