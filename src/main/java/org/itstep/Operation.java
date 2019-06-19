package org.itstep;

import java.util.GregorianCalendar;

public class Operation {
    private String accountNumber;
    private Long amount;
    private OperationType type;
    private GregorianCalendar date;
    private String recipientNumber = null;
    private Integer operationnumber;

    public Operation(String accountNumber, Long amount, OperationType type, Integer opernum) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.type = type;
        this.date = new GregorianCalendar();
        this.operationnumber = opernum;
    }

    public Operation(String accountNumber, Long amount, OperationType type, String recipientNumber, Integer opernum) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.type = type;
        this.date = new GregorianCalendar();
        this.recipientNumber = recipientNumber;
        this.operationnumber = opernum;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Long getAmount() {
        return amount;
    }

    public OperationType getType() {
        return type;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public String getRecipientNumber() {
        return recipientNumber;
    }

    public Integer getOperationnumber() {
        return operationnumber;
    }
}
