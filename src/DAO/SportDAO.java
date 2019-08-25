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

        String addSport = "INSERT INTO sports (name) VALUES ('" + name + "')";
        try {
            conn.createStatement().executeUpdate(addSport);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet findByName (String name) {
        String query = "SELECT id, name FROM sports WHERE LOWER(name) = '" + name.toLowerCase() + "'";
        try {
            return conn.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Sport sport) {
        System.out.println("Le sport existe déjà !");
    }

    public ResultSet selectAll() {
        String query = "SELECT id, name FROM sports";
        try {
            return conn.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
