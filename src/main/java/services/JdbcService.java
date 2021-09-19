package services;

//import org.apache.log4j.Logger;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class JdbcService {

    static final String DB_URL = "jdbc:mysql://sql11.freesqldatabase.com:3306/sql11420246";
    static final String USERNAME = "sql11420246";
    static final String PSW = "ehUZ4qKJsC";

    Connection connection = null;
    Statement statement;

    public JdbcService() {
        log.info("Setup connection to MySQL DB.");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("MySQL JDBC Driver is not found");
            log.error(e.getMessage());
            return;
        }

        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PSW);
        } catch (SQLException e) {
            log.error("Connection is failed");
            log.error(e.getMessage());
            return;
        }

        if (connection != null) {
            log.info("You successfully connected to db");
        } else {
            log.info("Failed to connect to db");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
            log.info("Connection closed successfully");
        } catch (SQLException throwable) {
            log.error("Can not close connection");
            log.error(throwable.getMessage());
        }
    }

    public Statement getStatement() {
        try {
            if (statement == null) {
                statement = connection.createStatement();
            }
        } catch (SQLException ex) {

            log.error("Statement creation failed");
            log.error(ex.getMessage());
        }
        return statement;
    }

    public void closeStatement() {
        try {
            statement.close();
        } catch (SQLException exception) {
            log.error("Statement can not be closed");
            log.error(exception.getMessage());
        }
    }

    public void executeSQL(String sql) {
        try {
            getStatement().execute(sql);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    public ResultSet executeQuery(String sql) {
        ResultSet resultSet = null;
        try {
            resultSet = getStatement().executeQuery(sql);
            log.info("SQL: " + sql + " has been executed");
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }
        return resultSet;
    }
}
