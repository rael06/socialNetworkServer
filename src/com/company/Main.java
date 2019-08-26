package com.company;

import Constants.Constants;
import DAO.ClubDAO;
import DAO.Database;
import DAO.PersonneDAO;
import DAO.SportDAO;
import ReseauSocial.Club;
import ReseauSocial.Personne;
import ReseauSocial.Sport;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Connection conn = Database.getInstance();

        try (ServerSocket serverSocket = new ServerSocket(Constants.PORT)) {
            do {
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                HashMap<String, Object> request = (HashMap<String, Object>) ois.readObject();

                String commandType = (String) request.keySet().toArray()[0];
                Object commandObject = request.values().toArray()[0];

                // Personne creation or update
                if (commandObject instanceof Personne) {
                    PersonneDAO personneDAO = new PersonneDAO(conn);
                    Personne personne = (Personne) commandObject;
                    if (commandType.equals("delete")) {
                        personneDAO.delete(personne);
                    } else {
                        if (personne.getId() == 0) personneDAO.create(personne);
                        else personneDAO.update(personne);
                    }
                    oos.writeObject(null);

                    // Sport creation
                } else if (commandObject instanceof Sport) {
                    SportDAO sportDAO = new SportDAO(conn);
                    Sport sport = (Sport) commandObject;
                    ResultSet rs = sportDAO.findByName(sport.getNom());
                    rs.last();
                    int size = rs.getRow();
                    if (size == 0) sportDAO.create(sport);
                    else sportDAO.update(sport);
                    oos.writeObject(null);

                    // Club creation
                } else if (commandObject instanceof Club) {
                    ClubDAO clubDAO = new ClubDAO(conn);
                    Club club = (Club) commandObject;
                    ResultSet rs = clubDAO.findByName(club.getNom());
                    rs.last();
                    int size = rs.getRow();
                    if (size == 0) clubDAO.create(club);
                    else clubDAO.update(club);
                    oos.writeObject(null);

                    // sports' request
                } else if (commandObject.toString().equals("sports")) {
                    SportDAO sportDAO = new SportDAO(conn);
                    Map<String, Sport> sports = new HashMap<>();
                    ResultSet rs = sportDAO.selectAll();
                    String sportName;
                    while (rs.next()) {
                        sportName = rs.getString("name");
                        sports.put(sportName, new Sport(sportName));
                    }
                    oos.writeObject(sports);

                    // clubs' request
                } else if (commandObject.toString().equals("clubs")) {
                    ClubDAO clubDAO = new ClubDAO(conn);
                    Map<String, Club> clubs = new HashMap<>();
                    ResultSet rs = clubDAO.selectAll();
                    String clubName;
                    while (rs.next()) {
                        clubName = rs.getString("name");
                        clubs.put(clubName, new Club(clubName));
                    }
                    oos.writeObject(clubs);

                    // members' request
                } else if (commandObject.toString().equals("personnes")) {
                    PersonneDAO personneDAO = new PersonneDAO(conn);
                    Map<String, Personne> personnes = new HashMap<>();
                    ResultSet rs = personneDAO.selectAll();
                    int memberId;
                    String memberName;
                    String memberFirstName;
                    int memberAge;
                    String[] memberSports = new String[]{};
                    String[] memberClubs = new String[]{};

                    while (rs.next()) {
                        memberId = rs.getInt("id");
                        memberName = rs.getString("name");
                        memberFirstName = rs.getString("firstName");
                        memberAge = rs.getInt("age");
                        if (rs.getString("sports") != null)
                            memberSports = rs.getString("sports").split(" ");
                        if (rs.getString("clubs") != null)
                            memberClubs = rs.getString("clubs").split(" ");
                        Personne personne = new Personne(memberId, memberName, memberFirstName, memberAge);
                        for (String sportString : memberSports) {
                            personne.setSport(new Sport(sportString));
                        }
                        for (String clubString : memberClubs) {
                            personne.setClub(new Club(clubString));
                        }
                        personnes.put(memberName, personne);
                    }
                    oos.writeObject(personnes);

                }
                oos.close();
            } while (true);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
