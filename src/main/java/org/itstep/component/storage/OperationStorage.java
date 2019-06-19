package org.itstep.component.storage;

import org.itstep.Account;
import org.itstep.Operation;
import org.itstep.OperationType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OperationStorage extends AbstractObjectStorage<Operation> {
    private static final String locker = "locker";

    OperationStorage(File file) {
        super(file);
    }

    public Operation create1accincl(
            String frstaccnum,
            Long amount,
            OperationType type
    ) throws IOException, ClassNotFoundException {
        synchronized (locker) {
            Operation operation = new Operation(frstaccnum, amount, type, getNextNumber());
            write(operation, true);

            return operation;
        }
    }
    public Operation create2accincl(
            String frstaccnum,
            Long amount,
            OperationType type,
            String scndaccnum
    ) throws IOException, ClassNotFoundException {
        synchronized (locker) {
            Operation operation = new Operation(frstaccnum, amount, type, scndaccnum, getNextNumber());
            write(operation, true);

            return operation;
        }
    }


    public Operation fetchacc(Integer number) throws IOException, ClassNotFoundException {
        synchronized (locker) {
            return read(operation -> operation.getAccountNumber().equals(number));
        }
    }

    public Operation fetchnum(Integer number) throws IOException, ClassNotFoundException {
        synchronized (locker) {
            return read(operation -> operation.getOperationnumber().equals(number));
        }
    }

    public ArrayList<Operation> fetchAll() throws IOException, ClassNotFoundException {
        synchronized (locker) {
            return readAll();
        }
    }



    private Integer getNextNumber() throws IOException, ClassNotFoundException {
        int number = 0;

        for (Operation operation : readAll()) {
            number = Math.max(number, operation.getOperationnumber());
        }

        return number + 1;
    }
}
