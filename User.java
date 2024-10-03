class User {
    private String fitnessGoal;
    private String currentLevel;
    private String medicalHistory;  // Sensitive information

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

    // Provide restricted access to medical history
    public boolean hasMedicalConditions() {
        return !medicalHistory.isEmpty();
    }

    // Helper method for checking specific conditions
    public boolean hasHeartDisease() {
        return medicalHistory.toLowerCase().contains("heart disease");
    }

    
}
