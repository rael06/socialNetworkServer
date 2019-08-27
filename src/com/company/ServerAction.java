package com.company;

import Constants.Constants;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ServerAction {

    void action() {
        do {
            try (ServerSocket serverSocket = new ServerSocket(Constants.PORT)) {
                Socket socket = serverSocket.accept();
                Thread t = new Thread(new Service(socket));
                t.start();
                Thread.sleep(10000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }
}
