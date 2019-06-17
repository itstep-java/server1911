package org.itstep.component.storage;

import org.itstep.Operation;
import org.itstep.OperationType;

import java.io.File;
import java.io.IOException;

public class OperationStorage extends AbstractObjectStorage {

    OperationStorage(File file) {
        super(file);
    }

    public Operation create(String accountNumber, Long amount, OperationType type)
            throws IOException {
        synchronized (OperationStorage.class) {
            Operation operation = create(accountNumber, amount, type, null);
            write(operation, true);
            return operation;
        }
    }

    public Operation create(String accountNumber, Long amount, OperationType type, String recipientNumber)
            throws IOException {
        synchronized (OperationStorage.class) {
            Operation operation = new Operation(accountNumber, amount, type, recipientNumber);
            write(operation, true);
            return operation;
        }
    }
}
