package com.company;

import Constants.Constants;
import DAO.Database;
import DAO.PersonneDAO;
import DAO.SportDAO;
import ReseauSocial.Personne;
import ReseauSocial.Sport;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
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
                    ResultSet rs = personneDAO.findByNameFirstName(personne.getNom(), personne.getPrenom());
                    rs.last();
                    int size = rs.getRow();
                    if (size == 0) personneDAO.create(personne);
                    else personneDAO.update(personne);

                } else if (o instanceof Sport) {
                    SportDAO sportDAO = new SportDAO(conn);
                    Sport sport = (Sport) o;
                    ResultSet rs = sportDAO.findByName(sport.getNom());
                    rs.last();
                    int size = rs.getRow();
                    if (size == 0) sportDAO.create(sport);
                    else sportDAO.update(sport);
                }
            } while (true);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
