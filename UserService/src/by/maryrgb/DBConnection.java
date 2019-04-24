package by.maryrgb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static String DATABASE_DRIVER;
    private static String DATABASE_URL;
    private static String DATABASE_USERNAME;
    private static String DATABASE_PASSWORD;

    public static Connection getConnection() {
        Connection conn = null;
        try{
            if(DATABASE_DRIVER == null){
                Properties properties = new Properties();
                properties.load(Files.newInputStream(Paths.get("I:\\TestTask\\UserService\\src\\app.properties")));
                DATABASE_DRIVER = properties.getProperty("db.driver");
                DATABASE_URL = properties.getProperty("db.url");
                DATABASE_USERNAME = properties.getProperty("db.username");
                DATABASE_PASSWORD = properties.getProperty("db.password");
            }

            Class.forName(DATABASE_DRIVER).newInstance();
            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
        catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return conn;
    }
}
