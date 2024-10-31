import java.util.Scanner;
import java.util.regex.Pattern;

public class Validations {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,}$");

    // Validate email format
    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isEmailUnique(String email, UserDatabase db) {
        return db.loadUserByEmail(email) == null;
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[a-z].*") &&
               password.matches(".*[0-9].*");
    }

    public static int getValidAge(Scanner scanner) {
        int age;
        while (true) {
            System.out.print("Please enter your age: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number for age.");
                scanner.next(); // Clear invalid input
            }
            age = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (age < 16) {
                System.out.println("Sorry, you must be 16 years or older to use this system.");
            } else {
                break; // Exit loop if age is valid
            }
        }
        return age;
    }

    public static String getValidGender(Scanner scanner) {
        String gender;
        while (true) {
            System.out.print("Please enter your gender (Male/Female): ");
            gender = scanner.nextLine().trim().toLowerCase();
            if (gender.equals("male") || gender.equals("m")) {
                return "Male"; // Return normalized gender
            } else if (gender.equals("female") || gender.equals("f")) {
                return "Female"; // Return normalized gender
            } else {
                System.out.println("Invalid input. Please enter 'Male', 'Female', 'm', or 'f'.");
            }
        }
    }
}
