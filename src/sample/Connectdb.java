package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectdb {
    public static Connection getConnections()throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:sqlite:room1.db");
        return connection;
    }



}
