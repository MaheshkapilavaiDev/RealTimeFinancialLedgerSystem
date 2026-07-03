package com.financialledgersystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialledgersystem.entity.AuditLog;
import com.financialledgersystem.repository.AuditLogRepository;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    @Autowired
    private AuditLogRepository repository;

    @GetMapping
    public List<AuditLog> getLogs() {

        return repository.findAll();

    }

    @GetMapping("/{username}")
    public List<AuditLog> getLogsByUser(
            @PathVariable String username){

        return repository.findByUsername(username);

    }

}
