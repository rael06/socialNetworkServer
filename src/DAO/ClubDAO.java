package DAO;

import ReseauSocial.Club;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClubDAO {
    private Connection conn;

    public ClubDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(Club club) {
        String name = club.getNom();

        String addClub = "INSERT INTO clubs (name) VALUES ('" + name + "')";
        try {
            conn.createStatement().executeUpdate(addClub);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet findByName (String name) {
        String query = "SELECT id, name FROM clubs WHERE LOWER(name) = '" + name.toLowerCase() + "'";
        try {
            return conn.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Club club) {
        System.out.println("Le club existe déjà !");
    }

    public ResultSet selectAll() {
        String query = "SELECT id, name FROM clubs";
        try {
            return conn.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

