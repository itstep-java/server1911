package org.itstep;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File file = new File(
            System.getProperty("user.dir") + File.separator + "accounts.dat"
        );
        AccountStorage accountStorage = new AccountStorage(file);

        Account account = accountStorage.create("John Smith");
        Integer number = account.getNumber();

        account.setBalance(10000L);
        account.setOwner("Aaron Smith");
        accountStorage.update(account);

        account = accountStorage.read(number);

        if (account != null) {
            System.out.println(account.getNumber());
            System.out.println(account.getBalance());
            System.out.println(account.getOwner());
        } else {
            System.out.println("Такой счет не существует");
        }
    }
}
