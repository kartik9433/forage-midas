package com.jpmc.midascore.controller;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final UserRepository userRepository;

    @Autowired
    public BalanceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Balance getBalance(@RequestParam("userId") Long userId) {
        UserRecord user = userRepository.findById(userId).orElse(null);
        float balance = (user != null) ? user.getBalance() : 0f;
        return new Balance(userId, balance);
    }
}
