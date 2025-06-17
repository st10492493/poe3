package prog3;
import java.security.MessageDigest;

public class User {
    private String firstName;
    private String surname;
    private String username;
    private String phoneNumber;
    private String passwordHash;

    public User(String firstName, String surname, String username, 
               String phoneNumber, String password) {
        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.passwordHash = hashPassword(password);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }

    public boolean verifyPassword(String password) {
        return passwordHash.equals(hashPassword(password));
    }

    // Getters
    public String getUsername() { return username; }
    public String getFirstName() { return firstName; }
    public String getPhoneNumber() { return phoneNumber; }
}