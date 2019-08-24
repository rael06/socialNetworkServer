package com.company;

import Constants.Constants;
import ReseauSocial.Personne;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(Constants.PORT)) {
            do {
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Object o = ois.readObject();
                if (o instanceof Personne) {
                    Personne personne = (Personne) o;
                    personne.afficher();
                }
            } while (true);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
