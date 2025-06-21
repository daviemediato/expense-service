package com.reimbursement.expense_service.repositories;

import com.reimbursement.expense_service.entities.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, UUID> {
    // Additional query methods can be defined here if needed

    // For example, to find expenses by user ID or status

    // List<ExpenseEntity> findByUserId(UUID userId);
    // List<ExpenseEntity> findByStatus(String status);
}
