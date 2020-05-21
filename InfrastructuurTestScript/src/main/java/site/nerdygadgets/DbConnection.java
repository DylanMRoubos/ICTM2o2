package site.nerdygadgets;

import java.sql.*;
import java.time.Instant;


/**
 * DbConnection class
 * Connection class to connect to datanode
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 12-05-2020
 */
public class DbConnection {
    Connection conn;
    Statement stmt;
    int id;
    String testTable = "ProcedureTest";

    public DbConnection(int id, String ip, String user, String password, String database) {
        this.id = id;
        try {
            String fullURL = String.format("jdbc:mysql://%s/%s", ip, database);

            conn = DriverManager.getConnection(fullURL, user, password);
            System.out.println("Connection established");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLastRowFromTestTable() {
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM ProcedureTest LIMIT 1";
            ResultSet rs = stmt.executeQuery(sql);

            rs.next();
            int insertedBy = rs.getInt("InsertedBy");
            String time = rs.getString("time");
            rs.close();
            stmt.close();
            return String.format("Inserted by: %d, time of insertion: %s", insertedBy, time);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean updateTestTable() {
        try {
            stmt = conn.createStatement();
            long time = Instant.now().getEpochSecond();
            String sql = String.format("UPDATE ProcedureTest SET InsertedBy = %d, time = '%s' WHERE 1=1", id, time);
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
