import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FitnessPlan {
    private String category;
    private int minDuration;
    private String level;
    private String goal;

    public FitnessPlan(String category, int minDuration, String level, String goal) {
        this.category = category;
        this.minDuration = minDuration;
        this.level = level;
        this.goal = goal;
    }

    public String getCategory() {
        return category;
    }

    public int getMinDuration() {
        return minDuration;
    }

    public String getLevel() {
        return level;
    }

    public String getGoal() {
        return goal;
    }
}

class User {
    private String fitnessGoal;
    private String currentLevel;
    private String medicalHistory;

    public User(String fitnessGoal, String currentLevel, String medicalHistory) {
        this.fitnessGoal = fitnessGoal;
        this.currentLevel = currentLevel;
        this.medicalHistory = medicalHistory;
    }

    public String getFitnessGoal() {
        return fitnessGoal;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }
}

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
                int totalTime = plan.getMinDuration() + getAdditionalTime(user.getCurrentLevel());
                System.out.println("Category: " + plan.getCategory() +
                                   ", Required Weekly Time: " + totalTime + " minutes" +
                                   ", Goal: " + plan.getGoal());
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your Fitness Goal (Weight Loss, Muscle Building, Improve Flexibility, Improve Cardiovascular Health, Stress Relief): ");
        String goal = scanner.nextLine();

        System.out.print("Enter your Current Fitness Level (Beginner, Intermediate, Advanced): ");
        String level = scanner.nextLine();

        System.out.print("Enter your Medical History: ");
        String medicalHistory = scanner.nextLine();

        User user = new User(goal, level, medicalHistory);
        FitnessRecommendationSystem system = new FitnessRecommendationSystem();
        system.recommendFitnessPlans(user);

        scanner.close();
    }
}
