package com.oxahex.wifinearme.db;

import java.sql.*;

public class DBManager {
    private static final String JDBC_DRIVER = "org.sqlite.JDBC";
    private static final String DB_FILE_PATH = "jdbc:sqlite::resource:wifinearme.db";

    /**
     * Driver 로드
     * */
    public DBManager() {}

    /**
     * Connection 객체 생성 및 반환
     * */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_FILE_PATH);
            // TODO: 로그를 남기는 부분 공부 후 추가 여부 판단
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("DBManager.getConnection(): " + e.getMessage());
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * Connection Close
     * */
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("DBManager.closeConnection(connection): " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Statement Close
     * */
    public static void closeConnection(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            System.out.println("DBManager.closeConnection(preparedStatement): " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * ResultSet Close
     * */
    public static void closeConnection(ResultSet resultSet) {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println("DBManager.closeConnection(resultSet): " + e.getMessage());
            e.printStackTrace();
        }
    }
}
