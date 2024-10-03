
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FitnessRecommendationSystem {
    private List<FitnessPlan> plans;

    public FitnessRecommendationSystem() {
        plans = new ArrayList<>();
        loadFitnessPlans();
    }

    private void loadFitnessPlans() {
        plans.add(new FitnessPlan("Cardio", 150, "Beginner", "Weight Loss"));
        plans.add(new FitnessPlan("Strength Training", 120, "Intermediate", "Muscle Building"));
        plans.add(new FitnessPlan("Flexibility", 90, "Beginner", "Improve Flexibility"));
        plans.add(new FitnessPlan("HIIT", 90, "Advanced", "Improve Cardiovascular Health"));
        plans.add(new FitnessPlan("Yoga", 120, "Beginner", "Stress Relief"));
    }

    public void recommendFitnessPlans(User user) {
        System.out.println("Recommended Fitness Plans based on your input:");
        for (FitnessPlan plan : plans) {
            if (plan.getGoal().equalsIgnoreCase(user.getFitnessGoal()) &&
                plan.getLevel().equalsIgnoreCase(user.getCurrentLevel())) {
                
                // Check medical conditions before recommendation
                if (user.hasMedicalConditions() && !isSuitableBasedOnMedicalHistory(user, plan)) {
                    System.out.println("Note: This plan may not be suitable due to your medical history.");
                    continue;
                }

                int totalTime = plan.getMinDuration() + getAdditionalTime(user.getCurrentLevel());
                System.out.println("Category: " + plan.getCategory() +
                                   ", Required Weekly Time: " + totalTime + " minutes" +
                                   ", Goal: " + plan.getGoal());

                
                if (user.hasMedicalConditions()) {
                    System.out.println("Additional Notes: Please consult your doctor before starting any new fitness plan.");
                } 
            }
        }
    }

    private int getAdditionalTime(String level) {
        switch (level) {
            case "Beginner":
                return 30;
            case "Intermediate":
                return 20;
            case "Advanced":
                return 10;
            default:
                return 0;
        }
    }
 
    private boolean isSuitableBasedOnMedicalHistory(User user, FitnessPlan plan) {
        if (user.hasHeartDisease() && plan.getCategory().equalsIgnoreCase("HIIT")) {
            return false;  
        }
     
        return true;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // validation
        String goal = "";
        while (goal.isEmpty()) {
            System.out.print("Enter your Fitness Goal (Weight Loss, Muscle Building, Improve Flexibility, Improve Cardiovascular Health, Stress Relief): ");
            goal = scanner.nextLine().trim();
            if (!isValidGoal(goal)) {
                System.out.println("Invalid goal. Please enter a valid fitness goal.");
                goal = "";
            }
        }

        String level = "";
        while (level.isEmpty()) {
            System.out.print("Enter your Current Fitness Level (Beginner, Intermediate, Advanced): ");
            level = scanner.nextLine().trim();
            if (!isValidLevel(level)) {
                System.out.println("Invalid fitness level. Please enter a valid level.");
                level = "";
            }
        }

        System.out.print("Enter your Medical History: ");
        String medicalHistory = scanner.nextLine().trim();

        // Create the user object after validation
        User user = new User(goal, level, medicalHistory);
        FitnessRecommendationSystem system = new FitnessRecommendationSystem();
        system.recommendFitnessPlans(user);

        scanner.close();
    }

    
    private static boolean isValidGoal(String goal) {
        return goal.equalsIgnoreCase("Weight Loss") ||
               goal.equalsIgnoreCase("Muscle Building") ||
               goal.equalsIgnoreCase("Improve Flexibility") ||
               goal.equalsIgnoreCase("Improve Cardiovascular Health") ||
               goal.equalsIgnoreCase("Stress Relief");
    }

    private static boolean isValidLevel(String level) {
        return level.equalsIgnoreCase("Beginner") ||
               level.equalsIgnoreCase("Intermediate") ||
               level.equalsIgnoreCase("Advanced");
    }
}
