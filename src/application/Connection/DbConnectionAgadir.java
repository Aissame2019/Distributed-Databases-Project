package application.Connection;

import java.sql.Connection;

import java.io.FileInputStream;

import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DbConnectionAgadir {
    private Connection con;
    private static DbConnectionAgadir dbc;
     
    private DbConnectionAgadir() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            FileInputStream fis=new FileInputStream("src\\application\\Connection\\connectionAgadir.prop");
            Properties p=new Properties (); 
            p.load(fis); 
            con=DriverManager.getConnection((String)p.get("url"),(String)p.get("username"),(String)p.get("password"));
        } catch (Exception ex) {
            Logger.getLogger(DbConnectionAgadir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static DbConnectionAgadir getDatabaseConnection() {
        if (dbc == null) {
            dbc = new DbConnectionAgadir();
        }
        return dbc;
    }
    
    public Connection getConnection() {
        return con;
    }
    
    public static void main(String[] args) {
        new DbConnectionAgadir();
    }
}