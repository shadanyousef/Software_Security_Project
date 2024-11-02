import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class UserDatabase {
    private static final String FILE_NAME = "users.dat";
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding"; // AES with CBC mode
    private static final String SECRET_KEY = "1234567890123456"; // 16 char key for AES-128
    private static final byte[] IV = new byte[16]; // 16 byte IV

    // Save user method
    public void saveUser(User user) {
        List<User> users = loadUsers(); // Load existing users

        // Hash the password before saving
        String hashedPassword = hashPassword(user.getPassword());
        User newUser = new User(user.getEmail(), hashedPassword, user.getName(), user.getAge(),
                user.getGender(), user.getFitnessGoal(), user.getCurrentLevel(),
                user.getBoneOrJointProblems(), user.getDiabetes(),
                user.getHeartDisease(), user.getSurgery(),
                user.getMedication(), user.getFitnessCategory());

        users.add(newUser); // Add the new user

        // Serialize the updated list of users and encrypt it
        try {
            CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(FILE_NAME + ".enc"),
                    getCipher(Cipher.ENCRYPT_MODE));
            try (ObjectOutputStream oos = new ObjectOutputStream(cos)) {
                oos.writeObject(users); // Write users to encrypted file
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle IO exceptions
        } catch (Exception e) {
            e.printStackTrace(); // Handle other exceptions from getCipher
        }
    }

    // Load users method
    @SuppressWarnings("unchecked")
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try {
            CipherInputStream cis = new CipherInputStream(new FileInputStream(FILE_NAME + ".enc"),
                    getCipher(Cipher.DECRYPT_MODE));
            try (ObjectInputStream ois = new ObjectInputStream(cis)) {
                users = (List<User>) ois.readObject(); // Deserialize the list of users
            }
        } catch (FileNotFoundException e) {
            // No users yet, file not found is fine
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle IO and class not found exceptions
        } catch (Exception e) {
            e.printStackTrace(); // Handle other exceptions from getCipher
        }
        return users;
    }

    // Hash the password using SHA-256
    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Get the Cipher object for encryption/decryption
    private Cipher getCipher(int mode) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        IvParameterSpec ivParams = new IvParameterSpec(IV);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(mode, keySpec, ivParams);
        return cipher;
    }

    // Load user by email
    public User loadUserByEmail(String email) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user; // Return the matching user
            }
        }
        return null; // If no user found
    }

    // Method to print users
    public void printUsers() {
        List<User> users = loadUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
