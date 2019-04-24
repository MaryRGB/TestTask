package by.maryrgb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private Connection connection;

    public UserRepository() throws SQLException{
        connection = DBConnection.getConnection();
        connection.setAutoCommit(false);
    }

    public void create(User user){
        PreparedStatement prepStatement = null;
        try {
            prepStatement = connection.prepareStatement("INSERT INTO user(first_name, last_name, email) values (?, ?, ?)");
            prepStatement.setString(1, user.getFirstName());
            prepStatement.setString(2, user.getLastName());
            prepStatement.setString(3, user.getEmail());
            prepStatement.executeUpdate();

            String userID = getUserId(user.getFirstName(), user.getLastName(), user.getEmail());

            for (String phoneNumber : user.getPhoneNumbers()) {
                addPhoneNumber(userID, phoneNumber);
            }

            for (String role : user.getRoles()) {
                String roleId = getRoleId(role);
                addRoleToUser(userID, roleId);
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            }
            catch(SQLException sqlex2){
                sqlex2.printStackTrace();
            }
        }
    }

    public String getUserInfo(String firstName, String lastName, String email) {
        try {
            String userID = getUserId(firstName, lastName, email);
            String result = "";

            PreparedStatement prepStatement = null;
            prepStatement = connection.prepareStatement("SELECT GROUP_CONCAT(name) AS roles\n" +
                    "FROM user_role JOIN role ON user_role.role_id = role.id\n" +
                    "WHERE user_id = ?");
            prepStatement.setString(1, userID);
            ResultSet rs = prepStatement.executeQuery();
            if(rs.next()){
                result += "roles: " + rs.getString(1) + "\n\r";
            }

            prepStatement = connection.prepareStatement("SELECT GROUP_CONCAT(phone_number) AS roles\n" +
                    "FROM phone\n" +
                    "WHERE user_id = ?");
            prepStatement.setString(1, userID);
            rs = prepStatement.executeQuery();
            if(rs.next()){
                result += "phone numbers: " + rs.getString(1);
            }

            return result;
        }
        catch(SQLException sqlex){
            sqlex.printStackTrace();
            return "";
        }
    }

    public void deleteUser(String firstName, String lastName, String email) {
        try {
            String userID = getUserId(firstName, lastName, email);

            PreparedStatement prepStatement = null;
            prepStatement = connection.prepareStatement("DELETE FROM user_role WHERE user_id = ?");
            prepStatement.setString(1, userID);
            prepStatement.executeUpdate();

            prepStatement = connection.prepareStatement("DELETE FROM phone WHERE user_id = ?");
            prepStatement.setString(1, userID);
            prepStatement.executeUpdate();

            prepStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?");
            prepStatement.setString(1, userID);
            prepStatement.executeUpdate();

            connection.commit();
        }
        catch(SQLException sqlex){
            sqlex.printStackTrace();
            try {
                connection.rollback();
            }
            catch(SQLException sqlex2){
                sqlex2.printStackTrace();
            }
        }
    }

    public void addPhoneNumber(String userID, String phoneNumber) throws SQLException{
        PreparedStatement prepStatement = null;
        prepStatement = connection.prepareStatement("INSERT INTO phone(user_id, phone_number) values (?, ?)");
        prepStatement.setString(1, userID.toString());
        prepStatement.setString(2, phoneNumber);
        prepStatement.executeUpdate();
    }

    public String getUserId(String firstName, String lastName, String email) throws SQLException{
        PreparedStatement prepStatement = null;
        prepStatement = connection.prepareStatement("SELECT id FROM user WHERE first_name = ? AND last_name = ? AND email = ?");
        prepStatement.setString(1, firstName);
        prepStatement.setString(2, lastName);
        prepStatement.setString(3, email);
        ResultSet rs = prepStatement.executeQuery();

        if(rs.next()) {
            return rs.getString(1);
        }
        return "";
    }

    public void addRoleToUser(String userID, String roleID) throws SQLException{
        PreparedStatement prepStatement = null;
        prepStatement = connection.prepareStatement("INSERT INTO user_role(user_id, role_id) values (?, ?)");
        prepStatement.setString(1, userID);
        prepStatement.setString(2, roleID);
        prepStatement.executeUpdate();
    }

    public String getRoleId(String name) throws SQLException{
        PreparedStatement prepStatement = null;
        prepStatement = connection.prepareStatement("SELECT id FROM role WHERE name = ?");
        prepStatement.setString(1, name);
        ResultSet rs = prepStatement.executeQuery();

        if(rs.next()){
            return rs.getString(1);
        }

        prepStatement = connection.prepareStatement("INSERT INTO role(name) values (?)");
        prepStatement.setString(1, name);
        prepStatement.executeUpdate();

        prepStatement = connection.prepareStatement("SELECT id FROM role WHERE name = ?");
        prepStatement.setString(1, name);
        rs = prepStatement.executeQuery();

        if(rs.next()) {
            return rs.getString(1);
        }
        return "";
    }

    public void closeConnection(){
        try{
            if(connection != null){
                connection.close();
            }
        }
        catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
    }
}
