package org.itstep.component.storage;

import org.itstep.Operation;
import org.itstep.OperationType;

import java.io.File;
import java.io.IOException;

public class OperationStorage extends AbstractObjectStorage<Operation> {

    private static final String locker = "locker";

    public OperationStorage(File file) {
        super(file);
    }

    public Operation create(String accountNumber, Long amount, OperationType type, String recipientNumber) throws IOException {
        Operation operation = new Operation(accountNumber, amount, type);
        write(operation, true);

        return operation;
    }

    public Operation create(String accountNumber, Long amount, OperationType type) throws IOException {
        return create(accountNumber, amount, type, null);
    }

    public Operation fetch (Integer num) throws IOException, ClassNotFoundException {
            synchronized (locker) {

                return read(operation -> Integer.parseInt(operation.getAccountNumber()) == num);

            }
    }
}
