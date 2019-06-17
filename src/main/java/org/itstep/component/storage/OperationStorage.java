package org.itstep.component.storage;

import org.itstep.Operation;
import org.itstep.OperationType;

import java.io.File;
import java.io.IOException;

public class OperationStorage extends AbstractObjectStorage {

    OperationStorage(File file) {
        super(file);
    }

    public Operation create(String accountNumber, Long amount, OperationType type) throws IOException {
        Operation operation = new Operation(accountNumber, amount, type);
        write(operation, true);
        return operation;
    }

    public Operation create(String accountNumber, Long amount, OperationType type, String recipientNumber) throws IOException {
        Operation operation = new Operation(accountNumber, amount, type, recipientNumber);
        write(operation, true);
        return operation;
    }
}
