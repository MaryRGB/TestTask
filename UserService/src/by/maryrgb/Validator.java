package by.maryrgb;

public class Validator {
    public static boolean validateEmail(String email){
        return email.matches(".+@.+\\..+");
    }
    public static boolean validateNumber(String number){
        return number.matches("375\\d\\d \\d{7}");
    }
}
