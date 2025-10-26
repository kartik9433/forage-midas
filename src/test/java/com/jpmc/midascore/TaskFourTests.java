package com.jpmc.midascore;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class TaskFourTests {
    static final Logger logger = LoggerFactory.getLogger(TaskFourTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private UserRepository userRepository;

    @Test
    void task_four_verifier() throws InterruptedException {
        // 1️⃣ Populate users
        userPopulator.populate();

        // 2️⃣ Load transactions from test file
        String[] transactionLines = fileLoader.loadStrings("/test_data/alskdjfh.fhdjsk");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }

        // 3️⃣ Wait for KafkaConsumer to process transactions
        Thread.sleep(3000); // increase if you have many transactions

        // 4️⃣ Fetch wilbur's final balance
        Optional<UserRecord> wilbur = userRepository.findByUsername("wilbur");
        if (wilbur.isPresent()) {
            int finalBalance = (int) Math.floor(wilbur.get().getBalance());
            System.out.println("Wilbur final balance (rounded down): " + finalBalance);
            logger.info("Wilbur final balance (rounded down): {}", finalBalance);
        } else {
            logger.warn("User 'wilbur' not found!");
        }
    }
}
