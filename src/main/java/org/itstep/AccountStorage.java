package org.itstep;

import java.io.*;
import java.util.ArrayList;

public class AccountStorage {
    private File file;

    public AccountStorage(File file) {
        this.file = file;
    }

    public Account create(String owner) throws IOException, ClassNotFoundException {
        Account account = new Account(getNewAccountNumber(), owner);
        ObjectOutputStream output = getOutput(true);
        output.writeObject(account);
        output.close();

        return account;
    }

    public Account read(Integer number) throws IOException, ClassNotFoundException {
        ObjectInputStream input = getInput();

        try {
            while (true) {
                Account account = (Account) input.readObject();

                if (account.getNumber().equals(number)) {
                    input.close();

                    return account;
                }
            }
        } catch (EOFException e) {
        }

        input.close();

        return null;
    }

    public ArrayList<Account> readAll() throws IOException, ClassNotFoundException {
        ArrayList<Account> list = new ArrayList<>();
        ObjectInputStream input = getInput();

        try {
            while (true) {
                list.add((Account) input.readObject());
            }
        } catch (EOFException e) {
        }

        input.close();

        return list;
    }

    public void update(Account account) throws IOException, ClassNotFoundException {
        ArrayList<Account> list = readAll();
        int index = list.lastIndexOf(account);

        if (index == -1) {
            throw new RuntimeException("Попытка обновления несуществующего счета");
        }

        list.set(index, account);

        ObjectOutputStream output = getOutput();

        for (Account acc : list) {
            output.writeObject(acc);
        }

        output.close();
    }

    private ObjectInputStream getInput() throws IOException {
        return new ObjectInputStream(new FileInputStream(file));
    }

    private ObjectOutputStream getOutput() throws IOException {
        return getOutput(false);
    }

    private ObjectOutputStream getOutput(boolean append) throws IOException {
        return isFileEmpty()
            ? new ObjectOutputStream(new FileOutputStream(file, append))
            : new AppendingObjectOutputStream(new FileOutputStream(file, append));
    }

    private Integer getNewAccountNumber() throws IOException, ClassNotFoundException {
        int number = 0;

        try {
            for (Account account : readAll()) {
                number = Math.max(number, account.getNumber());
            }
        } catch (EOFException e) {
        }

        return number + 1;
    }

    private boolean isFileEmpty() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        return br.readLine() != null;
    }
}
