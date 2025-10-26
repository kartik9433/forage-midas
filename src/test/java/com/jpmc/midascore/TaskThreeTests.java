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

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class TaskThreeTests {
    static final Logger logger = LoggerFactory.getLogger(TaskThreeTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private UserRepository userRepository;

    @Test
    void task_three_verifier() throws InterruptedException {
        userPopulator.populate();
        String[] transactionLines = fileLoader.loadStrings("/test_data/mnbvcxz.vbnm");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }
        Thread.sleep(3000); // Wait for all transactions to process

        // GET WALDORF'S BALANCE
        UserRecord waldorf = userRepository.findByUsername("waldorf").orElseThrow();
        float exactBalance = waldorf.getBalance();
        int waldorfBalance = (int) exactBalance; // Cast to int rounds down

        logger.info("----------------------------------------------------------");
        logger.info("==========================================");
        logger.info("*** WALDORF EXACT BALANCE: " + exactBalance + " ***");
        logger.info("*** WALDORF ROUNDED BALANCE (ANSWER): " + waldorfBalance + " ***");
        logger.info("==========================================");
        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("use your debugger to find out what waldorf's balance is after all transactions are processed");
        logger.info("kill this test once you find the answer");

        // Print all user balances for verification
        logger.info("\n=== ALL USER BALANCES ===");
        userRepository.findAll().forEach(user ->
                logger.info(user.getUsername() + ": $" + user.getBalance())
        );
        logger.info("=========================\n");

        while (true) {
            Thread.sleep(20000);
            logger.info("...");
        }
    }
}