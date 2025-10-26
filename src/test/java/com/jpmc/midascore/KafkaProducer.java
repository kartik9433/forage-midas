package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String transactionLine) {
        String[] data = transactionLine.split(", ");
        Transaction transaction = new Transaction(
                Long.parseLong(data[0]),
                Long.parseLong(data[1]),
                Float.parseFloat(data[2])
        );

        kafkaTemplate.send("transactions-topic", transaction);
    }
}
