package com.financialledgersystem.audit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.financialledgersystem.service.AuditLogService;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditLogService auditService;

    @AfterReturning("@annotation(audit)")
    public void success(JoinPoint joinPoint, Audit audit) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication != null
                ? authentication.getName()
                : "SYSTEM";

        auditService.saveLog(
                username,
                audit.operation(),
                "SUCCESS",
                joinPoint.getSignature().getName());

    }

    @AfterThrowing(
            pointcut = "@annotation(audit)",
            throwing = "exception")
    public void failure(JoinPoint joinPoint,
                        Audit audit,
                        Exception exception) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication != null
                ? authentication.getName()
                : "SYSTEM";

        auditService.saveLog(
                username,
                audit.operation(),
                "FAILED",
                exception.getMessage());

    }

}