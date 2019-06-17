package org.itstep;

import org.itstep.component.storage.AccountStorage;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        AccountStorage storage = new AccountStorage(getFile());
        Account account = storage.create("Keanu Reeves");
        Integer number = account.getNumber();

        account.setBalance(100500L);
        storage.update(account);

        account = storage.fetch(number);
        System.out.println(account);
    }

    public static File getFile() throws IOException {
        File file = new File(
            System.getProperty("user.dir")
                + File.separator + "data"
                + File.separator + "accounts.txt"
        );

        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }
}
