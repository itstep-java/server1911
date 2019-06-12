package org.itstep;

import java.io.Serializable;

public class Account implements Serializable {
    private Integer number;

    private Long balance = 0L;

    private String owner;

    public Account(Integer number, String owner) {
        this.number = number;
        this.owner = owner;
    }

    public Integer getNumber() {
        return number;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Account && ((Account) obj).number.equals(number);
    }
}
