package application.Connection;

import java.sql.Connection;
import java.io.FileInputStream;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.models.Client;

public class DbConnectionLogin {
    private Connection con;
    private static DbConnectionLogin dbc;
    public static Client currentClient; 
    private DbConnectionLogin() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            FileInputStream fis=new FileInputStream("src\\application\\Connection\\connectionLogin.prop");
            Properties p=new Properties (); 
            p.load(fis); 
            con=DriverManager.getConnection((String)p.get("url"),(String)p.get("username"),(String)p.get("password"));
        } catch (Exception ex) {
            Logger.getLogger(DbConnectionLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static DbConnectionLogin getDatabaseConnection() {
        if (dbc == null) {
            dbc = new DbConnectionLogin();
        }
        return dbc;
    }
    
    public Connection getConnection() {
        return con;
    }
    
    public static void main(String[] args) {
        new DbConnectionLogin();
    }
}
