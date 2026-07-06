package com.financialledgersystem.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.financialledgersystem.dto.TransactionEvent;

@Service
public class KafkaConsumerService {

    @KafkaListener(
            topics = "ledger-transactions",
            groupId = "ledger-group")
    public void consume(TransactionEvent event) {

        System.out.println("Received Transaction");

        System.out.println(event);

    }

}
