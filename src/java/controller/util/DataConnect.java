package controller.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataConnect {

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@172.16.50.205:1521:DB11G", "SMSBIRD", "SMSBIRD");

            /*System.out.println("controller.util.DataConnect.getConnection()");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "sys as sysdba", "Wael123");
             */
            return con;
        } catch (Exception ex) {
            System.out.println("Database.getConnection() Error -->"
                    + ex.getMessage());
            return null;
        }
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }
}
