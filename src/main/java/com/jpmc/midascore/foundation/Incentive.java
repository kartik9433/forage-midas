package com.jpmc.midascore.foundation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Ignore any extra fields from API response
@JsonIgnoreProperties(ignoreUnknown = true)
public class Incentive {

    private float amount;

    public Incentive() {
    }

    public Incentive(float amount) {
        this.amount = amount;
    }

    // Getter
    public float getAmount() {
        return amount;
    }

    // Setter
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Incentive{" +
                "amount=" + amount +
                '}';
    }
}
