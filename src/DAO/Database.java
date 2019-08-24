package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static Connection getConn() {
        return conn;
    }

    private static Connection conn;
    private String password = "";
    private String userName = "root";
    private String databaseName = "socialnetwork";
    private String url = "jdbc:mysql://localhost:3306/" + this.databaseName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private Database () {
        try {
            Database.conn = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getInstance () {
        if (Database.conn == null) {
            new Database();
        }
        return Database.conn;
    }
}
