package DAO;

import ReseauSocial.Sport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SportDAO {
    private Connection conn;

    public SportDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(Sport sport) {
        String name = sport.getNom();

        String query = "INSERT INTO sports (name) VALUES ('" + name + "')";
        try {
            conn.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ResultSet findByName (String name) {

        String query = "SELECT id, name FROM sports WHERE LOWER(name) = '" + name.toLowerCase() + "'";
        try {
            return conn.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Sport sport) {

        int id = sport.getId();
        String name = sport.getNom();

        String query = "UPDATE sports SET name = '" + name + "' " +
                "WHERE id = " + id;
        try {
            conn.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet selectAll() {
        String query = "SELECT id, name FROM sports ORDER BY name";
        try {
            return conn.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Sport sport) {
        int id = sport.getId();
        String query = "DELETE FROM sports WHERE id = " + id;
        try {
            conn.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
