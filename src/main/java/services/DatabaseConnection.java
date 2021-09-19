package services;

import core.ReadProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {

    private static Connection con = null;
    private static ReadProperties prop = new ReadProperties();

    static {
        String url = prop.getDbUrl();
        String username = prop.getDbUsername();
        String psw = prop.getDbPsw();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, psw);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return con;
    }

    public static void closeConnection() {
        try {
            con.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
