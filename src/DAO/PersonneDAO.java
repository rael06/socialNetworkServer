package DAO;

import ReseauSocial.Club;
import ReseauSocial.Personne;
import ReseauSocial.Sport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PersonneDAO {
    private Connection conn;
    SportDAO sportDAO;
    ClubDAO clubDAO;


    public PersonneDAO(Connection conn) {
        this.conn = conn;
        sportDAO = new SportDAO(conn);
        clubDAO = new ClubDAO(conn);
    }

    public void create(Personne personne) {
        String name = personne.getNom();
        String firstname = personne.getPrenom();
        int age = personne.getAge();
        HashMap<String, Sport> sports = personne.getSports();
        HashMap<String, Club> clubs = personne.getClubs();
        ResultSet memberRS;
        int idMember;
        ResultSet sportRS;
        int idSport;
        ResultSet clubRS;
        int idClub;

        try {
            String addMember = "INSERT INTO members (name, firstName, age) VALUES ('" + name + "','" + firstname + "'," + age + ")";
            conn.createStatement().executeUpdate(addMember);

            memberRS = findByNameFirstName(name, firstname);
            idMember = memberRS.next() ? memberRS.getInt("id") : 0;

            for (Map.Entry<String, Sport> sport : sports.entrySet()) {
                sportRS = sportDAO.findByName(sport.getValue().getNom());
                idSport = sportRS.next() ? sportRS.getInt("id") : 0;
                String addMemberSports = "INSERT INTO members_sports (idMember, idSport) VALUES (" + idMember + ", " + idSport + ")";
                conn.createStatement().executeUpdate(addMemberSports);
            }

            for (Map.Entry<String, Club> club : clubs.entrySet()) {
                clubRS = clubDAO.findByName(club.getValue().getNom());
                idClub = clubRS.next() ? clubRS.getInt("id") : 0;
                String addMemberSports = "INSERT INTO members_clubs (idMember, idClub) VALUES (" + idMember + ", " + idClub + ")";
                conn.createStatement().executeUpdate(addMemberSports);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet findByNameFirstName(String name, String firstName) {
        String query = "SELECT id, name, firstName, age FROM members " +
                "WHERE LOWER(name) = '" + name.toLowerCase() + "' AND LOWER(firstName) = '" +
                firstName.toLowerCase() + "'";
        try {
            return conn.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Personne personne) {
        System.out.println("Membre déjà inscrit !");
    }

    public ResultSet selectAll() {
        String query = "SELECT id, name, firstName, age FROM members";
        try {
            return conn.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
