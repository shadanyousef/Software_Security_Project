import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public String recommendFitnessPlans(User user) {
        String recommendedPlan = null; // To hold the recommended fitness plan
        boolean planFound = false; // Track if any valid plan is found
        boolean healthWarningsNeeded = false; // Track if health warnings should be displayed

        for (FitnessPlan plan : plans) {
            // Check if the user's goal and fitness level match the plan
            if (plan.getGoal().equalsIgnoreCase(user.getFitnessGoal()) &&
                    isLevelSufficient(user.getCurrentLevel(), plan.getLevel())) {

                planFound = true;
                recommendedPlan = plan.getCategory();
                int totalTime = plan.getMinDuration() + getAdditionalTime(user.getCurrentLevel());

                // Special handling for HIIT plan
                if (plan.getCategory().equalsIgnoreCase("HIIT")) {
                    if (user.getBoneOrJointProblems().equalsIgnoreCase("yes")
                            || user.getSurgery().equalsIgnoreCase("yes")) {
                        System.out.println("\nPlease BE AWARE and consider the following:");
                        System.out.println(
                                "HIIT is not recommended for joint or bone problems or for those recovering from surgery due to high impact."
                                        + " Please try a different fitness goal.");
                        return null;
                    }
                }

                // Display the recommended plan
                System.out.println("\nRecommended Fitness Plans based on your input:");
                System.out.println("Category: " + plan.getCategory() +
                        ", Required Weekly Time: " + totalTime + " minutes" +
                        ", Goal: " + plan.getGoal());

            }

            // Check if any health warnings are needed
            if (user.hasMedicalConditions()) {
                healthWarningsNeeded = true;
            }
        }

        // If no valid plans were found for the user's level and goal
        if (!planFound) {
            System.out.println(
                    "\nSorry, your current fitness level does not match the requirements for your selected fitness goal: "
                            + user.getFitnessGoal() + ".");
            return null;

        }

        // Display health warnings only if needed
        if (healthWarningsNeeded) {
            System.out.println("\nPlease BE AWARE and consider the following:");
            displayHealthWarnings(user, recommendedPlan); // Use the recommended plan, or null if none found
        }

        return recommendedPlan; // Return the recommended fitness plan
    }

    // Check if the user's level meets or exceeds the minimum required level for the plan
    private boolean isLevelSufficient(String userLevel, String requiredLevel) {
        List<String> levels = Arrays.asList("Beginner", "Intermediate", "Advanced");
        int userLevelIndex = levels.indexOf(userLevel);
        int requiredLevelIndex = levels.indexOf(requiredLevel);

        // User's level should be equal to or higher than the required level
        return userLevelIndex >= requiredLevelIndex;
    }

    // Add extra time based on the user's fitness level
    private int getAdditionalTime(String level) {
        switch (level.toLowerCase()) {
            case "beginner":
                return 30;
            case "intermediate":
                return 20;
            case "advanced":
                return 10;
            default:
                return 0;
        }
    }

    // Display warnings or suggestions based on user's health conditions and the fitness plan
    private void displayHealthWarnings(User user, String fitnessPlan) {

        if (fitnessPlan != null) {
            if (fitnessPlan.equalsIgnoreCase("Cardio")) {
                if (user.getBoneOrJointProblems().equalsIgnoreCase("yes")) {
                    System.out.println(
                            "For joint problems, low-impact cardio such as walking, cycling, or swimming is recommended.");
                }
                if (user.getDiabetes().equalsIgnoreCase("yes")) {
                    System.out.println(
                            "For diabetes, monitor glucose levels and avoid exercising during hypoglycemia or hyperglycemia.");
                }
                if (user.getHeartDisease().equalsIgnoreCase("yes")) {
                    System.out.println(
                            "For heart disease, light to moderate cardio is recommended after consulting a doctor.");
                }
                if (user.getSurgery().equalsIgnoreCase("yes")) {
                    System.out
                            .println(
                                    "After surgery, approach cardio with caution and tailor it to your recovery stages.");
                }
            }

            if (fitnessPlan.equalsIgnoreCase("Strength Training")) {
                if (user.getBoneOrJointProblems().equalsIgnoreCase("yes")) {
                    System.out.println(
                            "For joint problems, avoid heavy weights and focus on resistance training with lighter weights or resistance bands.");
                }
                if (user.getDiabetes().equalsIgnoreCase("yes")) {
                    System.out.println("For diabetes, strength training helps improve insulin sensitivity.");
                }
                if (user.getHeartDisease().equalsIgnoreCase("yes")) {
                    System.out.println(
                            "For heart conditions, avoid heavy weights and focus on controlled, moderate-intensity exercises after consulting with a cardiologist.");
                }
                if (user.getSurgery().equalsIgnoreCase("yes")) {
                    System.out.println(
                            "After surgery, introduce strength training carefully and avoid stress on the surgical area.");
                }
            }

            if (fitnessPlan.equalsIgnoreCase("Flexibility")) {
                if (user.getBoneOrJointProblems().equalsIgnoreCase("yes")) {
                    System.out
                            .println(
                                    "For joint problems, flexibility exercises like stretching are safe and beneficial.");
                }
                if (user.getDiabetes().equalsIgnoreCase("yes")) {
                    System.out.println(
                            "Flexibility exercises are safe for diabetes as long as movements are gentle.");
                }
                if (user.getHeartDisease().equalsIgnoreCase("yes")) {
                    System.out.println(
                            "Flexibility exercises are safe for heart disease as long as movements are gentle.");
                }
                if (user.getSurgery().equalsIgnoreCase("yes")) {
                    System.out.println(
                            "After surgery, gentle stretching can help restore movement but only with a doctor's approval.");
                }
            }

            if (fitnessPlan.equalsIgnoreCase("HIIT")) {
                if (user.getBoneOrJointProblems().equalsIgnoreCase("yes")) {
                    System.out.println(
                            "HIIT is not recommended for joint or bone problems due to high impact.");
                }
                if (user.getDiabetes().equalsIgnoreCase("yes")) {
                    System.out.println(
                            "For diabetes, HIIT should only be attempted under medical supervision. Please consult with a doctor before continuing with the plan.");
                }
                if (user.getHeartDisease().equalsIgnoreCase("yes")) {
                    System.out.println(
                            "For heart disease, HIIT should only be attempted under medical supervision. Please consult with a doctor before continuing with the plan.");
                }
                if (user.getSurgery().equalsIgnoreCase("yes")) {
                    System.out.println(
                            "HIIT is not recommended for those recovering from surgery.");
                }
            }

            if (fitnessPlan.equalsIgnoreCase("Yoga")) {
                if (user.getBoneOrJointProblems().equalsIgnoreCase("yes")) {
                    System.out.println(
                            "Yoga is excellent for joint problems as it improves flexibility and strength without high impact.");
                }
                if (user.getDiabetes().equalsIgnoreCase("yes")) {
                    System.out.println("For diabetes, yoga helps reduce stress and improve insulin sensitivity.");
                }
                if (user.getHeartDisease().equalsIgnoreCase("yes")) {
                    System.out.println(
                            "For heart disease, gentle forms of yoga focusing on breathing and relaxation are recommended.");
                }
                if (user.getSurgery().equalsIgnoreCase("yes")) {
                    System.out.println(
                            "For post-surgery individuals, restorative or gentle yoga is recommended to promote healing.");
                }
            }

            if (user.getMedication().equalsIgnoreCase("yes")) {
                System.out.println("You are taking medication. Please consult a doctor before starting this plan.");
            }
        }
    }
}
