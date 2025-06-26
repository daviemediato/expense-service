package com.reimbursement.expense_service.utils.aspects;

import com.reimbursement.expense_service.utils.annotations.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("@annotation(log)")
    public void logBefore(JoinPoint joinPoint, Log log) {
        String message = log.message();
        String methodName = joinPoint.getSignature().getName();

        logger.info("[LOG] Before method: {} | Message: {}", methodName, message);
    }
}
