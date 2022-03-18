package com.will.bank.model;

import java.io.Serializable;
import java.util.List;

public class WillBank implements Serializable {

    private List<ClienteDto> customers;

    private String bank;

    public List<ClienteDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<ClienteDto> customers) {
        this.customers = customers;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
