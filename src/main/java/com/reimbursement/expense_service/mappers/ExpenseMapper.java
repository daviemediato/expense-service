package com.reimbursement.expense_service.mappers;

import com.reimbursement.expense_service.dtos.ExpenseDto;
import com.reimbursement.expense_service.entities.ExpenseEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    ExpenseDto toDto(ExpenseEntity expenseEntity);
    ExpenseEntity toEntity(ExpenseDto expenseDto);
    List<ExpenseDto> toDtoList(List<ExpenseEntity> expenseEntities);
    List<ExpenseEntity> toEntityList(List<ExpenseDto> expenseDtos);
}
