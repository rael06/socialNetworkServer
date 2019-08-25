package DAO;

import ReseauSocial.Personne;
import ReseauSocial.Sport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class PersonneDAO {
    private Connection conn;

    public PersonneDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(Personne personne) {
        String name = personne.getNom();
        String firstname = personne.getPrenom();
        int age = personne.getAge();
        HashMap<String, Sport> sports = personne.getSports();

        String addMember = "INSERT INTO members (name, firstName, age) VALUES ('" + name + "','" + firstname + "'," + age + ")";
        try {
            conn.createStatement().executeUpdate(addMember);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet findByNameFirstName (String name, String firstName) {
        String query = "SELECT id, name, firstName, age FROM members " +
                "WHERE name = '" + name.toUpperCase() + "' AND firstName = '" +
                (firstName.charAt(0) + "").toUpperCase() + firstName.substring(1) + "'";
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
}
