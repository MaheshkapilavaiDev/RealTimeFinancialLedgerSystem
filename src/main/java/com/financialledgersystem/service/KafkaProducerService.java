package com.financialledgersystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.financialledgersystem.dto.TransactionEvent;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public void publish(TransactionEvent event) {

        kafkaTemplate.send("ledger-transactions", event);

        System.out.println("Published : " + event.getTransactionId());

    }

}
