package com.company;

import DAO.ClubDAO;
import DAO.Database;
import DAO.PersonneDAO;
import DAO.SportDAO;
import ReseauSocial.Club;
import ReseauSocial.Personne;
import ReseauSocial.Sport;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Service implements Runnable {

    private Socket socket;
    private Connection conn;

    Service(Socket socket) {
        this.socket = socket;
        conn = Database.getInstance();
    }

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            HashMap<String, Object> request = (HashMap<String, Object>) ois.readObject();

            String commandType = (String) request.keySet().toArray()[0];
            Object commandObject = request.values().toArray()[0];


            // Personne crud
            if (commandObject instanceof Personne) {
                PersonneDAO personneDAO = new PersonneDAO(conn);
                Personne personne = (Personne) commandObject;
                if (commandType.equals("delete")) {
                    personneDAO.delete(personne);
                } else {
                    if (personne.getId() == 0) personneDAO.create(personne);
                    else personneDAO.update(personne);
                }
                oos.writeObject(true);

                // Sport crud
            } else if (commandObject instanceof Sport) {
                SportDAO sportDAO = new SportDAO(conn);
                Sport sport = (Sport) commandObject;
                if (commandType.equals("delete")) {
                    sportDAO.delete(sport);
                } else {
                    if (sport.getId() == 0) sportDAO.create(sport);
                    else sportDAO.update(sport);
                }
                oos.writeObject(true);

                // Club crud
            } else if (commandObject instanceof Club) {
                ClubDAO clubDAO = new ClubDAO(conn);
                Club club = (Club) commandObject;
                if (commandType.equals("delete")) {
                    clubDAO.delete(club);
                } else {
                    if (club.getId() == 0) clubDAO.create(club);
                    else clubDAO.update(club);
                }
                oos.writeObject(true);

                // sports' request
            } else if (commandObject.toString().equals("sports")) {
                SportDAO sportDAO = new SportDAO(conn);
                Map<String, Sport> sports = new LinkedHashMap<>();
                ResultSet rs = sportDAO.selectAll();
                int sportId;
                String sportName;
                while (rs.next()) {
                    sportId = rs.getInt("id");
                    sportName = rs.getString("name");
                    sports.put(sportName, new Sport(sportId, sportName));
                }
                oos.writeObject(sports);

                // clubs' request
            } else if (commandObject.toString().equals("clubs")) {
                ClubDAO clubDAO = new ClubDAO(conn);
                Map<String, Club> clubs = new LinkedHashMap<>();
                ResultSet rs = clubDAO.selectAll();
                int clubId;
                String clubName;
                while (rs.next()) {
                    clubId = rs.getInt("id");
                    clubName = rs.getString("name");
                    clubs.put(clubName, new Club(clubId, clubName));
                }
                oos.writeObject(clubs);

                // members' request
            } else if (commandObject.toString().equals("personnes")) {
                PersonneDAO personneDAO = new PersonneDAO(conn);
                Map<String, Personne> personnes = new LinkedHashMap<>();
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
                        memberSports = rs.getString("sports").split(",");
                    if (rs.getString("clubs") != null)
                        memberClubs = rs.getString("clubs").split(",");
                    Personne personne = new Personne(memberId, memberName, memberFirstName, memberAge);
                    for (String sportString : memberSports) {
                        personne.setSport(new Sport(sportString));
                    }
                    for (String clubString : memberClubs) {
                        personne.setClub(new Club(clubString));
                    }
                    personnes.put(memberName, personne);
                }

                // pour tester le multi-threading
//                Thread.sleep(3000);
                // !
                oos.writeObject(personnes);
            }
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
