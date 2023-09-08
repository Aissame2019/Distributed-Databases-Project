package application.Connection;

import java.sql.Connection;

import java.io.FileInputStream;

import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnectionCasa {
    private Connection con;
    private static DbConnectionCasa dbc;
    
    private DbConnectionCasa() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            FileInputStream fis=new FileInputStream("src\\application\\Connection\\connectionCasa.prop");
            Properties p=new Properties (); 
            p.load(fis); 
            con=DriverManager.getConnection((String)p.get("url"),(String)p.get("username"),(String)p.get("password"));
        } catch (Exception ex) {
            Logger.getLogger(DbConnectionCasa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static DbConnectionCasa getDatabaseConnection() {
        if (dbc == null) {
            dbc = new DbConnectionCasa();
        }
        return dbc;
    }
    
    public Connection getConnection() {
        return con;
    }
    
    public static void main(String[] args) {
        new DbConnectionCasa();
    }
}