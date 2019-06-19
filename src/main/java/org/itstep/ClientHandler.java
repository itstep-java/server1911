package org.itstep;

import org.itstep.component.storage.AccountStorage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket client;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try (
            Scanner scanner = new Scanner(client.getInputStream());
            PrintWriter writer = new PrintWriter(client.getOutputStream())
        ) {
            Integer accountNumber = Integer.parseInt(scanner.nextLine());
            System.out.println("Счет: " + accountNumber);

            AccountStorage accountStorage = new AccountStorage(
                Main.getAccountsFile()
            );

            if (accountNumber.equals(0)) {
                String owner = scanner.nextLine();
                System.out.println("Имя владельца: " + owner);
                Account account = accountStorage.create(owner);
                writer.println(account.getNumber());
                System.out.println("Создан счет: " + account);
            } else {
                // TODO
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            client.close();
            System.out.println("Соединение завершено");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
