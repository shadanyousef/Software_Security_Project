import java.util.InputMismatchException;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Welcome statement
        System.out.println("Welcome to the Personalized Fitness Plan Recommendation System!");

        // System.out.println("1. Create a new Personalized Fitness Plan");
        // System.out.println("2. View your Personalized Fitness Plan");

        // User Information section
        System.out.print("Please enter your name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Please enter your age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Age condition: Only users aged 16 and older can apply
        if (age < 16) {
            System.out.println("Sorry, you must be 16 years or older to use this system.");
            scanner.close();
            return; // Terminate the program if the user is under 16
        }

        System.out.print("Please enter your gender (Male/Female): ");
        String gender = "";
        // validating gender input
        while (gender.isEmpty()) {
            gender = scanner.nextLine().trim().toLowerCase();
            if (gender.equals("male") || gender.equals("m")) {
                gender = "Male"; // Normalize to "Male"
            } else if (gender.equals("female") || gender.equals("f")) {
                gender = "Female"; // Normalize to "Female"
            } else {
                System.out.print("Invalid input. Please enter 'Male', 'Female', 'm', or 'f': ");
                gender = ""; // Reset gender to keep the loop running
            }
        }

        // Health section
        System.out.println("\nHealth Information:");

        String boneOrJointProblems = getYesOrNoInput(scanner, "Do you have bone or joint problems? (yes/no): ");
        String diabetes = getYesOrNoInput(scanner, "Do you have diabetes? (yes/no): ");
        String heartDisease = getYesOrNoInput(scanner, "Do you have heart disease? (yes/no): ");
        String surgery = getYesOrNoInput(scanner, "Have you had any surgery? (yes/no): ");
        String medication = getYesOrNoInput(scanner, "Are you taking any medication? (yes/no): ");

        // Fitness plan section
        String goal = "";
        while (goal.isEmpty()) {
            System.out.print(
                    "\nSelect your Fitness Goal (1. Weight Loss, 2. Muscle Building, 3. Improve Flexibility, 4. Improve Cardiovascular Health, 5. Stress Relief): ");
            try {
                int goalChoice = scanner.nextInt();
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

        // Fitness level section
        String level = "";
        while (level.isEmpty()) {
            System.out.print("Select your Current Fitness Level (1. Beginner, 2. Intermediate, 3. Advanced): ");
            try {
                int levelChoice = scanner.nextInt();
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

        // Create the user object after validation
        User user = new User(name, age, gender, goal, level, boneOrJointProblems, diabetes, heartDisease, surgery,
                medication);
        FitnessRecommendationSystem system = new FitnessRecommendationSystem();
        system.recommendFitnessPlans(user);

        scanner.close();
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
