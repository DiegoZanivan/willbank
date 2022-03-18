package com.will.bank.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaldoDto implements Serializable {
    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
