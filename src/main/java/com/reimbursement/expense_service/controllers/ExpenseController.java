package com.reimbursement.expense_service.controllers;

import java.util.List;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reimbursement.expense_service.dtos.ExpenseDto;
import com.reimbursement.expense_service.dtos.StatusUpdateDto;
import com.reimbursement.expense_service.services.ApprovalTopicService;
import com.reimbursement.expense_service.services.ExpenseService;
import com.reimbursement.expense_service.utils.annotations.Log;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ApprovalTopicService approvalTopicService;

    @Log(message = "Fetching all expenses")
    @GetMapping
    public ResponseEntity<List<ExpenseDto>> get() {
        List<ExpenseDto> expenses = this.expenseService.findAll();

        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @Log(message = "Finding expense by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> find(@PathVariable UUID id) {
        ExpenseDto expense = this.expenseService.findById(id);

        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @Log(message = "Creating a new expense")
    @PostMapping
    public ResponseEntity<ExpenseDto> create(@RequestBody @Valid ExpenseDto expenseDto) {
        ExpenseDto createdExpense = this.expenseService.create(expenseDto);

        try {

            StatusUpdateDto approval = new StatusUpdateDto();
            approval.setExpense_id(createdExpense.getId());
            approvalTopicService.publishMessage(approval);

        } catch (MqttException e) {
            System.out.println(e);
        }

        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }

    @Log(message = "Updating an existing expense")
    @PutMapping
    public ResponseEntity<ExpenseDto> update(@RequestBody @Valid ExpenseDto expenseDto) {
        ExpenseDto updatedExpense = this.expenseService.update(expenseDto);

        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }

    @Log(message = "Updating the status of an expense")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ExpenseDto> updateStatus(
            @PathVariable UUID id,
            @RequestBody @Valid StatusUpdateDto statusUpdate) {
        ExpenseDto updatedExpense = this.expenseService.updateStatus(id, statusUpdate.getStatus());

        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }

    @Log(message = "Deleting an expense")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.expenseService.softDelete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
