package com.financialledgersystem.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financialledgersystem.entity.AuditLog;
import com.financialledgersystem.repository.AuditLogRepository;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository repository;

    public void saveLog(String username,
                        String operation,
                        String status,
                        String description) {

        AuditLog log = new AuditLog();

        log.setUsername(username);
        log.setOperation(operation);
        log.setStatus(status);
        log.setDescription(description);
        log.setTimestamp(LocalDateTime.now());

        repository.save(log);

    }

}
