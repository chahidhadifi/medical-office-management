package utils;

public class InputValidator {
    
    public InputValidator() {
        
    }
    
    public static boolean isValidInput(String firstname, String lastname, String email, String gender, String bloodType) {
        return isValidFirstname(firstname) && isValidLastname(lastname) && isValidEmail(email) && isValidGender(gender) && isValidBloodType(bloodType);
    }
    
    public static boolean isValidInputRegistration(String firstname, String lastname, String username, String email, String password) {
        return isValidUsername(username) && isValidEmail(email) && isValidPassword(password) && isValidFirstname(firstname) && isValidLastname(lastname);
    }
    
    public static boolean isValidGender(String gender) {
        return !gender.isEmpty();
    }
    
    public static boolean isValidBloodType(String bloodType) {
        return !bloodType.isEmpty();
    }

    public static boolean isValidUsername(String username) {
        return !username.isEmpty();
    }
    
    public static boolean isValidFirstname(String firstname) {
        return !firstname.isEmpty();
    }
    
    public static boolean isValidLastname(String lastname) {
        return !lastname.isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 5;
    }
    
    public static boolean containsNumbers(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    
}
