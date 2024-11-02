import java.util.InputMismatchException;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDatabase userDatabase = new UserDatabase();

        userDatabase.printUsers(); // Debug output

        // Welcome statement
        System.out.println("Welcome to the Personalized Fitness Plan Recommendation System!");

        // Sign up or log in
        int choice = 0;
        boolean validChoice = false;

        while (!validChoice) {
            System.out.print("Do you want to sign up (1) or log in (2)? ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice == 1) {
                    signUp(scanner, userDatabase);
                    validChoice = true; // Set to true to exit the loop
                } else if (choice == 2) {
                    logIn(scanner, userDatabase);
                    validChoice = true; // Set to true to exit the loop
                } else {
                    System.out.println("Invalid choice, try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice, try again.");
                scanner.next(); // Clear invalid input
            }
        }

        scanner.close();
    }

    // Sign up method
    private static void signUp(Scanner scanner, UserDatabase userDatabase) {
        // EMAIL
        String email;
        do {
            System.out.print("Enter your email: ");
            email = scanner.nextLine().trim();
            if (!Validations.isValidEmail(email) || !Validations.isEmailUnique(email, userDatabase)) {
                System.out.println("Invalid email or email already exists. Please try again.");
                System.out.println("Email format: username@domain.extension");
            }
        } while (!Validations.isValidEmail(email) || !Validations.isEmailUnique(email, userDatabase));

        // PASSWORD
        String password;
        do {
            System.out.print("Enter your password: ");
            password = scanner.nextLine();
            if (!Validations.isValidPassword(password)) {
                System.out.println(
                        "Password must be at least 8 characters long, including at least 1 capital letter, 1 small letter, and 1 number.");
            }
        } while (!Validations.isValidPassword(password));
        // Hash the password before creating the User object
        String hashedPassword = userDatabase.hashPassword(password);
        // System.out.println("Hashed Password: " + hashedPassword); // Debug output

        // NAME
        System.out.print("Please enter your name: ");
        String name = scanner.nextLine().trim();

        // AGE
        int age = Validations.getValidAge(scanner);

        // GENDER
        String gender = Validations.getValidGender(scanner);

        // Health section
        String boneOrJointProblems = getYesOrNoInput(scanner, "Do you have bone or joint problems? (yes/no): ");
        String diabetes = getYesOrNoInput(scanner, "Do you have diabetes? (yes/no): ");
        String heartDisease = getYesOrNoInput(scanner, "Do you have heart disease? (yes/no): ");
        String surgery = getYesOrNoInput(scanner, "Have you had any surgery? (yes/no): ");
        String medication = getYesOrNoInput(scanner, "Are you taking any medication? (yes/no): ");

        // Fitness plan section
        String goal;
        String level;
        String fitnessCategory = ""; // To hold the fitness category

        // Loop until a valid fitness plan is generated
        do {
            goal = getFitnessGoal(scanner);
            level = getFitnessLevel(scanner);

            // After gathering user inputs
            User user = new User(email, hashedPassword, name, age, gender, goal, level,
                    boneOrJointProblems, diabetes, heartDisease, surgery, medication, "");

            // Determine the fitness plan category based on user input
            FitnessRecommendationSystem system = new FitnessRecommendationSystem();
            fitnessCategory = system.recommendFitnessPlans(user); // Retrieve the fitness category

            // Check if a valid fitness plan was generated
            if (fitnessCategory == null || fitnessCategory.isEmpty()) {
                System.out.println("Sorry, no valid fitness plan could be generated based on your inputs.");
                String tryAgainResponse = getYesOrNoInput(scanner, "Would you like to try again? (yes/no): ");

                if (!tryAgainResponse.equals("yes")) {
                    System.out.println("Thank you for using the system. Exiting...");
                    return; // Exit the method if the user doesn't want to try again
                }
                System.out.println("Please re-enter your fitness goal and level.");
            }
        } while (fitnessCategory == null || fitnessCategory.isEmpty());

        // Update user object with fitnessCategory
        User user = new User(email, password, name, age, gender, goal, level, boneOrJointProblems, diabetes, heartDisease,
                surgery, medication, fitnessCategory); // Update user with the correct fitness category

        // Save user data
        userDatabase.saveUser(user);

        // Ask if user wants to view their information
        String viewResponse = getYesOrNoInput(scanner, "\nWould you like to view your saved information? (yes/no): ");

        if (viewResponse.equals("yes")) {
            User savedUser = userDatabase.loadUserByEmail(email);
            if (savedUser != null) {
                System.out.println("\nSaved User Information:");
                System.out.println(savedUser);
                System.out.println("-------------------------");
            } else {
                System.out.println("No saved information found for user: " + name);
            }
        }

    }

    // Login method
    private static void logIn(Scanner scanner, UserDatabase userDatabase) {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();
        User user = userDatabase.loadUserByEmail(email);

        if (user == null) {
            System.out.println("Email not found. Please sign up.");
            return;
        }

        int attempts = 0;
        while (attempts < 2) { // Allow up to 2 attempts
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            // Hash the entered password
            String hashedPassword = userDatabase.hashPassword(password);
            // Debug: Print the stored and entered password hashes
            //System.out.println("Entered Password Hash: " + hashedPassword);
            //System.out.println("Stored Password Hash: " + user.getPassword());

            if (user.getPassword().equals(hashedPassword)) {
                System.out.println("\nLogin successful! Here is your information:");
                System.out.println(user);
                return; // Exit the method on successful login
            } else {
                attempts++;
                System.out.println("Incorrect password. Try again.");
            }
        }

        System.out.println("Too many failed attempts. Please try again later.");
    }

    // Method to ensure user input is either "yes" or "no" (ignoring case)
    private static String getYesOrNoInput(Scanner scanner, String prompt) {
        String input = "";
        while (input.isEmpty()) {
            System.out.print(prompt);
            input = scanner.nextLine().trim().toLowerCase();
            if (!input.equals("yes") && !input.equals("no")) {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                input = ""; // Reset input to keep the loop running
            }
        }
        return input;
    }

    // Fitness goal method
    private static String getFitnessGoal(Scanner scanner) {
        String goal = "";
        while (goal.isEmpty()) {
            System.out.print(
                    "\nSelect your Fitness Goal (1. Weight Loss, 2. Muscle Building, 3. Improve Flexibility, 4. Improve Cardiovascular Health, 5. Stress Relief): ");
            try {
                int goalChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                goal = getFitnessGoalByNumber(goalChoice);
                if (goal == null) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    goal = "";
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                scanner.next(); // Clear invalid input
            }
        }
        return goal;
    }

    // Map the number input to the fitness goal
    private static String getFitnessGoalByNumber(int number) {
        switch (number) {
            case 1:
                return "Weight Loss";
            case 2:
                return "Muscle Building";
            case 3:
                return "Improve Flexibility";
            case 4:
                return "Improve Cardiovascular Health";
            case 5:
                return "Stress Relief";
            default:
                return null;
        }
    }

    // Fitness level method
    private static String getFitnessLevel(Scanner scanner) {
        String level = "";
        while (level.isEmpty()) {
            System.out.print("Select your Current Fitness Level (1. Beginner, 2. Intermediate, 3. Advanced): ");
            try {
                int levelChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                level = getFitnessLevelByNumber(levelChoice);
                if (level == null) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                    level = "";
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                scanner.next(); // Clear invalid input
            }
        }
        return level;
    }

    // Map the number input to the fitness level
    private static String getFitnessLevelByNumber(int number) {
        switch (number) {
            case 1:
                return "Beginner";
            case 2:
                return "Intermediate";
            case 3:
                return "Advanced";
            default:
                return null;
        }
    }
}
