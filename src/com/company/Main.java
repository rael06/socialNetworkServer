package com.company;

import Constants.Constants;
import DAO.Database;
import DAO.PersonneDAO;
import ReseauSocial.Personne;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        Connection conn = Database.getInstance();

        try (ServerSocket serverSocket = new ServerSocket(Constants.PORT)) {
            do {
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Object o = ois.readObject();
                if (o instanceof Personne) {
                    PersonneDAO personneDAO = new PersonneDAO(conn);
                    Personne personne = (Personne) o;
                    personne.afficher();
                    if (personneDAO.findByNameFirstName(personne.getNom(), personne.getPrenom()) == null) personneDAO.create(personne);
                    else personneDAO.update(personne);
                }
            } while (true);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
