package org.itstep.component.storage;

import org.itstep.Account;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class AccountStorage extends AbstractObjectStorage<Account> {
    private static final String locker = "locker";

    public AccountStorage(File file) {
        super(file);
    }

    public Account create(String owner) throws IOException, ClassNotFoundException {
        synchronized (locker) {
            Account account = new Account(getNextNumber(), owner);
            write(account, true);

            return account;
        }
    }

    public Account fetch(Integer number) throws IOException, ClassNotFoundException {
        synchronized (locker) {
//            Predicate<Account> predicate = new Predicate<Account>() {
//                @Override
//                public boolean test(Account account) {
//                    return account.getNumber().equals(number);
//                }
//            };
//
//            return read(predicate);
            return read(account -> account.getNumber().equals(number));
        }
    }

    public ArrayList<Account> fetchAll() throws IOException, ClassNotFoundException {
        synchronized (locker) {
            return readAll();
        }
    }

    public void update(Account account) throws IOException, ClassNotFoundException {
        synchronized (locker) {
            ArrayList<Account> accounts = readAll();
            int index = accounts.lastIndexOf(account);

            if (-1 == index) {
                throw new RuntimeException("Попытка обновления несуществующего счета");
            }

            accounts.set(index, account);
            write(accounts);
        }
    }

    private Integer getNextNumber() throws IOException, ClassNotFoundException {
        int number = 0;

        for (Account account : readAll()) {
            number = Math.max(number, account.getNumber());
        }

        return number + 1;
    }
}
