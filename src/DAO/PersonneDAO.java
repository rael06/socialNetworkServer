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

    public ResultSet findById(int id) {
        String query = "SELECT id, name, firstName, age FROM members " +
                "WHERE id = " + id;
        try {
            return conn.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Personne personne) {
        System.out.println("Membre déjà inscrit !");
        int id = personne.getId();
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
            String addMember = "UPDATE members " +
                    "SET name = '" + name + "', " +
                    "firstName = '" + firstname + "', " +
                    "age = " + age + " " +
                    "WHERE id = " + id;
            conn.createStatement().executeUpdate(addMember);

            String deleteMemberSports = "DELETE FROM members_sports " +
                    "WHERE idMember = " + id;
            conn.createStatement().executeUpdate(deleteMemberSports);

            for (Map.Entry<String, Sport> sport : sports.entrySet()) {
                sportRS = sportDAO.findByName(sport.getValue().getNom());
                idSport = sportRS.next() ? sportRS.getInt("id") : 0;
                String addMemberSports = "INSERT INTO members_sports " +
                        "(idMember, idSport) " +
                        "VALUES (" + id + ", " + idSport + ")";
                conn.createStatement().executeUpdate(addMemberSports);
            }

            String deleteMemberClubs = "DELETE FROM members_clubs " +
                    "WHERE idMember = " + id;
            conn.createStatement().executeUpdate(deleteMemberClubs);

            for (Map.Entry<String, Club> club : clubs.entrySet()) {
                clubRS = clubDAO.findByName(club.getValue().getNom());
                idClub = clubRS.next() ? clubRS.getInt("id") : 0;
                String addMemberSports = "INSERT INTO members_clubs " +
                        "(idMember, idClub) " +
                        "VALUES (" + id + ", " + idClub + ")";
                conn.createStatement().executeUpdate(addMemberSports);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet selectAll() {
        String query = "SELECT members.id, members.name, firstName, age, " +
                "GROUP_CONCAT(sports.name SEPARATOR ' ') AS sports, " +
                "GROUP_CONCAT(clubs.name SEPARATOR ' ') AS clubs " +
                "FROM members " +
                "LEFT OUTER JOIN members_sports " +
                "ON members.id = members_sports.idMember " +
                "LEFT OUTER JOIN sports " +
                "ON sports.id = members_sports.idSport " +
                "LEFT OUTER JOIN members_clubs " +
                "ON members.id = members_clubs.idMember " +
                "LEFT OUTER JOIN clubs " +
                "ON clubs.id = members_clubs.idClub " +
                "GROUP BY members.id " +
                "ORDER BY members.name, firstName";
        try {
            return conn.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Personne personne) {
        int id = personne.getId();
        String query = "DELETE FROM members WHERE id = " + id;
        try {
            conn.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
