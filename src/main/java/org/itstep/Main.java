package org.itstep;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9000);
        System.out.println("Сервер запущен");

        while (true) {
            Socket client = server.accept();
            System.out.println("Принято соединение");
            Thread thread = new Thread(new ClientHandler(client));
            thread.start();
        }
    }

    public static File getAccountsFile() throws IOException {
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
