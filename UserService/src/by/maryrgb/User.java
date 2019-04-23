package by.maryrgb;

import java.util.LinkedList;
import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;
    private List<String> phoneNumbers;

    public User(){
        roles = new LinkedList<>();
        phoneNumbers = new LinkedList<>();
    }

    public User(String firstName, String lastName, String email, List<String> roles, List<String> phoneNumbers){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
        this.phoneNumbers = phoneNumbers;
    }

    public User(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        roles = new LinkedList<>();
        phoneNumbers = new LinkedList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void addRole(String role) throws OverFlowException{
        if(roles.size() < 3)
        {
            roles.add(role);
        }
        else{
            throw new OverFlowException("User has maximum number of roles.");
        }
    }

    public void removeRole(String role) {
        if(roles.contains(role))
        {
            roles.remove(role);
        }
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void addPhoneNumber(String phoneNumber) throws OverFlowException{
        if(phoneNumbers.size() < 3)
        {
            phoneNumbers.add(phoneNumber);
        }
        else{
            throw new OverFlowException("User has maximum number of telephones.");
        }
    }

    public void removePhoneNumber(String phoneNumber) {
        if(roles.contains(phoneNumber))
        {
            roles.remove(phoneNumber);
        }
    }
}