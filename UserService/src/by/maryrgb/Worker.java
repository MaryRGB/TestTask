package by.maryrgb;

import java.sql.SQLException;
import java.util.Scanner;

public class Worker {
    private UserRepository ur;

    Worker() throws SQLException {
        ur = new UserRepository();
    }

    public String getInfo(Scanner scanner) throws Exception{
        System.out.println("Template: first_name, last_name, email");
        String[] params = scanner.nextLine().split(", ");
        if(!Validator.validateEmail(params[2])) {
            throw new Exception("Wrong email format. Must be : *******@*****.***");
        }
        return ur.getUserInfo(params[0], params[1], params[2]);
    }

    public void create(Scanner scanner) throws OverFlowException, Exception{
        User user = new User();
        System.out.println("Template: first_name, last_name, email, [role1; role2;...], [phone1; phone2;...]");
        String[] params = scanner.nextLine().split(", ");
        user.setFirstName(params[0]);
        user.setLastName(params[1]);
        if(Validator.validateEmail(params[2])) {
            user.setEmail(params[2]);
        }
        else{
            throw new Exception("Wrong email format. Must be : *******@*****.***");
        }
        if(params.length > 3) {
            setRoles(user, params[3]);
        }
        if(params.length > 4) {
            setPhoneNumbers(user, params[4]);
        }

        ur.create(user);
    }

    public void delete(Scanner scanner){
        System.out.println("Template: first_name, last_name, email");
        String[] params = scanner.nextLine().split(", ");
        ur.deleteUser(params[0], params[1], params[2]);
    }

    public void endWork(){
        ur.closeConnection();
    }

    private void setRoles(User user, String roles) throws OverFlowException{
        String[] massOfRoles = roles.substring(1, roles.length() - 1).split("; ");
        for (String elem : massOfRoles) {
            user.addRole(elem);
        }
    }

    private void setPhoneNumbers(User user, String phones) throws OverFlowException, Exception{
        String[] massOfPhones = phones.substring(1, phones.length() - 1).split("; ");
        for (String elem : massOfPhones) {
            if(Validator.validateNumber(elem)) {
                user.addPhoneNumber(elem);
            }
            else{
                throw new Exception("Wrong phone number format. Must be : 375** *******");
            }
        }
    }
}
