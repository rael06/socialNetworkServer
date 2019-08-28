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

        String query = "INSERT INTO clubs (name) VALUES ('" + name + "')";
        try {
            conn.createStatement().executeUpdate(query);
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
        int id = club.getId();
        String name = club.getNom();

        String query = "UPDATE clubs SET name = '" + name + "' " +
                "WHERE id = " + id;
        try {
            conn.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet selectAll() {
        String query = "SELECT id, name FROM clubs ORDER BY name";
        try {
            return conn.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Club club) {
        int id = club.getId();
        String query = "DELETE FROM clubs WHERE id = " + id;
        try {
            conn.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

